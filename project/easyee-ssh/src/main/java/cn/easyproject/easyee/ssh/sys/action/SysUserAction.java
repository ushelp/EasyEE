package cn.easyproject.easyee.ssh.sys.action;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.ssh.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.ssh.sys.entity.SysRole;
import cn.easyproject.easyee.ssh.sys.entity.SysUser;
import cn.easyproject.easyee.ssh.sys.service.SysUserService;
import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.MD5;
import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.base.util.StatusCode;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@ParentPackage("easyssh-default")
@Namespace("/SysUser")
@SuppressWarnings({ "rawtypes" })
public class SysUserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger = LoggerFactory.getLogger(SysUserAction.class);

	private SysUser sysUser;
	private SysUserCriteria sysUserCriteria;
	private SysUserService sysUserService;
	/* private SysRoleService sysRoleService; */

	private String newPwd;
	private String confirmPwd;
	private String[] roleIds = {};
	// 要删除的用户ID列表，#分隔
	// private String userId;
	private String[] userId;// 多行批量删除

	/* private List<RoleList> roles=new ArrayList<RoleList>(); */

	@Override
	@Action(value="page",results={
			@Result(location="/WEB-INF/content/main/sys/sysUser.jsp")
	})
	public String execute() throws Exception {
		return SUCCESS;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Action("list")
	public String list() throws Exception {
		PageBean pb = super.getPageBean(); // 获得分页对象

		sysUserService.findByPage(pb, sysUserCriteria);

		// 使用EasyObjectUtils指定fieldExpression将无需序列化和使用的属性设为null，防止JSON序列化引起延迟加载异常
		// EasyObjectSetNull.setNull(pb.getData(),
		// "{sysRoles}.sysOperationPermissions", "{sysRoles}.sysUsers");

		// 从分页集合中抽取需要输出的分页数据，不需要的不用输出，而且可以防止no session异常
		/*
		 * 
		 * EasyObjectUtils的工具类中使用到了EasyObject FieldExpression（字段表达式）语言来进行属性定位。
			语法：
			 指定属性： property 
			 指定属性的属性：property.property
			 指定集合中每一个对象： {collection}
			 指定数组中每一个对象： [array] 
			 指定集合中每一个对象的属性：{collection}.property 
			 指定数组中每一个对象的属性：[array].property 
			
			 别名定义(仅适用于EasyObjectExtract)：FieldExpression#Alias
		 */
		List<Map> list = EasyObjectExtract.extract(pb.getData(), "userId", "realName", "name", "status",
				"{sysRoles}.name#roleNames", "{sysRoles}.roleId#roleIds");
		// 使用抽取的集合作为分页数据
		pb.setData(list);

		super.setJsonPaginationMap(pb);
		return JSON;
	}

	/*
	 * public String toEdit(){ roles=sysRoleService.list(); return "toEdit"; }
	 */
	@Action("changePwd")
	public String changePwd() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		SysUser u = (SysUser) request.getSession().getAttribute("USER");
		Object u=request.getSession().getAttribute("USER"); 

		msg = getText("sys.UserAction.pwdFail");

		if (u != null) {
			String name="";
			String password="";
			int userId=0;
			SysUser u2=null;
			if(u instanceof SysUser){
				u2=(SysUser) u;
				password=u2.getPassword();
				name=u2.getName(); 
				userId=u2.getUserId();
			}else{
				name=u.getClass().getMethod("getName").invoke(u).toString();
				password=u.getClass().getMethod("getPassword").invoke(u).toString();
				userId=Integer.valueOf(u.getClass().getMethod("getUserId").invoke(u).toString());
			}
			String originalPwd = MD5.getMd5(sysUser.getPassword(), name.toLowerCase());

			// 旧密码正确
			if (password.equals(originalPwd)) {
			
				if (confirmPwd!=null&&confirmPwd.equals(newPwd)) {
					try {
						String newpwd2=MD5.getMd5(newPwd, name.toLowerCase());
						sysUserService.changePwd(userId, newpwd2);
						msg = getText("msg.updateSuccess");

						 //修改session中的用户密码
						if(u2==null){
							u.getClass().getMethod("setPassword").invoke(u,newpwd2);
						}else{
							u2.setPassword(newpwd2);
						}
					} catch (Exception e) {
						statusCode = StatusCode.ERROR;
						logger.error(getText("sys.UserAction.pwdException"), e);
					}
				}else{
					statusCode = StatusCode.ERROR;
					msg = getText("sys.UserAction.pwdNotEqauals");
				}
			}else {
				statusCode = StatusCode.ERROR;
				msg = getText("sys.UserAction.pwdWrongError");
			}
		} 
		super.setJsonMsgMap();
		return JSON;
	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("save")
	public String save() {
		statusCode = StatusCode.ERROR;
		msg = getText("msg.saveFail");
		if (sysUser.getPassword() != null && sysUser.getPassword().equals(confirmPwd)) {

			if (sysUserService.existsName(sysUser.getName())) {
				msg = getText("sys.UserAction.userExists");
			} else {
				if(roleIds!=null){
					for (String roleId : roleIds) { // 保存角色
						sysUser.getSysRoles().add(new SysRole(Integer.valueOf(roleId)));
					}
				}
				// 保存用户
				try {
					sysUser.setPassword(MD5.getMd5(sysUser.getPassword(), sysUser.getName().toLowerCase()));
					sysUserService.add(sysUser);
					msg = getText("msg.saveSuccess");
					statusCode = StatusCode.OK;
					msg = "";
					// 跳转到最后一页
					super.page = sysUserService.findMaxPage(rows);
				} catch (Exception e) {
					logger.error(getText("sys.UserAction.saveException"), e);
				}
			}

		} else {
			msg = getText("sys.UserAction.pwdNotEqauals");
		}
		super.setJsonMsgMap("page", super.page);
		return JSON;
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@Action("delete")
	public String delete() {
		try {
			sysUserService.delete(userId);
			// sysUserService.delete(sysUser.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			// 出错输出 500 响应
			// ServletActionContext.getResponse().setStatus(500);
			statusCode = StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("update")
	public String update() {
		msg = getText("msg.updateFail");
		statusCode = StatusCode.ERROR;
		if (sysUser.getPassword() != null && sysUser.getPassword().equals(confirmPwd)) {
			try {
				if (sysUserService.existsName(sysUser.getName(), sysUser.getUserId())) {
					msg = getText("sys.UserAction.userExists");
				} else {
					String oldPwd = sysUserService.getPwd(sysUser.getUserId());
					sysUser.getSysRoles().clear(); // 清除原角色
					if(roleIds!=null){
						for (String roleId : roleIds) { // 保存角色
							sysUser.getSysRoles().add(new SysRole(Integer.valueOf(roleId)));
						}
					}
					// 判断密码是否修改了，没有修改则使用旧密码
					if (!isNotNullAndEmpty(sysUser.getPassword())) {
						sysUser.setPassword(oldPwd);
					} else {
						sysUser.setPassword(MD5.getMd5(sysUser.getPassword(), sysUser.getName().toLowerCase()));
					}

					// 保存用户
					sysUserService.update(sysUser);
					// 如果修改了当前登录用户，则修改session中的用户密码
//					SysUser u = (SysUser) request.getSession().getAttribute("USER");
					Object u=request.getSession().getAttribute("USER"); 

					if(u instanceof SysUser){
						SysUser u2=(SysUser)request.getSession().getAttribute("USER"); 
						u2.setPassword(sysUser.getPassword());
					}else{
						int userId=Integer.valueOf(u.getClass().getMethod("getUserId").invoke(u).toString());
						
						// System.out.println(request.getSession());
						// System.out.println("############" + u);
						// System.out.println(sysUser);
						if (userId==sysUser.getUserId().intValue()) {
	//						u.setPassword(sysUser.getPassword());
							u.getClass().getMethod("setPassword").invoke(u,sysUser.getPassword());
						}
					}
					msg = getText("msg.updateSuccess");
					statusCode = StatusCode.OK;

					// 如果修改的是当前用户自己，则刷新其菜单权限和操作权限
					if (sysUser.getUserId() == getLoginUser().getUserId()) {
						reloadPermissions(); // 自动刷新权限
//						 TODO 刷新操作权限
					}
					// 修改刷新当前页
					// super.page=sysUserService.findMaxPage(rows);
				}
			} catch (Exception e) {
				logger.error(getText("sys.UserAction.updateException"), e);
			}

		} else {
			msg = getText("sys.UserAction.pwdNotEqauals");
		}
		super.setJsonMsgMap();
		return JSON;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public void setUserId(String[] userId) {
		this.userId = userId;
	}
	// public void setUserId(String userId) {
	// this.userId = userId;
	// }

	public SysUserCriteria getSysUserCriteria() {
		return sysUserCriteria;
	}

	public void setSysUserCriteria(SysUserCriteria sysUserCriteria) {
		this.sysUserCriteria = sysUserCriteria;
	}

	/*
	 * public void setSysRoleService(SysRoleService sysRoleService) {
	 * this.sysRoleService = sysRoleService; }
	 * 
	 * public List<RoleList> getRoles() { return roles; }
	 */

}
