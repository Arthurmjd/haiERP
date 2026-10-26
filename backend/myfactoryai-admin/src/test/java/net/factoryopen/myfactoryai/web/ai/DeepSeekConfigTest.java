package net.factoryopen.myfactoryai.web.ai;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 覆盖 base-url 拼接与"密钥是否可用"的判断。
 * 这两处一旦写错，表现是 AI 静默降级成数据摘要，从日志上很难看出来。
 */
class DeepSeekConfigTest {

    private static DeepSeekConfig config(String apiKey, String baseUrl) {
        DeepSeekConfig config = new DeepSeekConfig();
        set(config, "apiKey", apiKey);
        set(config, "baseUrl", baseUrl);
        return config;
    }

    private static void set(DeepSeekConfig target, String field, String value) {
        try {
            Field f = DeepSeekConfig.class.getDeclaredField(field);
            f.setAccessible(true);
            f.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void endpointAppendsChatCompletionsToV1Base() {
        assertEquals("https://api.deepseek.com/v1/chat/completions",
                config("sk-x", "https://api.deepseek.com/v1").endpoint());
    }

    @Test
    void endpointWorksWithoutV1Segment() {
        assertEquals("https://api.deepseek.com/chat/completions",
                config("sk-x", "https://api.deepseek.com").endpoint());
    }

    @Test
    void endpointToleratesTrailingSlash() {
        assertEquals("https://api.deepseek.com/v1/chat/completions",
                config("sk-x", "https://api.deepseek.com/v1/").endpoint());
    }

    @Test
    void endpointKeepsFullUrlAsIs() {
        assertEquals("https://relay.example.com/v1/chat/completions",
                config("sk-x", "https://relay.example.com/v1/chat/completions").endpoint());
    }

    @Test
    void endpointFallsBackToOfficialWhenBlank() {
        assertEquals("https://api.deepseek.com/v1/chat/completions",
                config("sk-x", "  ").endpoint());
    }

    @Test
    void placeholderKeysCountAsNotConfigured() {
        assertFalse(config("unset", "https://api.deepseek.com/v1").isConfigured());
        assertFalse(config("", "https://api.deepseek.com/v1").isConfigured());
        assertFalse(config(null, "https://api.deepseek.com/v1").isConfigured());
        assertFalse(config("your-api-key", "https://api.deepseek.com/v1").isConfigured());
        assertFalse(config("sk-placeholder", "https://api.deepseek.com/v1").isConfigured());
        assertFalse(config("CHANGE_ME", "https://api.deepseek.com/v1").isConfigured());
    }

    @Test
    void realKeyCountsAsConfigured() {
        assertTrue(config("sk-0123456789abcdef", "https://api.deepseek.com/v1").isConfigured());
        // 首尾空格要能容忍，从 PowerShell 环境变量拷过来常带空白
        assertTrue(config("  sk-0123456789abcdef  ", "https://api.deepseek.com/v1").isConfigured());
    }
}
