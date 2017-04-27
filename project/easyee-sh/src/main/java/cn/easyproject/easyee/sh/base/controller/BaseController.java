package cn.easyproject.easyee.sh.base.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.base.util.StatusCode;
import cn.easyproject.easyee.sh.base.util.StringUtils;
import cn.easyproject.easyee.sh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sh.sys.entity.SysUser;
import cn.easyproject.easyee.sh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.sh.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.sh.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.sh.sys.util.EasyUIUtil;
import cn.easyproject.easyshiro.EasyJdbcRealm;

public class BaseController {
	
	/**
	 * Web Object
	 */
	@Resource
	protected HttpServletRequest request;
	@Resource
	protected ServletContext application;
	@Resource
	protected HttpServletResponse response;
	@Resource
	private MessageSource messageSource;


	
	/*
	 * Ajax 请求响应信息 <br/> { <br/> statusCode: 响应状态码; <br/> msg: 响应消息; <br/>
	 * callback: 执行回调函数, <br/> locationUrl: 跳转页面<br/> } <br/>
	 */

	/**
	 * Ajax请求返回的状态码，非Http状态吗 <br/>
	 * 200操作正常，300操作失败，301用户超时，404资源没有找到，500服务器遇到了错误，401权限不足 <br/>
	 * easyssh.main.js 全局Ajax处理
	 */
	private ThreadLocal<Integer> statusCode=new ThreadLocal<Integer>();
	/**
	 * Ajax请求返回的提示消息
	 */
	private ThreadLocal<String> msg=new ThreadLocal<String>();
	/**
	 * 返回信息后的回调函数名
	 */
	private ThreadLocal<String> callback=new ThreadLocal<String>();
	/**
	 * 返回信息后的回调函数名
	 */
	private ThreadLocal<String> locationUrl=new ThreadLocal<String>();
	
	
	
	@Resource
	private SysOperationPermissionService sysOperationPermissionService;
	@Resource
	private SysMenuPermissionService sysMenuPermissionService;
	

	
	
	/**
	 * JSON数据初始化，包含自定义JSON键值对
	 * 
	 * @param jsonKeyAndValuePair
	 *            json数据键值对列表 key,value,key2,value2....
	 */
	protected Map<Object, Object> setJsonMap(Object... jsonKeyAndValuePair) {
		
		
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		if (jsonKeyAndValuePair.length > 0) {
			for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
				jsonMap.put(jsonKeyAndValuePair[2 * i],
						jsonKeyAndValuePair[2 * i + 1]);
			}
		}

		return jsonMap;
	}
	
	
	/**
	 * JSON数据初始化，包含自定义JSON键值对，及以下EasySSH Ajax响应信息：<br/>
	 * statusCode: 响应状态码; <br/>
	 * msg: 响应消息; <br/>
	 * callback: 执行回调函数, <br/>
	 * locationUrl: 跳转页面 <br/>
	 * 
	 * @param jsonKeyAndValuePair
	 *            json数据键值对列表 key,value,key2,value2....
	 */
	protected Map<Object, Object> setJsonMsgMap(Object... jsonKeyAndValuePair) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		
		if(msg.get()==null){
			msg.set("");
		}
		if(statusCode.get()==null){
			statusCode.set(StatusCode.OK);
		}
		if(callback.get()==null){
			callback.set("");
		}
		if(locationUrl.get()==null){
			locationUrl.set("");
		}
		
		
		jsonMap.put("msg", msg.get());
		jsonMap.put("statusCode", statusCode.get());
		jsonMap.put("callback", callback.get());
		jsonMap.put("locationUrl", locationUrl.get());
		if (jsonKeyAndValuePair.length > 0) {
			for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
				jsonMap.put(jsonKeyAndValuePair[i],
						jsonKeyAndValuePair[2 * i + 1]);
			}
		}
		clearThreadLocalResponse();
		return jsonMap;
	}

	/**
	 * JSON数据初始化，包含自定义JSON键值对，分页信息，及以下EasySSH Ajax响应信息：<br/>
	 * statusCode: 响应状态码; <br/>
	 * msg: 响应消息; <br/>
	 * callback: 执行回调函数, <br/>
	 * locationUrl: 跳转页面 <br/>
	 * 
	 * @param pb
	 *            分页对象
	 * @param jsonKeyAndValuePair
	 *            json数据键值对列表 key,value,key2,value2....
	 */
	@SuppressWarnings("rawtypes")
	protected Map<Object, Object> setJsonPaginationMap(PageBean pb,
			Object... jsonKeyAndValuePair) {
		Map<Object, Object> jsonMap = new HashMap<Object, Object>();
		// 分页参数
		jsonMap.put("page", pb.getPageNo());
		jsonMap.put("rows", pb.getData());
		jsonMap.put("total", pb.getRowsCount());

		
		if(msg.get()==null){
			msg.set("");
		}
		if(statusCode.get()==null){
			statusCode.set(StatusCode.OK);
		}
		if(callback.get()==null){
			callback.set("");
		}
		if(locationUrl.get()==null){
			locationUrl.set("");
		}
		
		
		jsonMap.put("msg", msg.get());
		jsonMap.put("statusCode", statusCode.get());
		jsonMap.put("callback", callback.get());
		jsonMap.put("locationUrl", locationUrl.get());
		if (jsonKeyAndValuePair.length > 0) {
			for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
				jsonMap.put(jsonKeyAndValuePair[i],
						jsonKeyAndValuePair[2 * i + 1]);
			}
		}
		clearThreadLocalResponse();
		return jsonMap;
	}

	
	
	/**
	 * 设置Response 响应状态码
	 */
	protected void setStatus(int status) {
		response.setStatus(status);
	}

	
	/**
	 * 获得分页对象，自动封装客户端提交的分页参数
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public PageBean getPageBean() {
		PageBean pb = new PageBean();
		/*
		 * EasyUI Pagination parameter
		 * EasyUI Sort parameter
		 */
		int page=ServletRequestUtils.getIntParameter(request, "page",1);
		int rows=ServletRequestUtils.getIntParameter(request, "rows",10);
		String sort=ServletRequestUtils.getStringParameter(request, "sort","");
		String order=ServletRequestUtils.getStringParameter(request, "order","");
		
		pb.setPageNo(page);
		pb.setRowsPerPage(rows);
		// 分页排序
		// 防止SQL注入过滤
		sort = StringUtils.filterSQLCondition(sort);
		// 防止SQL注入过滤
		order = StringUtils.filterSQLCondition(order);

		if (isNotNullAndEmpty(sort)) {
			pb.setSort(sort);
		}
		if (isNotNullAndEmpty(order)) {
			pb.setSortOrder(order);
		}

		return pb;
	}
	
	/*
	 * Common method
	 */
	public boolean isNotNullAndEmpty(Object str) {
		return str != null && (!str.equals(""));
	}
	
	/**
	 * 从Session获得当前登录的用户对象
	 * 
	 * @return 当前登录的用户对象
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SysUser getLoginUser() {
		HttpSession session = request.getSession();
		SysUser user=new SysUser();
		if(session.getAttribute("USER") instanceof SysUser){
			user=(SysUser) session.getAttribute("USER");
		}else{
			// 防止devtools 不同类加载器 ClassCastException
			Object o=session.getAttribute("USER");
			Class c=o.getClass();
			try {
				int userId=Integer.valueOf(c.getMethod("getUserId").invoke(o).toString());
				int status=Integer.valueOf(c.getMethod("getStatus").invoke(o).toString());
				String name=c.getMethod("getName").invoke(o).toString();
				String realName=c.getMethod("getRealName").invoke(o).toString();
				String password=c.getMethod("getPassword").invoke(o).toString();
				
				user.setUserId(userId);
				user.setName(name);
				user.setPassword(password);
				user.setRealName(realName);
				user.setStatus(status);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			
		}
		
		return user;
	}
	
	/**
	 * 刷新当前用户的菜单，修改菜单权限后，刷新主页即可更新菜单，无需重新登录
	 */
	public void reloadPermissions() {
		
		SysUser sysUser = getLoginUser();
		if (sysUser != null) {
			
			// 重新初始化菜单权限列表和权限映射名称
			initMenuAndOperationsName(sysUser);
			// 权限重新加载
			EasyJdbcRealm.reloadPermissions();
			
			
		}
	}

	/**
	 * 设置当前用户的菜单
	 */
	public void initMenuAndOperationsName(SysUser sysUser) {
		HttpSession session = request.getSession();
		List<SysMenuPermission> menus=sysMenuPermissionService.listByUserId(sysUser.getUserId());
		
		// 将菜单权限集合转为EasyUI菜单Tree
		List<EasyUITreeEntity> list = EasyUIUtil
				.getEasyUITreeFromUserMenuPermission(menus);
		Gson gson = new GsonBuilder().create();
		String menuTreeJson = gson.toJson(list);
		session.setAttribute("menuTreeJson", menuTreeJson); // 菜单权限集合 info
		
		// 保存所有权限对应的权限名称，权限备注
		session.setAttribute("operationsName", sysOperationPermissionService.getAllOpreationNames());
	}
	

	
	/**
	 * I18N 
	 * @param key i18n key
	 * @param args i18n key args
	 * @return i18n value
	 */
	public String getText(String key, Object[] args){
	     return messageSource.getMessage(key,args, RequestContextUtils.getLocale(request));
	}
	/**
	 * I18N 
	 * @param key i18n key
	 * @param args i18n key args
	 * @param defaultMessage defaultMessage
	 * @return i18n value
	 */
	public String getTextString(String key, Object[] args, String defaultMessage){
		return messageSource.getMessage(key, args, defaultMessage,RequestContextUtils.getLocale(request));
	}
	/**
	 * I18N 
	 * @param key i18n key
	 * @return i18n value
	 */
	public String getText(String key){
		return messageSource.getMessage(key, null, RequestContextUtils.getLocale(request));
	}
	
	/*
	 * 
	 * Ajax 响应信息设置
	 * 
	 * 
	 */

	/**
	 * 获得响应状态码
	 * @return
	 */
	public Integer getStatusCode() {
		return statusCode.get();
	}

	/**
	 * 设置响应状态码
	 * @param statusCode
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode.set(statusCode);
	}

	/**
	 * 获得响应消息
	 * @return
	 */
	public String getMsg() {
		return msg.get();
	}

	/**
	 * 设置响应消息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg.set(msg);
	}
	
	/**
	 * 设置响应消息
	 * @param msg
	 */
	public void setMsgKey(String i18nKey) {
		this.msg.set(getText(i18nKey));
	}

	/**
	 * 获得响应回调函数
	 * @return
	 */
	public String getCallback() {
		return callback.get();
	}

	/**
	 * 设置响应回调函数
	 * @param callback
	 */
	public void setCallback(String callback) {
		this.callback.set(callback);
	}

	/**
	 * 获得响应跳转地址
	 * @return
	 */
	public String getLocationUrl() {
		return locationUrl.get();
	}
	/**
	 * 设置响应跳转地址
	 * @param locationUrl
	 */
	public void setLocationUrl(String locationUrl) {
		this.locationUrl.set(locationUrl);
	}
	
	
	/**
	 * 内部使用，清除 ThreadLocal 的数据，防止线程池共享导致的其他请求受影响
	 */
	private void clearThreadLocalResponse(){
		statusCode.remove();
		msg.remove();
		callback.remove();
		locationUrl.remove();
	}
	
	
	
	
}
