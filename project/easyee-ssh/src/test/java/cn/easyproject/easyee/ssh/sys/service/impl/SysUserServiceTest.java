package cn.easyproject.easyee.ssh.sys.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;








import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.base.util.SpringUtil;
import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysRole;
import cn.easyproject.easyee.ssh.sys.entity.SysUser;
import cn.easyproject.easyee.ssh.sys.service.SysUserService;
import cn.easyproject.easyee.ssh.sys.util.EasyUIUtil;
@RunWith(SpringJUnit4ClassRunner.class) //测试运行器
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class SysUserServiceTest {
	@Resource(name="sysUserService")
	SysUserService sysUserService;
//	SysUserService sysUserService = SpringUtil.getBean("sysUserService");

	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void testLoginMenu() {

		SysUser user = sysUserService.login(new SysUser("test", "111111"));
		System.out.println(user.getSysRoles());

		Set<SysRole> roles = user.getSysRoles();
		System.out.println(roles);

		// Menu Test
		Set<SysMenuPermission> menus = new HashSet<SysMenuPermission>();

		for (SysRole role : roles) {
			System.out.println(role.getName() + "  " + role.getRemark());
			Set<SysMenuPermission> menuPermissions = role.getSysMenuPermissions();

			for (SysMenuPermission menuPermission : menuPermissions) {
				// System.out.println(menuPermission);
				menus.add(menuPermission);
			}
		}

		System.out.println(menus);
		System.out.println(EasyUIUtil.getEasyUITreeFromUserMenuPermission(menus));
	}

	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void testLoginOperations() {

		SysUser user = sysUserService.login(new SysUser("admin", "111111"));
		System.out.println(user.getSysRoles());

		Set<SysRole> roles = user.getSysRoles();
		System.out.println(roles);

		// Menu Test
		// Set<SysOperationPermission> operations = new HashSet<SysOperationPermission>();
		// for (SysRole role : roles) {
		// Set<SysOperationPermission> operationPermissions =
		// role.getSysOperationPermissions();
		// for (SysOperationPermission operationPermission : operationPermissions) {
		// operations.add(operationPermission);
		// }
		// }
		Set<String> operations = new HashSet<String>();
		for (SysRole role : roles) {
			Set<SysOperationPermission> operationPermissions = role
					.getSysOperationPermissions();
			for (SysOperationPermission operationPermission : operationPermissions) {
				if (operationPermission.getAction() != null) {
					for (String o : operationPermission.getAction().split("#|,")) {
						if(o.trim().length()>0){
							operations.add(o);
						}
					}
				}
			}
		}

		System.out.println(operations);
	}

	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void findByPage() {
		PageBean pb = new PageBean();
		pb.setPageNo(1);
		pb.setRowsPerPage(5);
		sysUserService.findByPage(pb, null);

		List<SysUser> users = pb.getData();
		for (SysUser u : users) {

			// ((SysRole)u.getSysRoles().iterator().next()).setSysMenuPermissions(null);
			System.out.println(u.getName()
					+ " "
					+ ((SysRole) u.getSysRoles().iterator().next())
							.getSysMenuPermissions());
		}
		// List<Object[]> users=pb.getData();
		// for(Object[] u:users){
		// System.out.println(Arrays.toString(u));
		// }

		// List<Map> users=pb.getData();
		// System.out.println(users);
		// for(Map u:users){
		// System.out.println(u.get("name")+", "+u.get("status")+", "+u.get("sysRoles"));
		// }

	}

	
	@Test
	public void testDelete(){
		sysUserService.delete(new String[]{"103","104"});
	}
	

}
