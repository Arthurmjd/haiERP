package net.factoryopen.myfactoryai.ai.multimodal.ppt;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.factoryopen.myfactoryai.ai.config.LLMConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

/**
 * PPT 生成控制器
 * 多模态能力之一：自然语言生成 PPT 报告
 */
@RestController
@RequestMapping("/ai/multimodal/ppt")
public class PPTController {

    private static final Logger log = LoggerFactory.getLogger(PPTController.class);

    @Autowired
    private PPTGenerationService pptGenerationService;

    @Autowired
    private LLMConfig llmConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 通过自然语言生成 PPT
     *
     * 请求示例：
     * {
     *   "content": "帮我生成一份关于本月工厂运营的报告，包含产量、质量、设备三个部分",
     *   "provider": "deepseek",
     *   "model": "deepseek-v4-flash"
     * }
     */
    @PostMapping("/generate")
    public ResponseEntity<byte[]> generate(@RequestBody Map<String, String> request) {
        String userMessage = request.get("content");
        String provider = request.getOrDefault("provider", "deepseek");
        String model = request.get("model");

        log.info("========================================");
        log.info("【多模态-PPT】用户需求: {}", userMessage);
        log.info("【多模态-PPT】使用模型: {} / {}", provider, model);

        try {
            // 1. 调用大模型解析用户意图，提取标题和章节
            String prompt = buildExtractPrompt(userMessage);
            String aiResponse = callLLMForExtract(prompt, provider, model);
            log.debug("【多模态-PPT】AI解析结果: {}", aiResponse);

            // 2. 解析 AI 返回的 JSON
            @SuppressWarnings("unchecked")
            Map<String, Object> pptPlan = objectMapper.readValue(aiResponse, Map.class);
            String title = (String) pptPlan.getOrDefault("title", "AI 生成报告");

            @SuppressWarnings("unchecked")
            List<Map<String, String>> sectionsMap = (List<Map<String, String>>) pptPlan.get("sections");
            List<PPTGenerationService.ReportSection> sections = new ArrayList<>();
            if (sectionsMap != null) {
                for (Map<String, String> section : sectionsMap) {
                    sections.add(new PPTGenerationService.ReportSection(
                            section.get("heading"),
                            section.get("content")
                    ));
                }
            }

            // 3. 生成 PPT
            byte[] pptBytes = pptGenerationService.generateReport(title, sections);

            // 4. 返回文件
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "report.pptx");

            log.info("【多模态-PPT】生成成功，标题: {}", title);
            return ResponseEntity.ok().headers(headers).body(pptBytes);

        } catch (Exception e) {
            log.error("【多模态-PPT】生成失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 构建 PPT 结构提取的 Prompt
     */
    private String buildExtractPrompt(String userMessage) {
        return """
            你是一位专业的工厂管理报告专家。用户想要生成一份PPT报告，请根据用户需求提取报告标题和章节内容。

            用户需求：%s

            要求：
            1. 如果用户明确指定了章节，按用户指定的生成
            2. 如果用户没有指定，请根据"工厂运营报告"常见内容生成3-5个章节
            3. 每个章节的content要具体，包含数据或要点，用换行符分隔多个要点
            4. 标题和章节标题要简洁明了

            请严格返回以下JSON格式（不要有其他文字）：
            {
              "title": "报告标题",
              "sections": [
                {"heading": "章节标题1", "content": "要点1\\n要点2\\n要点3"},
                {"heading": "章节标题2", "content": "内容描述"}
              ]
            }
            """.formatted(userMessage);
    }

    /**
     * 同步调用大模型提取 PPT 结构
     */
    private String callLLMForExtract(String prompt, String provider, String model) throws Exception {
        WebClient client = llmConfig.getWebClient(provider);

        String actualModel = (model != null && !model.isEmpty()) ? model
                : ("deepseek".equals(provider) ? llmConfig.getDeepseekModel() : llmConfig.getQwenModel());

        Object body;
        if ("dashscope".equals(provider)) {
            body = Map.of(
                    "model", actualModel,
                    "stream", false,
                    "input", Map.of("messages", List.of(Map.of("role", "user", "content", prompt))),
                    "parameters", Map.of("result_format", "message")
            );
        } else {
            body = Map.of(
                    "model", actualModel,
                    "stream", false,
                    "messages", List.of(Map.of("role", "user", "content", prompt))
            );
        }

        String response = client.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("AI 返回空响应");
        }

        String content = extractContentFromResponse(response, provider);
        if (content == null || content.isEmpty()) {
            throw new RuntimeException("AI 响应中无有效内容");
        }

        // 清理 markdown 标记
        String cleaned = content.replaceAll("```json\n?", "").replaceAll("\n?```", "").trim();
        return cleaned;
    }

    /**
     * 从 AI 响应中提取内容
     */
    private String extractContentFromResponse(String responseJson, String provider) {
        try {
            Map<String, Object> root = objectMapper.readValue(responseJson, Map.class);
            if ("dashscope".equals(provider)) {
                Map<String, Object> output = (Map<String, Object>) root.get("output");
                List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            } else {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) root.get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        } catch (Exception e) {
            log.error("提取 AI 响应内容失败", e);
            return null;
        }
    }
}