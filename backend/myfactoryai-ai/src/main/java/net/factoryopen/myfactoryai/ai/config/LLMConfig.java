package net.factoryopen.myfactoryai.ai.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class LLMConfig {

    // ---------- 配置属性 ----------
    @Value("${spring.ai.dashscope.api-key}")
    private String dashscopeApiKey;

    @Value("${spring.ai.dashscope.chat.options.model:qwen-plus}")
    private String qwenModel;

    @Value("${spring.ai.openai.api-key}")
    private String deepseekApiKey;

    @Value("${spring.ai.openai.chat.options.model:deepseek-chat}")
    private String deepseekModel;

    // ---------- WebClient Bean 定义 ----------
    @Bean(name = "dashscopeWebClient")
    public WebClient dashscopeWebClient() {
        return WebClient.builder()
                .baseUrl("https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + dashscopeApiKey)
                .build();
    }

    @Bean(name = "deepseekWebClient")
    public WebClient deepseekWebClient() {
        return WebClient.builder()
                .baseUrl("https://api.deepseek.com/v1/chat/completions")
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + deepseekApiKey)
                .build();
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
}