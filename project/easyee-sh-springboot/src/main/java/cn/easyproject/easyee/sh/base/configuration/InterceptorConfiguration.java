package cn.easyproject.easyee.sh.base.configuration;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import cn.easyproject.easyee.sh.sys.interceptor.LogInterceptor;
import cn.easyproject.easyee.sh.sys.service.SysLogService;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurerAdapter {

	@Resource
	SysLogService sysLogService;
	
	/**
	 * Interceptors
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// Log HandlerInterceptor
		LogInterceptor logInterceptor=new LogInterceptor();
		logInterceptor.setSysLogService(sysLogService); // 手动注入
		registry.addInterceptor(logInterceptor).addPathPatterns("/**");

		// I18N HandlerInterceptor
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

}
