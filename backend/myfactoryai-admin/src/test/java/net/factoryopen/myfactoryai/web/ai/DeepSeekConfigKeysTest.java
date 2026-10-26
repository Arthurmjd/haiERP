package net.factoryopen.myfactoryai.web.ai;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.yaml.snakeyaml.Yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 配置键与代码里的 @Value 必须严格对应。
 *
 * <p>踩过的坑：yml 里 {@code ai:} 少缩进了两格，实际路径变成
 * {@code spring.ai.deepseek.api-key}，而代码读的是 {@code ai.deepseek.api-key}，
 * @Value 找不到就静默用默认值 unset —— 表现是"配了密钥却说没检测到"。
 * 这种错编译不报错、启动也不报错，只有看日志才发现，所以用测试钉住。</p>
 */
class DeepSeekConfigKeysTest {

    @SuppressWarnings("unchecked")
    private static Map<String, Object> yaml() {
        try (InputStream in = DeepSeekConfigKeysTest.class.getResourceAsStream("/application.yml")) {
            assertNotNull(in, "classpath 下找不到 application.yml");
            return new Yaml().load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    void aiSectionIsTopLevelNotUnderSpring() {
        Map<String, Object> root = yaml();
        assertTrue(root.containsKey("ai"), "application.yml 顶层应有 ai 段，实际顶层键： " + root.keySet());

        Object spring = root.get("spring");
        assertTrue(spring instanceof Map, "spring 应为 Map");
        assertTrue(!((Map<String, Object>) spring).containsKey("ai"),
                "ai 段若缩进在 spring 下面，属性路径会变成 spring.ai.*，与代码读取的路径不符");
    }

    @Test
    void everyConfigKeyUsedByCodeExistsInYaml() {
        Map<String, Object> root = yaml();
        Map<String, Object> ai = (Map<String, Object>) root.get("ai");
        assertNotNull(ai, "缺少 ai 段");
        Map<String, Object> deepseek = (Map<String, Object>) ai.get("deepseek");
        assertNotNull(deepseek, "缺少 ai.deepseek 段");

        // 代码里 @Value 引用的键，必须在 yml 里真实存在
        for (Field field : DeepSeekConfig.class.getDeclaredFields()) {
            Value value = field.getAnnotation(Value.class);
            if (value == null) continue;
            String key = value.value();
            int colon = key.indexOf(':');
            String path = colon > 0 ? key.substring(0, colon) : key;
            if (!path.startsWith("ai.")) continue;   // 只校验本类自己的配置键
            assertTrue(resolve(root, path) != null,
                    "代码引用了 " + path + "，但 application.yml 里没有这个键");
        }
    }

    @Test
    void timeoutKeysArePresent() {
        Map<String, Object> root = yaml();
        Map<String, Object> timeout = (Map<String, Object>)
                ((Map<String, Object>) ((Map<String, Object>) root.get("ai")).get("deepseek")).get("timeout");
        assertNotNull(timeout, "缺少 ai.deepseek.timeout");
        assertEquals(5000, ((Number) timeout.get("connect-ms")).intValue());
        assertEquals(30000, ((Number) timeout.get("read-ms")).intValue());
    }

    @Test
    void apiKeyReadsFromEnvironmentVariable() {
        // 密钥必须来自环境变量，不能在 yml 里写死
        Map<String, Object> root = yaml();
        Map<String, Object> deepseek = (Map<String, Object>)
                ((Map<String, Object>) root.get("ai")).get("deepseek");
        assertEquals("${OPENAI_API_KEY:unset}", deepseek.get("api-key"));
        assertEquals("${OPENAI_BASE_URL:https://api.deepseek.com/v1}", deepseek.get("base-url"));
        assertEquals("${OPENAI_MODEL:deepseek-v4-flash}", deepseek.get("model"));
    }

    /** 按 a.b.c 逐层取值，取不到返回 null */
    @SuppressWarnings("unchecked")
    private static Object resolve(Map<String, Object> root, String path) {
        Object current = root;
        for (String part : path.split("\\.")) {
            if (!(current instanceof Map)) return null;
            current = ((Map<String, Object>) current).get(part);
            if (current == null) return null;
        }
        return current;
    }
}
