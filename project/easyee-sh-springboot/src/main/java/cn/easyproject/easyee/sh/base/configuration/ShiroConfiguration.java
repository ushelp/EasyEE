package cn.easyproject.easyee.sh.base.configuration;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.DispatcherType;

import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.easyproject.easyshiro.EasyFormAuthenticationFilter;
import cn.easyproject.easyshiro.EasyLogoutFilter;
import cn.easyproject.easyshiro.EasyURLPermissionFilter;  
  
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */  
@Configuration  
public class ShiroConfiguration {  
  
	/**
	 * Shiro Core FilterRegistrationBean: Shiro DelegatingFilterProxy
	 * 
	 * @return
	 */
	@Bean 
	public FilterRegistrationBean shiroFilterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy("shiroFilter");
		delegatingFilterProxy.setTargetFilterLifecycle(true);
		filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE,
				DispatcherType.ERROR);
		filterRegistration.setFilter(delegatingFilterProxy);
		List<String> urlPatterns = new ArrayList<String>();
		urlPatterns.add("/*");
		filterRegistration.setUrlPatterns(urlPatterns);
		return filterRegistration;
	}

   
    /**
     * 取消 Shiro Filter 的/*自动注册行为
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean disableRegistrationLogout(EasyLogoutFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean(filter);
        registration.setEnabled(false);
        return registration;
    }
    /**
     * 取消 Shiro Filter 的/*自动注册行为
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean disableRegistrationPerms(EasyURLPermissionFilter filter) {
    	FilterRegistrationBean registration = new FilterRegistrationBean(filter);
    	registration.setEnabled(false);
    	return registration;
    }
    /**
     * 取消 Shiro Filter 的/*自动注册行为
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean disableRegistrationAuth(EasyFormAuthenticationFilter filter) {
    	FilterRegistrationBean registration = new FilterRegistrationBean(filter);
    	registration.setEnabled(false);
    	return registration;
    }
    /**
     * 取消 Shiro Filter 的/*自动注册行为
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean disableRegistrationShiroFilter(AbstractShiroFilter filter) {
    	FilterRegistrationBean registration = new FilterRegistrationBean(filter);
    	registration.setEnabled(false);
    	return registration;
    }

    

  
}  