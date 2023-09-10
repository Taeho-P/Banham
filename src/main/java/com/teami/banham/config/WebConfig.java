package com.teami.banham.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
    private String resourcePath = "/upload/**"; // view에서 접근할 경로
    private String savePath = "file:///C:/banham_files/"; // 실제 파일 저장 경로

    private String resourceProfilePath = "/profile/**"; // view에서 접근할 경로

    private String saveProfilePath = "file:///C:/banham_files/profile_img/"; // 실제 파일 저장 경로
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(resourcePath).addResourceLocations(savePath);

        registry.addResourceHandler(resourceProfilePath).addResourceLocations(saveProfilePath);
    }
}
