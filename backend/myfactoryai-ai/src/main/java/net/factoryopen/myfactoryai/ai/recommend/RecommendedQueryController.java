package net.factoryopen.myfactoryai.ai.recommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推荐会话控制器
 * 对外提供推荐查询列表，无需认证即可访问
 */
@RestController
@RequestMapping("/ai/recommended")
public class RecommendedQueryController {

    @Autowired
    private RecommendedQueryService recommendedQueryService;

    @GetMapping("/list")
    public Map<String, Object> list() {
        List<RecommendedQueryItem> items = recommendedQueryService.getRecommendedQueries();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("data", items);
        return result;
    }
}