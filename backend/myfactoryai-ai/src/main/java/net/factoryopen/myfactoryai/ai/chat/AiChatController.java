package net.factoryopen.myfactoryai.ai.chat;

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
 * AI 对话控制器（流式）
 * 支持 DashScope（通义千问）和 DeepSeek，自动携带系统身份提示词
 */
@RestController
@RequestMapping("/ai")
public class AiChatController {

    private static final Logger log = LoggerFactory.getLogger(AiChatController.class);

    private final LLMConfig llmConfig;
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 系统提示词：明确 AI 的身份背景和职责边界
     * - AI 可以回答通用知识、协助生成文件等
     * - 但必须主动告知用户自己是 FactoryOpen 的 AI 助手，核心职责是帮助操作工厂系统
     */
    private static final String SYSTEM_PROMPT = """
            你是 FactoryOpen 开源项目的 AI 助手，专门为 FactoryOpen 工厂管理系统提供智能服务。
            FactoryOpen 是一套覆盖 PLM、CRM、MRPII、MES、IIoT 适配和车间数字孪生的智能制造平台。
            你的核心职责是帮助用户操作和使用 FactoryOpen 系统，包括解答系统功能、操作流程、实施建议等。
            同时，你也可以回答通用知识问题、协助生成文件、提供编程帮助等，但在回答任何问题前（或回答中），请先简要说明你的身份背景：
            “我是 FactoryOpen 的 AI 助手，本职是为工厂系统服务，但也可以尽力帮助您解决其他问题。”
            回答要求：简洁、专业、有帮助。
            """;

    @Autowired
    public AiChatController(LLMConfig llmConfig) {
        this.llmConfig = llmConfig;
    }

    /**
     * 聊天接口（流式输出）
     * @param request 请求体，包含 content（用户消息）、provider（模型提供商）、model（具体模型名）
     * @return SSE 流式响应
     */
    @PostMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chat(@RequestBody Map<String, String> request) {
        String userMessage = request.get("content");
        String provider = request.getOrDefault("provider", "deepseek");
        String customModel = request.get("model");

        log.info("========================================");
        log.info("[AiChat] 收到用户消息: {}", userMessage);
        log.info("[AiChat] 使用模型提供商: {}, 指定模型: {}", provider, customModel);

        if (userMessage == null || userMessage.trim().isEmpty()) {
            log.warn("[AiChat] 用户消息为空");
            return Flux.just("data: {\"content\": \"消息不能为空\"}\n\n");
        }

        if ("deepseek".equalsIgnoreCase(provider)) {
            return callDeepSeek(userMessage, customModel);
        } else {
            return callDashScope(userMessage, customModel);
        }
    }

    // ==================== 通义千问（DashScope）调用 ====================

    /**
     * 调用阿里云百炼（通义千问）流式接口，已注入系统提示词
     */
    private Flux<String> callDashScope(String userMessage, String customModel) {
        String actualModel = (customModel != null && !customModel.isEmpty()) ? customModel : llmConfig.getQwenModel();
        WebClient webClient = llmConfig.getWebClient("dashscope");

        // 构造包含 system 和 user 的 messages 数组
        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", SYSTEM_PROMPT),
                Map.of("role", "user", "content", userMessage)
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", actualModel);
        body.put("stream", true);
        body.put("input", Map.of("messages", messages));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("result_format", "message");
        parameters.put("incremental_output", true);
        body.put("parameters", parameters);

        log.debug("[DashScope] 请求模型: {}, Prompt长度: {}", actualModel, userMessage.length());

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnSubscribe(sub -> log.info("[DashScope] 开始流式请求"))
                .doOnComplete(() -> log.info("[DashScope] 流式响应完成"))
                .doOnError(err -> log.error("[DashScope] 请求出错", err))
                .flatMap(line -> {
                    if (line.startsWith("data:")) {
                        String data = line.substring(5).trim();
                        if ("[DONE]".equals(data)) {
                            return Flux.just("data: [DONE]\n\n");
                        }
                        try {
                            Map<String, Object> json = objectMapper.readValue(data, Map.class);
                            String content = extractQwenContent(json);
                            if (content != null && !content.isEmpty()) {
                                return Flux.just("data: {\"content\": " + jsonEscape(content) + "}\n\n");
                            }
                            return Flux.empty();
                        } catch (Exception e) {
                            log.warn("[DashScope] 解析响应片段失败: {}", data, e);
                            return Flux.just("data: {\"content\": \"解析错误\"}\n\n");
                        }
                    } else {
                        // 兼容非标准的完整 JSON 返回（非流式模式）
                        try {
                            Map<String, Object> json = objectMapper.readValue(line, Map.class);
                            String content = extractQwenContent(json);
                            if (content != null) {
                                return Flux.just(
                                        "data: {\"content\": " + jsonEscape(content) + "}\n\n",
                                        "data: [DONE]\n\n"
                                );
                            }
                            return Flux.just("data: {\"content\": \"\"}\n\n", "data: [DONE]\n\n");
                        } catch (Exception e) {
                            log.warn("[DashScope] 解析完整响应失败: {}", line, e);
                            return Flux.just("data: {\"content\": \"解析错误\"}\n\n");
                        }
                    }
                })
                .concatWith(Flux.just("data: [DONE]\n\n"));
    }

    // ==================== DeepSeek 调用 ====================

    /**
     * 调用 DeepSeek 流式接口（OpenAI 兼容格式），已注入系统提示词
     */
    private Flux<String> callDeepSeek(String userMessage, String customModel) {
        String actualModel = (customModel != null && !customModel.isEmpty()) ? customModel : llmConfig.getDeepseekModel();
        WebClient webClient = llmConfig.getWebClient("deepseek");

        // 构造包含 system 和 user 的 messages 数组
        List<Map<String, String>> messages = List.of(
                Map.of("role", "system", "content", SYSTEM_PROMPT),
                Map.of("role", "user", "content", userMessage)
        );

        Map<String, Object> body = new HashMap<>();
        body.put("model", actualModel);
        body.put("stream", true);
        body.put("messages", messages);

        log.debug("[DeepSeek] 请求模型: {}, Prompt长度: {}", actualModel, userMessage.length());

        return webClient.post()
                .bodyValue(body)
                .retrieve()
                .bodyToFlux(String.class)
                .doOnSubscribe(sub -> log.info("[DeepSeek] 开始流式请求"))
                .doOnComplete(() -> log.info("[DeepSeek] 流式响应完成"))
                .doOnError(err -> log.error("[DeepSeek] 请求出错", err))
                .flatMap(line -> {
                    String trimmed = line.trim();
                    if (trimmed.startsWith("data:")) {
                        trimmed = trimmed.substring(5).trim();
                    }
                    if (trimmed.isEmpty()) {
                        return Flux.empty();
                    }
                    if ("[DONE]".equals(trimmed)) {
                        return Flux.just("data: [DONE]\n\n");
                    }
                    try {
                        Map<String, Object> json = objectMapper.readValue(trimmed, Map.class);
                        String content = extractDeepSeekContent(json);
                        if (content != null && !content.isEmpty()) {
                            return Flux.just("data: {\"content\": " + jsonEscape(content) + "}\n\n");
                        }
                        return Flux.empty();
                    } catch (Exception e) {
                        log.warn("[DeepSeek] 解析响应失败: {}", trimmed, e);
                        return Flux.just("data: {\"content\": \"解析错误\"}\n\n");
                    }
                })
                .concatWith(Flux.just("data: [DONE]\n\n"));
    }

    // ==================== 内容提取工具方法 ====================

    /**
     * 从通义千问的响应 JSON 中提取 content 字段
     */
    private String extractQwenContent(Map<String, Object> json) {
        Map<String, Object> output = (Map<String, Object>) json.get("output");
        if (output != null && output.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                Object content = message.get("content");
                return content != null ? content.toString() : "";
            }
        }
        return "";
    }

    /**
     * 从 DeepSeek 响应 JSON 中提取 delta.content 字段
     */
    private String extractDeepSeekContent(Map<String, Object> json) {
        List<Map<String, Object>> choices = (List<Map<String, Object>>) json.get("choices");
        if (choices != null && !choices.isEmpty()) {
            Map<String, Object> delta = (Map<String, Object>) choices.get(0).get("delta");
            if (delta != null && delta.containsKey("content")) {
                Object content = delta.get("content");
                return content != null ? content.toString() : "";
            }
        }
        return "";
    }

    /**
     * JSON 字符串转义，用于安全地嵌入 SSE 数据
     */
    private String jsonEscape(String raw) {
        if (raw == null) return "\"\"";
        return "\"" + raw.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t") + "\"";
    }
}