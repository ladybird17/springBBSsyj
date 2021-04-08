package com.bitc.bbssyj.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bitc.bbssyj.interceptor.LoggerInterceptor;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		
		//파일의 인코딩 UTF-8
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		//최대 업로드 파일 크기 5 * 1024 * 1024 = 10mb
		commonsMultipartResolver.setMaxUploadSizePerFile(10*1024*1024);
		
		return commonsMultipartResolver;
	}
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor());
	}
}
