package net.factoryopen.myfactoryai.web.ai;

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
 * DeepSeek 接入配置。系统里只有两处用到大模型：业务问答与预警解读，都走这里。
 *
 * <p>DeepSeek 使用 OpenAI 兼容协议，但配置项统一放在 {@code ai.deepseek.*} 下，
 * 不再沿用 {@code spring.ai.openai.*} 那种容易误解的命名。</p>
 */
@Configuration
public class DeepSeekConfig {

    private static final Logger log = LoggerFactory.getLogger(DeepSeekConfig.class);

    @Value("${ai.deepseek.api-key:unset}")
    private String apiKey;

    /** 官方为 https://api.deepseek.com，带不带 /v1 都可用；换中转站改这里 */
    @Value("${ai.deepseek.base-url:https://api.deepseek.com/v1}")
    private String baseUrl;

    /**
     * deepseek-chat / deepseek-reasoner 已于 2026-07-24 弃用，
     * 现役是 deepseek-v4-flash（快、便宜）与 deepseek-v4-pro（强推理）。
     */
    @Value("${ai.deepseek.model:deepseek-v4-flash}")
    private String model;

    @Value("${ai.deepseek.timeout.connect-ms:5000}")
    private int connectTimeoutMs;

    @Value("${ai.deepseek.timeout.read-ms:30000}")
    private int readTimeoutMs;

    @PostConstruct
    void logReadiness() {
        if (isConfigured()) {
            log.info("[AI] DeepSeek 已就绪 model={} endpoint={}", model, endpoint());
        } else {
            log.warn("[AI] 未检测到 DeepSeek API Key，AI 问答与预警解读会降级为数据摘要。"
                    + "设置环境变量 OPENAI_API_KEY 后重启后端即可启用。");
        }
    }

    /**
     * 密钥是否真的可用。配置默认值是 {@code unset}，不能当成已配置，
     * 否则每次提问都要白打一次 401 请求。
     */
    public boolean isConfigured() {
        String key = apiKey == null ? "" : apiKey.trim();
        return !key.isEmpty()
                && !"unset".equalsIgnoreCase(key)
                && !key.contains("CHANGE_ME")
                && !key.startsWith("your-")
                && !key.startsWith("sk-placeholder");
    }

    public String getModel() { return model; }

    /** 把 base-url 补全成完整的 chat/completions 地址，允许写根路径、/v1 或完整地址 */
    String endpoint() {
        String base = baseUrl == null ? "" : baseUrl.trim();
        if (base.isEmpty()) base = "https://api.deepseek.com/v1";
        while (base.endsWith("/")) base = base.substring(0, base.length() - 1);
        if (base.endsWith("/chat/completions")) return base;
        return base + "/chat/completions";
    }

    @Bean("deepSeekWebClient")
    public WebClient deepSeekWebClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeoutMs)
                .responseTimeout(Duration.ofMillis(readTimeoutMs));
        return WebClient.builder()
                .baseUrl(endpoint())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }
}
