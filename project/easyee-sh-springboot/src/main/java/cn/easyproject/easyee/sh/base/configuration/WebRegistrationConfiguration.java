package cn.easyproject.easyee.sh.base.configuration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import cn.easyproject.easyfilter.filter.EasyFilter;
import net.bull.javamelody.MonitoringFilter;
import net.bull.javamelody.SessionListener;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Configuration
public class WebRegistrationConfiguration {

	
	/**
	 * EasyFilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean easyContentFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		EasyFilter contentFilter = new EasyFilter();
		contentFilter.charset = "utf-8";
		registrationBean.setFilter(contentFilter);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		registrationBean.setUrlPatterns(urlPatterns);
		return registrationBean;
	}
	
	
	
	/**
	 * CKFinder Servlet
	 * @return
	 */
	@Bean
	public ServletRegistrationBean ckfinderConnectorServletRegistrationBean() {
		ServletRegistrationBean registrationBean = new ServletRegistrationBean();
		registrationBean.addInitParameter("XMLConfig", "/WEB-INF/ckfinder-config.xml");
		registrationBean.addInitParameter("debug", "false");
		registrationBean.setOrder(1);
		// Jars in "WEB-INF/lib/", please add to build path for development
		registrationBean.setServlet(new com.ckfinder.connector.ConnectorServlet());
		
		registrationBean.addUrlMappings("/staticresources/ckfinder/core/connector/java/connector.java");
		return registrationBean;
	}

}
