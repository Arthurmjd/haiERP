package net.factoryopen.myfactoryai.ai.config;

import java.time.Duration;

import io.netty.channel.ChannelOption;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

/**
 * 大模型接入配置。
 *
 * <p>DeepSeek 使用 OpenAI 兼容格式，所以沿用 {@code spring.ai.openai.*} 这组配置项：
 * {@code api-key} 填密钥，{@code base-url} 决定打到官方还是中转站，
 * {@code chat.options.model} 决定模型。以后换供应商通常只改这三个值。</p>
 */
@Configuration
public class LLMConfig {

    private static final Logger log = LoggerFactory.getLogger(LLMConfig.class);

    // ---------- 配置属性 ----------
    @Value("${spring.ai.dashscope.api-key:unset}")
    private String dashscopeApiKey;

    @Value("${spring.ai.dashscope.chat.options.model:qwen-plus}")
    private String qwenModel;

    @Value("${spring.ai.openai.api-key:unset}")
    private String deepseekApiKey;

    /** 官方为 https://api.deepseek.com，带不带 /v1 都可以；换中转站时填中转站地址 */
    @Value("${spring.ai.openai.base-url:https://api.deepseek.com/v1}")
    private String deepseekBaseUrl;

    /**
     * deepseek-chat / deepseek-reasoner 已于 2026-07-24 弃用，
     * 现役模型名是 deepseek-v4-flash（快且便宜）与 deepseek-v4-pro（强推理）。
     */
    @Value("${spring.ai.openai.chat.options.model:deepseek-v4-flash}")
    private String deepseekModel;

    @Value("${spring.ai.openai.timeout.connect-ms:5000}")
    private int connectTimeoutMs;

    @Value("${spring.ai.openai.timeout.read-ms:30000}")
    private int readTimeoutMs;

    @PostConstruct
    void logAiReadiness() {
        if (isDeepseekConfigured()) {
            log.info("[AI] DeepSeek 已就绪 model={} endpoint={}", deepseekModel, deepseekEndpoint());
        } else {
            log.warn("[AI] 未检测到 DeepSeek API Key，AI 问答与预警解读会降级为数据摘要。"
                    + "设置环境变量 OPENAI_API_KEY 后重启后端即可启用。");
        }
    }

    /**
     * 密钥是否真的可用。application.yml 的默认值是 {@code unset}，
     * 不能当成已配置，否则每次提问都会白打一次 401 请求。
     */
    public boolean isDeepseekConfigured() {
        String key = deepseekApiKey == null ? "" : deepseekApiKey.trim();
        return !key.isEmpty()
                && !"unset".equalsIgnoreCase(key)
                && !key.contains("CHANGE_ME")
                && !key.startsWith("your-")
                && !key.startsWith("sk-placeholder");
    }

    /** 把 base-url 补全成完整的 chat/completions 地址，避免中转站地址少写一段路径 */
    String deepseekEndpoint() {
        String base = deepseekBaseUrl == null ? "" : deepseekBaseUrl.trim();
        if (base.isEmpty()) base = "https://api.deepseek.com/v1";
        while (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        if (base.endsWith("/chat/completions")) return base;
        return base + "/chat/completions";
    }

    private WebClient.Builder builder(String baseUrl, String apiKey) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofMillis(readTimeoutMs));
        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + apiKey);
    }

    // ---------- WebClient Bean 定义 ----------
    @Bean(name = "dashscopeWebClient")
    public WebClient dashscopeWebClient() {
        return builder("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation",
                dashscopeApiKey).build();
    }

    @Bean(name = "deepseekWebClient")
    public WebClient deepseekWebClient() {
        return builder(deepseekEndpoint(), deepseekApiKey).build();
    }

    // ---------- 工具方法：根据 provider 获取对应的 WebClient ----------
    public WebClient getWebClient(String provider) {
        if ("deepseek".equalsIgnoreCase(provider)) {
            return deepseekWebClient();
        } else {
            return dashscopeWebClient();
        }
    }

    // ---------- Getter 方法 ----------
    public String getDashscopeApiKey() { return dashscopeApiKey; }
    public String getQwenModel() { return qwenModel; }
    public String getDeepseekApiKey() { return deepseekApiKey; }
    public String getDeepseekModel() { return deepseekModel; }
    public String getDeepseekBaseUrl() { return deepseekBaseUrl; }
}
