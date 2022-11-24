package com.photo.photo_backend.config;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport{
    // 添加自定义的过滤器
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor()).addPathPatterns("/**");
    }
    // 头像路径
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        ApplicationHome home = new ApplicationHome(this.getClass());
        String path1 = home.getSource().getParent()+"\\upload\\image\\avatar\\";
        String path2 = home.getSource().getParent()+"\\upload\\image\\photos\\";
        String path3 = home.getSource().getParent()+"\\upload\\image\\thumbnail\\";
        registry.addResourceHandler("/avatar/**").addResourceLocations("file:"+path1);
        registry.addResourceHandler("/photos/**").addResourceLocations("file:"+path2);
        registry.addResourceHandler("/thumbnail/**").addResourceLocations("file:"+path3);
        super.addResourceHandlers(registry);
    }
}