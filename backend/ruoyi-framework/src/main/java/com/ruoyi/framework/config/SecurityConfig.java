package com.ruoyi.framework.config;

import com.ruoyi.framework.config.properties.PermitAllUrlProperties;
import com.ruoyi.framework.security.filter.JwtAuthenticationTokenFilter;
import com.ruoyi.framework.security.handle.AuthenticationEntryPointImpl;
import com.ruoyi.framework.security.handle.LogoutSuccessHandlerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private PermitAllUrlProperties permitAllUrl;

    @Autowired
    private AuthenticationEntryPointImpl unauthorizedHandler;

    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    @Autowired
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    @Autowired
    private CorsFilter corsFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 配置白名单匿名访问
        http.authorizeHttpRequests(auth -> {
            // 从配置文件中读取的白名单URL
            permitAllUrl.getUrls().forEach(url -> auth.requestMatchers(url).permitAll());

            // 登录、注册、验证码
            auth.requestMatchers("/login", "/register", "/captchaImage").permitAll();

            // 静态资源
            //auth.requestMatchers(HttpMethod.GET, "/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/profile/**").permitAll();
            // 修正静态资源：删除 /**/*.html 等写法，改用目录通配
            auth.requestMatchers(HttpMethod.GET, "/", "/*.html", "/static/**", "/profile/**").permitAll();

            // Swagger / OpenAPI 文档（UI 在 /swagger/，短地址 /doc，规范在 /v3/api-docs/**）
            auth.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/swagger", "/swagger/**", "/doc",
                    "/swagger-resources/**", "/webjars/**", "/v3/api-docs", "/v3/api-docs/**", "/*/api-docs",
                    "/druid/**").permitAll();

            // WebSocket
            auth.requestMatchers("/websocket/**", "/websocket", "/rtsp/**").permitAll();

            // Jimureport
            auth.requestMatchers("/jmreport/**").permitAll();

            // 其他所有请求需要认证
            auth.anyRequest().authenticated();
        });

        // CSRF禁用
        http.csrf(csrf -> csrf.disable());

        // 禁用HTTP响应标头缓存
        http.headers(headers -> headers.cacheControl(cache -> cache.disable()).frameOptions(frame -> frame.disable()));

        // 认证失败处理
        http.exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandler));

        // 无状态session
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 退出登录
        http.logout(logout -> logout.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));

        // 添加过滤器
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        http.addFilterBefore(corsFilter, LogoutFilter.class);

        return http.build();
    }
}
