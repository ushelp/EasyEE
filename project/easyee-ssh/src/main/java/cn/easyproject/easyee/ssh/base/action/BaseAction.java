package cn.easyproject.easyee.ssh.base.action;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.util.ServletContextAware;
import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.base.util.StatusCode;
import cn.easyproject.easyee.ssh.base.util.StringUtils;
import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysUser;
import cn.easyproject.easyee.ssh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.ssh.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.ssh.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.ssh.sys.util.EasyUIUtil;
import cn.easyproject.easyshiro.EasyJdbcRealm;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;

/**
 * All Action extends BaseAction
 * 
 * BaseAction中定义了以下主要内容：<br/>
 * 
 * - Servlet API（request, application ） <br/>
 * - JSON参数（请求响应相关的EasyUI分页参数，EasySSH Ajax消息参数） <br/>
 * - JSON响应初始化工具方法（`setJsonRoot`,`setJsonMap`，`setJsonMsgMap`，`setJsonPaginationMap`）<br/>
 * - JSON result常量： ` final String JSON = "json";` <br/>
 * - 工具方法<br/> 
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseAction extends ActionSupport implements ServletRequestAware,
		ServletContextAware {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Web Object
	 */
	protected HttpServletRequest request;
	protected ServletContext application;
	/**
	 * Struts Results
	 */
	protected final String JSON = "json";
	protected final String LOGIN_REDIRECT = "loginRedirect";

	/**
	 * EasyUI Pagination parameter
	 */
	public int page;
	public int rows;

	/**
	 * EasyUI Sort parameter
	 */
	public String sort;
	public String order;

	/**
	 * Ajax json root object 所有JSON内容在Action中存入该对象 然后，return JSON;
	 */
	public Object jsonRoot;

	/*
	 * Ajax 请求响应信息 <br/> { <br/> statusCode: 响应状态码; <br/> msg: 响应消息; <br/>
	 * callback: 执行回调函数, <br/> locationUrl: 跳转页面<br/> } <br/>
	 */

	/**
	 * Ajax请求返回的状态码，非Http状态吗 <br/>
	 * 200操作正常，300操作失败，301用户超时，404资源没有找到，500服务器遇到了错误，401权限不足 <br/>
	 * easyssh.main.js 全局Ajax处理
	 */
	protected int statusCode = StatusCode.OK;
	/**
	 * Ajax请求返回的提示消息
	 */
	protected String msg;
	/**
	 * 返回信息后的回调函数名
	 */
	protected String callback = "";
	/**
	 * 返回信息后的回调函数名
	 */
	protected String locationUrl = "";
	
	
	
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
	protected void setJsonMap(Object... jsonKeyAndValuePair) {
		
		Map jsonMap = new HashMap();
		jsonMap.put("page", page);
		if (jsonKeyAndValuePair.length > 0) {
			for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
				jsonMap.put(jsonKeyAndValuePair[2 * i],
						jsonKeyAndValuePair[2 * i + 1]);
			}
		}

		jsonRoot = jsonMap;
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
	protected void setJsonMsgMap(Object... jsonKeyAndValuePair) {
		Map jsonMap = new HashMap();
		jsonMap.put("page", page);
		jsonMap.put("msg", msg);
		jsonMap.put("statusCode", statusCode);
		jsonMap.put("callback", callback);
		jsonMap.put("locationUrl", locationUrl);
		if (jsonKeyAndValuePair.length > 0) {
			for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
				jsonMap.put(jsonKeyAndValuePair[i],
						jsonKeyAndValuePair[2 * i + 1]);
			}
		}
		jsonRoot = jsonMap;
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
	protected void setJsonPaginationMap(PageBean pb,
			Object... jsonKeyAndValuePair) {
		Map jsonMap = new HashMap();
		// 分页参数
		jsonMap.put("page", pb.getPageNo());
		jsonMap.put("rows", pb.getData());
		jsonMap.put("total", pb.getRowsCount());

		jsonMap.put("msg", msg);
		jsonMap.put("statusCode", statusCode);
		jsonMap.put("callback", callback);
		jsonMap.put("locationUrl", locationUrl);
		if (jsonKeyAndValuePair.length > 0) {
			for (int i = 0; i < jsonKeyAndValuePair.length / 2; i++) {
				jsonMap.put(jsonKeyAndValuePair[i],
						jsonKeyAndValuePair[2 * i + 1]);
			}
		}
		jsonRoot = jsonMap;
	}

	/**
	 * 设置Response 响应状态码
	 */
	protected void setStatus(int status) {
		ServletActionContext.getResponse().setStatus(status);
	}

	/**
	 * 获得分页对象，自动封装客户端提交的分页参数
	 * 
	 * @return
	 */
	public PageBean getPageBean() {
		PageBean pb = new PageBean();
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

	/*
	 * Servlet API Injection
	 */
	@Override
	public void setServletContext(ServletContext arg0) {
		this.application = arg0;
	}

	@Override
	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0;
	}

	public String getMsg() {
		return msg;
	}

	public Object getJsonRoot() {
		return jsonRoot;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * 自定义JSON输出根对象
	 * 
	 * @param jsonRoot
	 */
	public void setJsonRoot(Object jsonRoot) {
		this.jsonRoot = jsonRoot;
	}
	

}
