package com.cn.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>自定义后端跨域配置类</p>
 *
 * @author 咖啡不苦
 * @date 2024-07-21
 * @since 1.0
 */
@Configuration
public class WebCorsConfig implements WebMvcConfigurer {

    /**
     * 配置跨域映射规则
     * <p>addMapping: 映射规则路径</p>
     * <p>allowCredentials: 是否允许认证、发送cookie</p>
     * <p>allowOrigin：支持的跨域规则（SpringBoot 2.4.0版本以下）</p>
     * <p>allowedOriginPatterns: 支持的跨域规则</p>
     * <p>allowedMethods: 支持跨域的请求方式</p>
     * <p>allowedHeaders: 支持跨域的请求头信息</p>
     * <p>exposedHeaders: 响应时暴露的响应头信息</p>
     * <br />
     * <ui>
     * <span>默认暴露响应头</span>
     * <li>Cache-Control</li>
     * <li>Content-Language</li>
     * <li>Content-Type</li>
     * <li>Expires</li>
     * <li>Last-Modified</li>
     * <li>Pragma</li>
     * </ui>
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
//                .allowedOrigins("*")
                .allowedOriginPatterns("*")
                .allowedMethods("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Access-Control-Allow-Origin");
    }
}
