package cn.easyproject.easyee.sh.sys.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.sh.base.controller.BaseController;
import cn.easyproject.easyee.sh.base.util.MD5;
import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.base.util.StatusCode;
import cn.easyproject.easyee.sh.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysRole;
import cn.easyproject.easyee.sh.sys.entity.SysUser;
import cn.easyproject.easyee.sh.sys.service.SysUserService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@RestController
@RequestMapping("SysUser")
@SuppressWarnings({ "rawtypes" })
public class SysUserController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(SysUserController.class);

	@Resource
	private SysUserService sysUserService;
	/* private SysRoleService sysRoleService; */

	/* private List<RoleList> roles=new ArrayList<RoleList>(); */

	/**
	 * 转向显示页面
	 * 
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView mv) {
		mv.setViewName("main/sys/sysUser");
		return mv;
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public Map<Object, Object> list(SysUserCriteria sysUserCriteria) throws Exception {
		PageBean pb = super.getPageBean(); // 获得分页对象

		sysUserService.findByPage(pb, sysUserCriteria);

		// 使用EasyObjectUtils指定fieldExpression将无需序列化和使用的属性设为null，防止JSON序列化引起延迟加载异常
		// EasyObjectSetNull.setNull(pb.getData(),
		// "{sysRoles}.sysOperationPermissions", "{sysRoles}.sysUsers");

		// 从分页集合中抽取需要输出的分页数据，不需要的不用输出，而且可以防止no session异常
		/*
		 * 
		 * EasyObjectUtils的工具类中使用到了EasyObject FieldExpression（字段表达式）语言来进行属性定位。
		 * 语法： 指定属性： property 指定属性的属性：property.property 指定集合中每一个对象： {collection}
		 * 指定数组中每一个对象： [array] 指定集合中每一个对象的属性：{collection}.property
		 * 指定数组中每一个对象的属性：[array].property
		 * 
		 * 别名定义(仅适用于EasyObjectExtract)：FieldExpression#Alias
		 */
		List<Map> list = EasyObjectExtract.extract(pb.getData(), "userId", "realName", "name", "status",
				"{sysRoles}.name#roleNames", "{sysRoles}.roleId#roleIds");
		// 使用抽取的集合作为分页数据
		pb.setData(list);

		return super.setJsonPaginationMap(pb);

	}

	/*
	 * public String toEdit(){ roles=sysRoleService.list(); return "toEdit"; }
	 */

	@RequestMapping("changePwd")
	public Map<Object, Object> changePwd(String newPwd, String confirmPwd, SysUser sysUser) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		SysUser u = (SysUser) request.getSession().getAttribute("USER");
		
		Object u=request.getSession().getAttribute("USER"); 

		super.setMsgKey("sys.UserController.pwdFail");

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

				if (confirmPwd != null && confirmPwd.equals(newPwd)) {
					try {
						String newpwd2 = MD5.getMd5(newPwd, name.toLowerCase());
						sysUserService.changePwd(userId, newpwd2);
						super.setMsgKey("msg.updateSuccess");

						// 修改session中的用户密码
						if(u2==null){
							u.getClass().getMethod("setPassword").invoke(u,newpwd2);
						}else{
							u2.setPassword(newpwd2);
						}
					} catch (Exception e) {
						super.setStatusCode(StatusCode.ERROR); // 默认为OK
						logger.error(getText("sys.UserController.pwdException"), e);
					}
				} else {
					super.setStatusCode(StatusCode.ERROR); // 默认为OK
					super.setMsgKey("sys.UserController.pwdNotEqauals");
				}
			} else {
				super.setStatusCode(StatusCode.ERROR); // 默认为OK
				super.setMsgKey("sys.UserController.pwdWrongError");
			}
		}
		return super.setJsonMsgMap();

	}

	/**
	 * 添加用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("save")
	public Map<Object, Object> save(int rows, String confirmPwd, String[] roleIds, SysUser sysUser) {
		super.setStatusCode(StatusCode.ERROR); // 默认为OK
		super.setMsgKey("msg.saveFail");
		if (sysUser.getPassword() != null && sysUser.getPassword().equals(confirmPwd)) {

			if (sysUserService.existsName(sysUser.getName())) {
				super.setMsgKey("sys.UserController.userExists");
			} else {
				if (roleIds != null) {
					for (String roleId : roleIds) { // 保存角色
						sysUser.getSysRoles().add(new SysRole(Integer.valueOf(roleId)));
					}
				}

				// 保存用户
				try {
					sysUser.setPassword(MD5.getMd5(sysUser.getPassword(), sysUser.getName().toLowerCase()));
					sysUserService.add(sysUser);
					super.setMsgKey("msg.saveSuccess");
					super.setStatusCode(StatusCode.OK);
					super.setMsg("");
				} catch (Exception e) {
					logger.error(getText("sys.UserController.saveException"), e);
				}
			}

		} else {
			super.setMsgKey("sys.UserController.pwdNotEqauals");
		}

		// 如果需要刷新，跳转到最后一页
		int maxPage = sysUserService.findMaxPage(rows);
		return super.setJsonMsgMap("page", maxPage);
	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@RequestMapping("delete")
	public Map<Object, Object> delete(String[] userId) {
		try {
			sysUserService.delete(userId);
			// sysUserService.delete(sysUser.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			// 出错输出 500 响应
			// ServletControllerContext.getResponse().setStatus(500);
			super.setStatusCode(StatusCode.ERROR); // 默认为OK
		}
		return super.setJsonMsgMap();

	}

	/**
	 * 修改用户
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("update")
	public Map<Object, Object> update(String confirmPwd, String[] roleIds, SysUser sysUser) {
		super.setMsgKey("msg.updateFail");
		super.setStatusCode(StatusCode.ERROR); // 默认为OK
		if (sysUser.getPassword() != null && sysUser.getPassword().equals(confirmPwd)) {
			try {
				if (sysUserService.existsName(sysUser.getName(), sysUser.getUserId())) {
					super.setMsgKey("sys.UserController.userExists");
				} else {
					String oldPwd = sysUserService.getPwd(sysUser.getUserId());
					sysUser.getSysRoles().clear(); // 清除原角色
					for (String roleId : roleIds) { // 保存角色
						sysUser.getSysRoles().add(new SysRole(Integer.valueOf(roleId)));
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
					super.setMsgKey("msg.updateSuccess");
					super.setStatusCode(StatusCode.OK);

					// 如果修改的是当前用户自己，则刷新其菜单权限和操作权限
					if (sysUser.getUserId() == getLoginUser().getUserId()) {
						reloadPermissions(); // 自动刷新权限
					}
					// 修改刷新当前页
					// super.page=sysUserService.findMaxPage(rows);
				}
			} catch (Exception e) {
				logger.error(getText("sys.UserController.updateException"), e);
			}

		} else {
			super.setMsgKey("sys.UserController.pwdNotEqauals");
		}
		return super.setJsonMsgMap();

	}

}
