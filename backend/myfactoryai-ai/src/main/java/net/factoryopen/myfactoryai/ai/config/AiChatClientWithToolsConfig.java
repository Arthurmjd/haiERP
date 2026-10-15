package net.factoryopen.myfactoryai.ai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 带工具调用能力的 ChatClient 配置
 * 该 Bean 使用 @Qualifier("chatClientWithTools") 区分，不作为默认注入
 */
@Configuration
public class AiChatClientWithToolsConfig {

    @Bean
    @Qualifier("chatClientWithTools")
    public ChatClient chatClientWithTools(ChatClient.Builder builder,
                                          ToolCallbackProvider toolCallbackProvider) {
        return builder
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }
}