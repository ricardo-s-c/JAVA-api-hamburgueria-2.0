package com.vipsoftcom.apihamburgueria20.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		.allowedOrigins("http://localhost:5173","http://localhost:8080", "http://localhost:5432")
		.allowedOrigins("*")
		.allowedHeaders("*")
		.allowedMethods("GET", "POST", "PUT", "DELETE") 
		.exposedHeaders("*");
		
//		WebMvcConfigurer.super.addCorsMappings(registry);
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**");
	}
}
