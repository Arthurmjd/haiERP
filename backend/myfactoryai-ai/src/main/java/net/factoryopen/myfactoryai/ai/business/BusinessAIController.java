package net.factoryopen.myfactoryai.ai.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.factoryopen.myfactoryai.ai.config.LLMConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务知识库问答（基于官网用户手册）
 */
@RestController
@RequestMapping("/ai/business")
public class BusinessAIController {

    private static final Logger log = LoggerFactory.getLogger(BusinessAIController.class);

    @Autowired
    private HtmlKnowledgeService knowledgeService;

    @Autowired
    private LLMConfig llmConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 基于知识库的问答接口（流式）
     * @param request 包含 content（用户问题），可选 provider/model
     * @return SSE 流式响应
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody Map<String, String> request) {
        String userQuestion = request.get("content");
        String provider = request.getOrDefault("provider", "deepseek");
        String customModel = request.get("model");

        log.info("【知识库问答】用户问题: {}", userQuestion);
        log.info("【知识库问答】使用模型: {} / {}", provider, customModel);

        if (userQuestion == null || userQuestion.trim().isEmpty()) {
            return Flux.just("data: {\"content\": \"问题不能为空\"}\n\n", "data: [DONE]\n\n");
        }

        // 获取知识库内容（带缓存）
        String knowledgeText = knowledgeService.getKnowledgeText();
        log.info("【知识库问答】知识库长度: {} 字符", knowledgeText.length());

        // 构建 system prompt
        String systemPrompt = buildSystemPrompt(knowledgeText);
        String userPrompt = buildUserPrompt(userQuestion);

        // 调用大模型
        return callLLM(systemPrompt, userPrompt, provider, customModel);
    }

    private String buildSystemPrompt(String knowledgeText) {
        return """
                你是 FactoryOpen 开源项目的 AI 助手，请严格基于以下《用户手册》内容回答用户问题。
                如果手册中没有相关信息，请如实告知用户“手册中未提及，建议查阅官网或联系技术支持”。
                回答要准确、简洁、友好。

                ========== 用户手册内容 ==========
                %s
                =================================
                """.formatted(knowledgeText);
    }

    private String buildUserPrompt(String userQuestion) {
        return "用户问题：" + userQuestion + "\n\n请根据上述手册内容回答。";
    }

    private Flux<String> callLLM(String systemPrompt, String userPrompt, String provider, String model) {
        WebClient client = llmConfig.getWebClient(provider);
        String actualModel = (model != null && !model.isEmpty()) ? model
                : ("deepseek".equals(provider) ? llmConfig.getDeepseekModel() : llmConfig.getQwenModel());

        Object body;
        if ("dashscope".equals(provider)) {
            body = Map.of(
                    "model", actualModel,
                    "stream", true,
                    "input", Map.of("messages", List.of(
                            Map.of("role", "system", "content", systemPrompt),
                            Map.of("role", "user", "content", userPrompt)
                    )),
                    "parameters", Map.of("result_format", "message", "incremental_output", true)
            );
        } else {
            body = Map.of(
                    "model", actualModel,
                    "stream", true,
                    "messages", List.of(
                            Map.of("role", "system", "content", systemPrompt),
                            Map.of("role", "user", "content", userPrompt)
                    )
            );
        }

        return client.post()
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .flatMap(line -> {
                    if (line.startsWith("data:")) {
                        String data = line.substring(5).trim();
                        if ("[DONE]".equals(data)) {
                            return Flux.just("data: [DONE]\n\n");
                        }
                        try {
                            Map<String, Object> json = objectMapper.readValue(data, Map.class);
                            String content = extractContent(json, provider);
                            if (content != null && !content.isEmpty()) {
                                return Flux.just("data: {\"content\": " + jsonEscape(content) + "}\n\n");
                            }
                            return Flux.empty();
                        } catch (Exception e) {
                            log.warn("解析响应失败: {}", data);
                            return Flux.empty();
                        }
                    }
                    return Flux.empty();
                })
                .concatWith(Flux.just("data: [DONE]\n\n"));
    }

    private String extractContent(Map<String, Object> json, String provider) {
        if ("dashscope".equals(provider)) {
            Map<String, Object> output = (Map<String, Object>) json.get("output");
            if (output != null && output.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    Object content = message.get("content");
                    return content != null ? content.toString() : "";
                }
            }
        } else {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) json.get("choices");
            if (choices != null && !choices.isEmpty()) {
                Map<String, Object> delta = (Map<String, Object>) choices.get(0).get("delta");
                if (delta != null && delta.containsKey("content")) {
                    Object content = delta.get("content");
                    return content != null ? content.toString() : "";
                }
            }
        }
        return "";
    }

    private String jsonEscape(String raw) {
        if (raw == null) return "\"\"";
        return "\"" + raw.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t") + "\"";
    }
}