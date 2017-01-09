package cn.easyproject.easyee.ssh.sys.service.impl;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ognl.OgnlException;
import cn.easyproject.easyee.ssh.base.util.SpringUtil;
import cn.easyproject.easyee.ssh.sys.service.SysOperationPermissionService;
@RunWith(SpringJUnit4ClassRunner.class) //测试运行器
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class SysOperationPermissionServiceTest {
	@Resource(name="sysOperationPermissionService")
	SysOperationPermissionService sysOperationPermissionService;
//	SysOperationPermissionService sysOperationPermissionService=SpringUtil.getBean("sysOperationPermissionService");
	
	
	@org.junit.Test
	public void testList() throws OgnlException {
		// 查询所有操作权限
		System.out.println(sysOperationPermissionService);
//		System.out.println(sysOperationPermissionService.list(3));
		System.out.println(sysOperationPermissionService.getAllOpreationNames());
		
	}
	

	
}
