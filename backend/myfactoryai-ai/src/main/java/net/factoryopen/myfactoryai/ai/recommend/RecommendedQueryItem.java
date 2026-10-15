package net.factoryopen.myfactoryai.ai.recommend;

public class RecommendedQueryItem {
    private String id;          // 唯一标识
    private String title;       // 显示标题（简短）
    private String displayText; // 显示在推荐清单上的描述文字（用户友好）
    private String queryText;   // 实际发送给 AI 的自然语言查询（精确，可含技术词）
    private String queryMode;   // "chat" 或 "chart"
    private String model;       // 可选，指定使用的模型

    @Deprecated
    private String content;     // 兼容旧版，实际不再使用

    public RecommendedQueryItem() {}

    // 新构造器：使用 displayText 和 queryText
    public RecommendedQueryItem(String id, String title, String displayText, String queryText, String queryMode, String model) {
        this.id = id;
        this.title = title;
        this.displayText = displayText;
        this.queryText = queryText;
        this.queryMode = queryMode;
        this.model = model;
        // 兼容旧字段
        this.content = displayText;
    }

    // getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDisplayText() { return displayText; }
    public void setDisplayText(String displayText) { this.displayText = displayText; }
    public String getQueryText() { return queryText; }
    public void setQueryText(String queryText) { this.queryText = queryText; }
    public String getQueryMode() { return queryMode; }
    public void setQueryMode(String queryMode) { this.queryMode = queryMode; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    @Deprecated
    public String getContent() { return content; }
    @Deprecated
    public void setContent(String content) { this.content = content; }
}