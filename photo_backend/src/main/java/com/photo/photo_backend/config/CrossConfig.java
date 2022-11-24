package com.photo.photo_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CrossConfig {
    // 配置跨域
     @Bean
     public WebMvcConfigurer corsConfigurer() {
         return new WebMvcConfigurer() {
             @Override
             public void addCorsMappings(CorsRegistry registry) {
                 registry.addMapping("/**")
                         .allowedOrigins("*")
                         .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
                         .allowCredentials(true)
                         .maxAge(3600)
                         .allowedHeaders("*");
             }
         };
     }
}
