package cn.easyproject.easyee.sm.base.configuration;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Configuration
public class MultipartConfiguration extends WebMvcConfigurerAdapter {
	
	 @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        long maxFileSize=10485760; //10MB
        long maxRequestSize=104857600; //100MB
        factory.setMaxFileSize(maxFileSize);
        factory.setMaxRequestSize(maxRequestSize);  
        return factory.createMultipartConfig();  
    }  
}
