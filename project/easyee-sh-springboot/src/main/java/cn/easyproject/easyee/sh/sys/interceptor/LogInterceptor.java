package cn.easyproject.easyee.sh.sys.interceptor;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sh.sys.entity.SysLog;
import cn.easyproject.easyee.sh.sys.entity.SysUser;
import cn.easyproject.easyee.sh.sys.service.SysLogService;

/**
 * 系统操作，日志记录拦截器
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class LogInterceptor implements HandlerInterceptor {

	SysLogService sysLogService;

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(request.getRequestURI().contains("captcha")){
			return;
		}
		/*
		 * 日志信息
		 */

		String ip = request.getRemoteAddr();
		StringBuilder parameters = new StringBuilder();
		;
		if (request.getParameterMap() != null) {

			Map<String, String[]> map = request.getParameterMap();
			for (Entry<String, String[]> entry : map.entrySet()) {
				if (entry.getKey().contains("password") || entry.getKey().contains("pwd")) {
					parameters.append(entry.getKey() + ": can'tshow; ");
					continue;
				}
				parameters.append(entry.getKey() + ": " + Arrays.toString(entry.getValue()) + "; ");
			}

			if (parameters.toString().endsWith("; ")) {
				parameters.substring(0, parameters.length() - 2);
			}
		}

		// 利用反射， 防止出现 ClassCastException
		Object sysUser= request.getSession().getAttribute("USER");
//				SysUser sysUser = (SysUser) request.getSession().getAttribute("USER");
		String account = "未登录";
		if (sysUser != null) {
			if(sysUser instanceof SysUser){
				
				String name=((SysUser) sysUser).getName();
				String realName=((SysUser) sysUser).getRealName();
				account = name + "[" + realName + "]";
			}else{
				String name=sysUser.getClass().getMethod("getName").invoke(sysUser).toString();
				String realName=sysUser.getClass().getMethod("getRealName").invoke(sysUser).toString();
				account = name + "[" + realName + "]";
			}
		}
		/*
		 * 添加日志
		 */
		String invokeBeanName = "";
		if(handler instanceof HandlerMethod){
			HandlerMethod hm = ((HandlerMethod) handler);
			invokeBeanName = hm.getBean().getClass().getName() + "#" + hm.getMethod().getName();
		}else{
			invokeBeanName = handler.getClass().getName();
		}
		
		String res = "";
		if (ex != null) {
			res = ex.getMessage() + " [" + ex.getClass().getName() + "]";
		}
		SysLog sysLog = new SysLog(request.getRequestURI() + " [" + invokeBeanName + "]", parameters.toString(), res,
				account, ip, new Date());
		
		sysLogService.add(sysLog);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		return true;
	}

	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	
	

}
