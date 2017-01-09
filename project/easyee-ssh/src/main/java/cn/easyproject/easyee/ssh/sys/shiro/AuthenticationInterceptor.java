package cn.easyproject.easyee.ssh.sys.shiro;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysUser;
import cn.easyproject.easyee.ssh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.ssh.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.ssh.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.ssh.sys.util.EasyUIUtil;
import cn.easyproject.easyshiro.EasyAuthenticationInterceptor;

/**
 * Shiro 认证拦截器
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 2.1.0
 */
public class AuthenticationInterceptor implements EasyAuthenticationInterceptor {

	@Resource
	private SysOperationPermissionService sysOperationPermissionService;
	@Resource
	private SysMenuPermissionService sysMenuPermissionService;

	@Override
	public void afterSuccess(ServletRequest request, ServletResponse response, AuthenticationToken token)
			throws Exception {
		Subject subject = SecurityUtils.getSubject();
		// 不要强制转换，防止 devtools 的 RestartClassLoader 导致的 cast exception
		UsernamePasswordEncodeToken downToken = new UsernamePasswordEncodeToken();
		downToken.setUserId(Integer.valueOf(token.getClass().getMethod("getUserId").invoke(token).toString()));
		downToken.setName(token.getClass().getMethod("getName").invoke(token).toString());
		downToken.setPassword((char[])token.getClass().getMethod("getPassword").invoke(token));
		downToken.setRealName(token.getClass().getMethod("getRealName").invoke(token).toString());
		downToken.setStatus(Integer.valueOf(token.getClass().getMethod("getStatus").invoke(token).toString()));
		// 用户锁定
		if (downToken.getStatus() == SysUser.STATUS_LOCK) {
			subject.logout();
			throw new LockedAccountException("账户已锁定！");
		}

		// 存入用户信息到Session
		// SysUser sysUser=new SysUser(downToken.getName(), new
		// String(downToken.getPassword()));
		SysUser sysUser = new SysUser(downToken.getName(), "");
		sysUser.setPassword(new String(downToken.getPassword()));
		sysUser.setRealName(downToken.getRealName());
		sysUser.setStatus(downToken.getStatus());
		sysUser.setUserId(downToken.getUserId());
		
		subject.getSession().setAttribute("USER", sysUser);

		// 初始化菜单列表
		initMenu(subject.getSession(), downToken);

//		System.out.println("登录成功！");
//		System.out.println(sysOperationPermissionService.getAllOpreationNames());

		// 保存所有权限对应的权限名称，权限备注
		subject.getSession().setAttribute("operationsName", sysOperationPermissionService.getAllOpreationNames());
	}

	@Override
	public void afterFailure(ServletRequest request, ServletResponse response, AuthenticationToken token, Exception e)
			throws Exception {
	}

	/**
	 * 设置当前用户的菜单
	 * 
	 * @param session
	 * @param token
	 */
	public void initMenu(Session session, UsernamePasswordEncodeToken token) {
		// Set<SysMenuPermission> menus = new HashSet<SysMenuPermission>();
		// Set<SysRole> roles = sysUser.getSysRoles(); // Roles
		// // 菜单权限
		// for (SysRole role : roles) {
		// Set<SysMenuPermission> menuPermissions =
		// role.getSysMenuPermissions();
		// for (SysMenuPermission menuPermission : menuPermissions) {
		// menus.add(menuPermission);
		// }
		// }

		List<SysMenuPermission> menus = sysMenuPermissionService.listByUserId(token.getUserId());

		// 将菜单权限集合转为EasyUI菜单Tree
		List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeFromUserMenuPermission(menus);
		Gson gson = new GsonBuilder().create();
		String menuTreeJson = gson.toJson(list);
		// session.setAttribute("menus", menus); //菜单权限集合 info
		session.setAttribute("menuTreeJson", menuTreeJson); // 菜单权限集合 info
	}

	public void setSysOperationPermissionService(SysOperationPermissionService sysOperationPermissionService) {
		this.sysOperationPermissionService = sysOperationPermissionService;
	}

}
