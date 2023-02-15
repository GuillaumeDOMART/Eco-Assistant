package com.ecoassitant.back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig to avoid cord problem
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost","http://localhost:8001","http://localhost:80","http://nginx:80", "http://nginx:80","https://eco-assistant-esipe.fr")
                .allowedMethods("*")
                .allowedHeaders("*").exposedHeaders("*");
    }
}
