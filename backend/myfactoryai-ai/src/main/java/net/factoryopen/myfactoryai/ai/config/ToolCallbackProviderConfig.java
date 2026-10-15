package net.factoryopen.myfactoryai.ai.config;

import net.factoryopen.myfactoryai.ai.tools.FactoryTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具回调提供者配置
 * 负责将业务方法（如 FactoryTools 中的方法）封装为可供大模型调用的 ToolCallback
 * 当前为空实现，后续可按需添加具体工具（工单查询、良率查询等）
 */
@Configuration
public class ToolCallbackProviderConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(FactoryTools factoryTools) {
        // 目前未启用具体工具，返回空数组
        // 后续可在此处通过 MethodToolCallback.builder() 添加工单详情、产线良率等工具
        return () -> new org.springframework.ai.tool.ToolCallback[]{};

//        // 工单详情工具
//        ToolCallback workOrderCallback = MethodToolCallback.builder()
//                .toolMethod(factoryTools, "getWorkOrderDetail")
//                .description("根据工单ID获取工单的详细信息")
//                .inputType(String.class)   // 参数类型，如果需要参数名可进一步配置
//                .build();
//
//        // 产线良率工具
//        ToolCallback yieldCallback = MethodToolCallback.builder()
//                .toolMethod(factoryTools, "getProductionLineYield")
//                .description("查询指定生产线的当前良率")
//                .inputType(String.class)
//                .build();
//
//        return () -> new ToolCallback[]{workOrderCallback, yieldCallback};

    }
}