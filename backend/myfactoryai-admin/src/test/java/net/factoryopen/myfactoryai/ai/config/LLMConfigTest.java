package net.factoryopen.myfactoryai.ai.config;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 覆盖 base-url 拼接与"密钥是否可用"的判断。
 * 这两处一旦写错，表现是 AI 静默降级成数据摘要，从日志上很难看出来。
 */
class LLMConfigTest {

    private static LLMConfig config(String apiKey, String baseUrl) {
        LLMConfig config = new LLMConfig();
        set(config, "deepseekApiKey", apiKey);
        set(config, "deepseekBaseUrl", baseUrl);
        return config;
    }

    private static void set(LLMConfig target, String field, String value) {
        try {
            Field f = LLMConfig.class.getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void endpointAppendsChatCompletionsToV1Base() {
        assertEquals("https://api.deepseek.com/v1/chat/completions",
                config("sk-x", "https://api.deepseek.com/v1").deepseekEndpoint());
    }

    @Test
    void endpointWorksWithoutV1Segment() {
        assertEquals("https://api.deepseek.com/chat/completions",
                config("sk-x", "https://api.deepseek.com").deepseekEndpoint());
    }

    @Test
    void endpointToleratesTrailingSlash() {
        assertEquals("https://api.deepseek.com/v1/chat/completions",
                config("sk-x", "https://api.deepseek.com/v1/").deepseekEndpoint());
    }

    @Test
    void endpointKeepsFullUrlAsIs() {
        assertEquals("https://relay.example.com/v1/chat/completions",
                config("sk-x", "https://relay.example.com/v1/chat/completions").deepseekEndpoint());
    }

    @Test
    void endpointFallsBackToOfficialWhenBlank() {
        assertEquals("https://api.deepseek.com/v1/chat/completions",
                config("sk-x", "  ").deepseekEndpoint());
    }

    @Test
    void placeholderKeysCountAsNotConfigured() {
        assertFalse(config("unset", "https://api.deepseek.com/v1").isDeepseekConfigured());
        assertFalse(config("", "https://api.deepseek.com/v1").isDeepseekConfigured());
        assertFalse(config(null, "https://api.deepseek.com/v1").isDeepseekConfigured());
        assertFalse(config("your-api-key", "https://api.deepseek.com/v1").isDeepseekConfigured());
        assertFalse(config("sk-placeholder", "https://api.deepseek.com/v1").isDeepseekConfigured());
        assertFalse(config("CHANGE_ME", "https://api.deepseek.com/v1").isDeepseekConfigured());
    }

    @Test
    void realKeyCountsAsConfigured() {
        assertTrue(config("sk-0123456789abcdef", "https://api.deepseek.com/v1").isDeepseekConfigured());
        // 首尾空格要能容忍，从 PowerShell 环境变量拷过来常带空白
        assertTrue(config("  sk-0123456789abcdef  ", "https://api.deepseek.com/v1").isDeepseekConfigured());
    }
}
