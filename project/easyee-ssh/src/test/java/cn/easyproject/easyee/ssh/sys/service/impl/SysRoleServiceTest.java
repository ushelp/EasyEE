package cn.easyproject.easyee.ssh.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;





import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ognl.OgnlException;
import cn.easyproject.easyee.ssh.base.util.SpringUtil;
import cn.easyproject.easyee.ssh.sys.entity.SysRole;
import cn.easyproject.easyee.ssh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.ssh.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.ssh.sys.service.SysRoleService;
@RunWith(SpringJUnit4ClassRunner.class) //测试运行器
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class SysRoleServiceTest {
	@Resource(name="sysRoleService")
	SysRoleService sysRoleService = SpringUtil.getBean("sysRoleService");
//	@Resource(name="sysRoleService")
//	SysMenuPermissionService sysMenuPermissionService = SpringUtil.getBean("sysMenuPermissionService");
//	@Resource(name="sysRoleService")
//	SysOperationPermissionService sysOperationPermissionService = SpringUtil.getBean("sysOperationPermissionService");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@org.junit.Test
	public void getAllPermissionsIds() throws OgnlException {
//		// 查询所有菜单权限
//		
//		List menuIds=sysMenuPermissionService.getIdsByRoleId(22);
//		List permissionIds=sysOperationPermissionService.getIdsByRoleId(22);
//		menuIds.addAll(permissionIds);
//		
//		System.out.println(menuIds);
//		
//		List<Map> all=sysRoleService.getAllPermissionsIds(22);
//		List allPermissions=new ArrayList();
//		for (Map res : all) {
//			allPermissions.add(res.get("type")+"_"+res.get("id"));
//		}
//		System.out.println(allPermissions);
//		
		
	}
	

}
