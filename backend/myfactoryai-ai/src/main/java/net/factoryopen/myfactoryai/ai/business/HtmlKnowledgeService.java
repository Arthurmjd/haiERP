package net.factoryopen.myfactoryai.ai.business;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class HtmlKnowledgeService {

    private static final Logger log = LoggerFactory.getLogger(HtmlKnowledgeService.class);

    @Value("${ai.business.knowledge.urls:}")
    private List<String> knowledgeUrls;

    @Value("${ai.business.knowledge.cache-minutes:60}")
    private long cacheMinutes;

    @Value("${ai.business.knowledge.timeout-seconds:10}")
    private int timeoutSeconds;

    private final AtomicReference<String> cachedText = new AtomicReference<>();
    private volatile long lastUpdateTime = 0;

    /**
     * 获取合并后的知识库纯文本（带缓存）
     */
    public String getKnowledgeText() {
        long now = System.currentTimeMillis();
        if (cachedText.get() != null && (now - lastUpdateTime) < TimeUnit.MINUTES.toMillis(cacheMinutes)) {
            log.debug("使用缓存的知识库，缓存年龄 {} 分钟", (now - lastUpdateTime) / 60000);
            return cachedText.get();
        }
        return refreshCache();
    }

    /**
     * 手动刷新缓存（从所有配置的 URL 拉取并合并）
     */
    public synchronized String refreshCache() {
        if (knowledgeUrls == null || knowledgeUrls.isEmpty()) {
            log.warn("未配置知识库 URL，请检查 ai.business.knowledge.urls");
            return "";
        }

        log.info("开始刷新知识库缓存，共 {} 个文档源", knowledgeUrls.size());
        StringBuilder merged = new StringBuilder();

        for (String url : knowledgeUrls) {
            try {
                String html = fetchHtml(url);
                String text = extractTextFromHtml(html);
                merged.append("\n\n========== 文档来源：").append(url).append(" ==========\n\n");
                merged.append(text);
                log.info("成功加载文档：{}，文本长度 {} 字符", url, text.length());
            } catch (IOException e) {
                log.error("加载文档失败：{}，错误：{}", url, e.getMessage());
                // 继续加载其他文档
            }
        }

        String result = merged.toString().trim();
        cachedText.set(result);
        lastUpdateTime = System.currentTimeMillis();
        log.info("知识库缓存刷新完成，总长度 {} 字符", result.length());
        return result;
    }

    private String fetchHtml(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .timeout(timeoutSeconds * 1000)
                .get();
        return doc.html();
    }

    private String extractTextFromHtml(String html) {
        Document doc = Jsoup.parse(html);
        // 移除脚本和样式
        doc.select("script, style").remove();
        // 提取 body 文本，如果没有 body 则取整个文档
        String text = doc.body() != null ? doc.body().text() : doc.text();
        // 压缩空白
        return text.replaceAll("\\s+", " ").trim();
    }
}