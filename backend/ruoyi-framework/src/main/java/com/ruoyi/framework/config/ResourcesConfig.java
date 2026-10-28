package com.ruoyi.framework.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.framework.interceptor.RepeatSubmitInterceptor;

/**
 * 通用配置
 *
 * @author ruoyi
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {
    @Autowired
    private RepeatSubmitInterceptor repeatSubmitInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /** 本地文件上传路径 */
        registry.addResourceHandler(Constants.RESOURCE_PREFIX + "/**")
                .addResourceLocations("file:" + RuoYiConfig.getProfile() + "/");

        // 说明：这里原来有一条 springfox 时代的 /swagger-ui/** → classpath:...webjars/springfox-swagger-ui/
        // 映射，那个目录早已不存在，而且它会挡住 springdoc 自己的 /swagger-ui/**（含 swagger-initializer.js
        // 的重写逻辑，负责把界面默认地址改成 /v3/api-docs）。删掉后 Swagger UI 才能正确连到本项目的文档。

        // hls静态资源映射
        registry.addResourceHandler("/hls/**")
                .addResourceLocations("file:///d:/hls/");
    }

    /**
     * 接口文档入口的容错跳转。
     *
     * <p>页面本身在 {@code static/swagger/index.html}，只有 {@code /swagger/} 这种「目录 + 斜杠」的写法
     * 才会命中欢迎页。少写斜杠时 Spring 会把它当成一个叫 {@code swagger} 的静态资源去找，
     * 找不到就抛 NoResourceFoundException，被全局异常处理器包成
     * {@code {"msg":"No static resource swagger.","code":500}} —— 答辩现场最容易踩这个坑。</p>
     *
     * <p>这里显式映射三种写法，另外给一个短地址 {@code /doc}，口头念起来也方便。</p>
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger").setViewName("forward:/swagger/index.html");
        registry.addViewController("/swagger/").setViewName("forward:/swagger/index.html");
        registry.addViewController("/doc").setViewName("forward:/swagger/index.html");
    }

    /**
     * 自定义拦截规则
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(repeatSubmitInterceptor).addPathPatterns("/**");
    }

    /**
     * 允许跨域的来源，逗号分隔。默认仅允许本地前端开发端口，生产环境用环境变量 CORS_ALLOWED_ORIGINS 覆盖。
     */
    @Value("#{'${cors.allowed-origins:http://localhost:5173,http://127.0.0.1:5173}'.split(',')}")
    private List<String> allowedOrigins;

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址（白名单，禁止与 allowCredentials 同时放开全部）
        allowedOrigins.forEach(config::addAllowedOriginPattern);
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
