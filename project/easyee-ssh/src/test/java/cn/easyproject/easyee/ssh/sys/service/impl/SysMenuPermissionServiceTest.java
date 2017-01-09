package cn.easyproject.easyee.ssh.sys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;
import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.ssh.base.util.SpringUtil;
import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysRole;
import cn.easyproject.easyee.ssh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.ssh.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.ssh.sys.util.EasyUIUtil;

@RunWith(SpringJUnit4ClassRunner.class) //测试运行器
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class SysMenuPermissionServiceTest {
	@Resource(name="sysMenuPermissionService")
	SysMenuPermissionService sysMenuPermissionService;
//	SysMenuPermissionService sysMenuPermissionService=SpringUtil.getBean("sysMenuPermissionService");
	
	@org.junit.Test
	public void testList2() throws OgnlException {
		// 查询所有菜单权限
				List<SysMenuPermission> menus=sysMenuPermissionService.list();
//				System.out.println(menus.get(0).getSysMenuPermissions());
		
		System.out.println(EasyObjectExtract.extract(menus, "menuPermissionId","action","icon","name","remark","sortNum","sysMenuPermission","sysMenuPermissions"));
	}
	@SuppressWarnings("unchecked")
	@org.junit.Test
	public void testList() throws OgnlException {
		// 查询所有菜单权限
		List<SysMenuPermission> allMenus=sysMenuPermissionService.list();
		
		// 初始化
		
//		List<SysMenuPermission> rootMenus=MenuPermissionUitl.sysPermissionMenuInit(allMenus);
		
//		for(SysMenuPermission rootSysMenuPermission: rootMenus){
		for(SysMenuPermission rootSysMenuPermission: allMenus){
			// root
			if(rootSysMenuPermission.getSysMenuPermission()==null){
				printSysMenu(rootSysMenuPermission,"++"); 
			}
		}
		OgnlContext context = new OgnlContext();
		context.put("menus", allMenus);
		List<SysMenuPermission> rootMenus = (List<SysMenuPermission>) Ognl.getValue(
				"#menus.{?#this.sysMenuPermission==null}", context,
				context.getRoot());
		
		System.out.println("-------------------------------------------------");
	
		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeFromRootMenu(rootMenus);
		System.out.println(list);
	}
	
	
	
	public void printSysMenu(SysMenuPermission sysMenuPermission,String icon){
		System.out.println(icon+" "+sysMenuPermission);
		if(sysMenuPermission.getSysMenuPermissions()!=null){
			for(SysMenuPermission s:sysMenuPermission.getSysMenuPermissions()){
				printSysMenu(s,icon+icon+icon);
			}
		}else{
			
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testListAll(){
		List menus=sysMenuPermissionService.listAll();
		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeFromMenuAndOperation(menus);
		System.out.println(list);
	}
	
	@Test
	public void testGetMaxOrder(){
		System.out.println(sysMenuPermissionService.getMaxSortNum(-1));
	}
	
	@Test
	public void testMove(){
		sysMenuPermissionService.move(4, true);
	}
	
//	@Test
//	public void testListByUserId(){
//		List menus=sysMenuPermissionService.listByUserId(1);
//		System.out.println(menus);
//	}
//	
//	public static void main(String[] args) {
//		SysMenuPermissionService sysMenuPermissionService=SpringUtil.getBean("sysMenuPermissionService");
//		List<SysMenuPermission> menus=sysMenuPermissionService.listByUserId(1);
//		System.out.println(menus);
//		System.out.println(menus.size());
//		System.out.println(menus.get(0).getSysMenuPermissions());
//	}
//	
	
	@org.junit.Test
	public void testListByUserId(){
		List<SysMenuPermission> menus=sysMenuPermissionService.listByUserId(1);
//		System.out.println(menus);
		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeFromUserMenuPermission(menus);
	}
}
