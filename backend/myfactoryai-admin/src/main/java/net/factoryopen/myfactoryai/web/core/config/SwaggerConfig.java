package net.factoryopen.myfactoryai.web.core.config;

import com.ruoyi.common.config.RuoYiConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置（基于 SpringDoc OpenAPI 3）
 *
 * @author ruoyi
 */
@Configuration
@ConditionalOnProperty(name = "swagger.enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Autowired
    private RuoYiConfig ruoyiConfig;

    @Value("${swagger.pathMapping:}")
    private String pathMapping;

    /**
     * 自定义 OpenAPI 文档信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        // 联系人信息
        Contact contact = new Contact()
                .name(ruoyiConfig.getName())
                .url(null)
                .email(null);

        // 文档标题、描述、版本
        Info info = new Info()
                .title("标题：若依管理系统_接口文档")
                .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                .contact(contact)
                .version("版本号:" + ruoyiConfig.getVersion());

        // 配置全局安全头（Authorization Token）
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
     * 如果配置了 pathMapping，则需要通过 OpenApiCustomizer 添加路径前缀
     * （SpringDoc 默认不支持全局 pathMapping，这里通过自定义实现）
     */
    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            if (pathMapping != null && !pathMapping.isEmpty()) {
                // 修改 servers 列表，添加 server URL
                openApi.addServersItem(new io.swagger.v3.oas.models.servers.Server().url(pathMapping));
            }
        };
    }
}