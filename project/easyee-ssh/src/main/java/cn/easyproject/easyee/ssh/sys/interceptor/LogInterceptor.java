package cn.easyproject.easyee.ssh.sys.interceptor;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import cn.easyproject.easyee.ssh.sys.entity.SysLog;
import cn.easyproject.easyee.ssh.sys.entity.SysUser;
import cn.easyproject.easyee.ssh.sys.service.SysLogService;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 系统操作，日志记录拦截器
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class LogInterceptor extends AbstractInterceptor {

	@Resource
	SysLogService sysLogService;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		/*
		 * 日志信息
		 */
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		String method = invocation.getProxy().getMethod();
		
		String action = namespace + actionName + "." + method;
		String ip = request.getRemoteAddr();
		StringBuilder parameters = new StringBuilder();;
		if(request.getParameterMap()!=null){
			
			Map<String, String[]> map=request.getParameterMap();
			for(Entry<String, String[]> entry:map.entrySet()){
				if(entry.getKey().contains("password")||entry.getKey().contains("pwd")){
					parameters.append(entry.getKey()+": can'tshow; ");
					continue;
				}
				parameters.append(entry.getKey()+": "+Arrays.toString(entry.getValue())+"; ");
			}
			
			if(parameters.toString().endsWith("; ")){
				parameters.substring(0,parameters.length()-2);
			}
		}
		
		String res="";
		Object o = invocation.invoke();
		if(o!=null){
			res = o.toString();
		}
		
		// 利用反射， 防止出现 ClassCastException
		Object sysUser= request.getSession().getAttribute("USER");
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
//		SysLogService sysLogService = SpringUtil.getBean("sysLogService");
		SysLog sysLog = new SysLog(action, parameters.toString(), res, account, ip,new Date());
		
		if(!actionName.contains("captcha")){
			sysLogService.add(sysLog);
		}
		
		return null;
	}

}
