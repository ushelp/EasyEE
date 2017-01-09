package cn.easyproject.easyee.sh.base.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter  {
	
	@Bean
	public StandardServletMultipartResolver multipartResolver() {
		StandardServletMultipartResolver ssmr = new StandardServletMultipartResolver();
		return ssmr;
	}
	
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// Your view controllers
		registry.addViewController("/easyshiro/locklogin").setViewName("locklogin/admin");
		registry.addViewController("/echartsDemo").setViewName("jsp/echarts/doChart");
		registry.addViewController("/captcha").setViewName("jsp/VerifyCode");
		super.addViewControllers(registry);
	}
	
}
