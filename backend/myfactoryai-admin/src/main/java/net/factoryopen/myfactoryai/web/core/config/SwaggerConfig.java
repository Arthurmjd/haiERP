package net.factoryopen.myfactoryai.web.core.config;

import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置（基于 SpringDoc OpenAPI 3 + knife4j UI）
 *
 * <p>访问入口：{@code http://127.0.0.1:8080/swagger/index.html}（Swagger UI，页面在
 * {@code static/swagger/index.html}，引用 springdoc 依赖自带的 swagger-ui 静态资源）。
 * 规范 JSON：{@code http://127.0.0.1:8080/v3/api-docs}。</p>
 *
 * <p>分组按业务域切分，答辩时直接点左侧分组，比在 260 个接口里翻要快。</p>
 *
 * @author ruoyi
 */
@Configuration
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Autowired
    private RuoYiConfig ruoyiConfig;

    /** 网关/反向代理前缀；本机直连后端时留空，否则「Try it out」会把前缀拼在地址上导致 404。 */
    @Value("${swagger.pathMapping:}")
    private String pathMapping;

    /**
     * 自定义 OpenAPI 文档信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact().name(ruoyiConfig.getName());

        Info info = new Info()
                .title("海岚 ERP · 服装制造企业资源计划 接口文档")
                .description("""
                        从报价到成本的一条订单主线，覆盖 9 个业务域。

                        **接口规模**：43 个 REST 控制器 / 260 个方法级接口 / 228 处 `@PreAuthorize` 权限注解。

                        **怎么调通**：
                        1. 先调 `POST /login`（此接口免登录），body 传 `{"username":"admin","password":"Demo@2026!"}`；
                        2. 从返回里复制 `token`；
                        3. 点右上角「Authorize / 认证」，把 token 粘进去（只填 token，不要加 `Bearer ` 前缀）；
                        4. 之后每个接口右上角的「Try it out / 调试」就能直接发真实请求。

                        **权限说明**：每个接口都挂了权限点，当前账号没有该权限时返回 HTTP 200 + body `code=403`，
                        不是 HTTP 403 —— 这是本项目统一的响应约定。""")
                .contact(contact)
                .version("v1.0 · 2026-10");

        // 全局安全头（Authorization Token）
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");
        Components components = new Components().addSecuritySchemes("Authorization", securityScheme);

        return new OpenAPI()
                .info(info)
                .components(components)
                .addSecurityItem(new SecurityRequirement().addList("Authorization"));
    }

    /**
     * 配置了 pathMapping 才追加 server 前缀（SpringDoc 不支持全局 pathMapping，用 customizer 补）。
     */
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            if (pathMapping != null && !pathMapping.isEmpty()) {
                openApi.addServersItem(new io.swagger.v3.oas.models.servers.Server().url(pathMapping));
            }
        };
    }

    // ---------------------------------------------------------------- 业务域分组
    // 分组名不要带 "/"，它会成为 /v3/api-docs/{group} 的路径段。

    @Bean
    public GroupedOpenApi salesApi() {
        return GroupedOpenApi.builder().group("01 销售：报价与订单").pathsToMatch("/erp/sd/**").build();
    }

    @Bean
    public GroupedOpenApi purchaseApi() {
        return GroupedOpenApi.builder().group("02 采购：缺料与收货").pathsToMatch("/erp/pu/**").build();
    }

    @Bean
    public GroupedOpenApi productionApi() {
        return GroupedOpenApi.builder().group("03 生产：工单与报工").pathsToMatch("/erp/pr/**").build();
    }

    @Bean
    public GroupedOpenApi stockApi() {
        return GroupedOpenApi.builder().group("04 库存：结存与流水").pathsToMatch("/erp/st/**").build();
    }

    @Bean
    public GroupedOpenApi shipmentApi() {
        return GroupedOpenApi.builder().group("05 出货：发货与退货").pathsToMatch("/erp/sh/**").build();
    }

    @Bean
    public GroupedOpenApi financeApi() {
        return GroupedOpenApi.builder().group("06 财务：应收应付与成本")
                .pathsToMatch("/erp/fn/**", "/erp/finance/**").build();
    }

    @Bean
    public GroupedOpenApi hrApi() {
        return GroupedOpenApi.builder().group("07 人事：员工与工资").pathsToMatch("/erp/hr/**").build();
    }

    @Bean
    public GroupedOpenApi masterApi() {
        return GroupedOpenApi.builder().group("08 主数据：款式物料客户").pathsToMatch("/erp/md/**").build();
    }

    @Bean
    public GroupedOpenApi systemApi() {
        return GroupedOpenApi.builder().group("09 系统与权限")
                .pathsToMatch("/system/**", "/monitor/**").build();
    }

    @Bean
    public GroupedOpenApi aiApi() {
        return GroupedOpenApi.builder().group("10 看板与 AI 问答")
                .pathsToMatch("/erp/dashboard/**", "/ai/**").build();
    }
}
