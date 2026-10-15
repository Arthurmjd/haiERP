package net.factoryopen.myfactoryai.ai.nl2sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ai/metadata")
@PreAuthorize("@ss.hasRole('admin')")
public class MetadataController {

    @Autowired
    private MetadataSchemaCache metadataCache;

    @PostMapping("/refresh")
    public Map<String, Object> refreshCache() {
        metadataCache.manualRefresh();
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "元数据缓存刷新已触发，请查看日志确认结果");
        return result;
    }
}
