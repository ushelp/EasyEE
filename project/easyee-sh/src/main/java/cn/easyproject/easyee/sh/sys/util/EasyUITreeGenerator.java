package cn.easyproject.easyee.sh.sys.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import cn.easyproject.easyee.sh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sh.sys.entity.SysOperationPermission;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * 针对 EasyEE 的 EasyUI Tree Generator
 * 
 * @author easyproject.cn
 * 
 */
@SuppressWarnings("unchecked")
public class EasyUITreeGenerator {

	/**
	 * 存储菜单权限集合等数据的OGNL上下文 操作完成后清除
	 */
	static OgnlContext context;

	/**
	 * 将用户菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            Set<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	static List<EasyUITreeEntity> getUserEasyUITreeMenu(Set<SysMenuPermission> menus) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		context = new OgnlContext();
		context.put("menus", menus);
		try {
			List<SysMenuPermission> rootMenus = (List<SysMenuPermission>) Ognl.getValue(
					"#menus.{?#this.sysMenuPermission==null}", context,
					context.getRoot());
			
			sortMenuPermission(rootMenus); //按SortNum排序菜单
			
			for (SysMenuPermission root : rootMenus) {
				EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity(
						root.getMenuPermissionId() + "", root.getName(),
						root.getAction(), "open", root.getIcon());
				easyUITreeRoot.setRemark(root.getRemark());
				recursionInitEasyUITree(easyUITreeRoot); // 递归初始化
				list.add(easyUITreeRoot);
			}

		} catch (OgnlException e) {
			e.printStackTrace();
		} finally {
			context.clear();
			context = null;
		}

		// Gson gson=new GsonBuilder().create();
		// System.out.println( gson.toJson(list));

		return list;
	}
	/**
	 * 将用户菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	static List<EasyUITreeEntity> getUserEasyUITreeMenu(List<SysMenuPermission> menus) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		context = new OgnlContext();
		context.put("menus", menus);
		try {
			List<SysMenuPermission> rootMenus = (List<SysMenuPermission>) Ognl.getValue(
					"#menus.{?#this.sysMenuPermission==null}", context,
					context.getRoot());
			
			sortMenuPermission(rootMenus); //按SortNum排序菜单
			
			for (SysMenuPermission root : rootMenus) {
				EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity(
						root.getMenuPermissionId() + "", root.getName(),
						root.getAction(), "open", root.getIcon());
				easyUITreeRoot.setRemark(root.getRemark());
				recursionInitEasyUITree(easyUITreeRoot); // 递归初始化
				list.add(easyUITreeRoot);
			}
			
		} catch (OgnlException e) {
			e.printStackTrace();
		} finally {
			context.clear();
			context = null;
		}
		
		// Gson gson=new GsonBuilder().create();
		// System.out.println( gson.toJson(list));
		
		return list;
	}

	
	/**
	 * 将所有菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	
	static List<EasyUITreeEntity> getEasyUITreeMenu(List<SysMenuPermission> menus) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		OgnlContext context = new OgnlContext();
		try {
			context.put("menus", menus);
			List<SysMenuPermission> rootMenus = (List<SysMenuPermission>) Ognl.getValue(
					"#menus.{?#this.sysMenuPermission==null}", context,
					context.getRoot());
			for (SysMenuPermission root : rootMenus) {
				EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity(root.getMenuPermissionId()
						+ "", root.getName(), root.getAction(), "open",
						root.getIcon());
				easyUITreeRoot.setRemark(root.getRemark());
				recursionInitEasyUITree(root, easyUITreeRoot); // 递归初始化
				list.add(easyUITreeRoot);
			}
		} catch (OgnlException e) {
			e.printStackTrace();
		}finally{
			context.clear();
			context = null;
		}
		return list;
	}
	
	/**
	 * 将所有菜单和操作权限集合，转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	static List<EasyUITreeEntity> getEasyUITreeMenuAndOperation(List<SysMenuPermission> menus) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		OgnlContext context = new OgnlContext();
		try {
			context.put("menus", menus);
			List<SysMenuPermission> rootMenus = (List<SysMenuPermission>) Ognl.getValue(
					"#menus.{?#this.sysMenuPermission==null}", context,
					context.getRoot());
			for (SysMenuPermission root : rootMenus) {
				EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity(root.getMenuPermissionId()
						+ "", root.getName(), root.getAction(), "open",
						root.getIcon());
				easyUITreeRoot.setRemark(root.getRemark());
				easyUITreeRoot.setType(EasyUITreeEntity.MENU_PERMISSION);
				recursionAllInitEasyUITree(root, easyUITreeRoot); // 递归初始化
				initOperationPermissionTree(easyUITreeRoot, root.getSysOperationPermissions()); // 初始化菜单下的权限树，在菜单加载完后
				list.add(easyUITreeRoot);
			}
		} catch (OgnlException e) {
			e.printStackTrace();
		}finally{
			context.clear();
			context = null;
		}
		return list;
	}
	
	/**
	 * 将所有菜单和操作权限集合，转换为EasyUI菜单tree集合，针对角色加载权限
	 * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 *            
	 * @return EasyUI菜单tree集合
	 */
	static List<EasyUITreeEntity> getEasyUITreeMenuAndOperationUsePrefixId(List<SysMenuPermission> menus) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		OgnlContext context = new OgnlContext();
		try {
			context.put("menus", menus);
			List<SysMenuPermission> rootMenus = (List<SysMenuPermission>) Ognl.getValue(
					"#menus.{?#this.sysMenuPermission==null}", context,
					context.getRoot());
			for (SysMenuPermission root : rootMenus) {
				EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity("menu_"+root.getMenuPermissionId()
						+ "", root.getName(), root.getAction(), "open",
						root.getIcon());
				easyUITreeRoot.setRemark(root.getRemark());
				easyUITreeRoot.setType(EasyUITreeEntity.MENU_PERMISSION);
				recursionAllInitEasyUITreeUsePrefixId(root, easyUITreeRoot); // 递归初始化
				initOperationPermissionTreeUsePrefixId(easyUITreeRoot, root.getSysOperationPermissions()); // 初始化菜单下的权限树，在菜单加载完后
				list.add(easyUITreeRoot);
			}
		} catch (OgnlException e) {
			e.printStackTrace();
		}finally{
			context.clear();
			context = null;
		}
		return list;
	}
	
	/**
	 * 递归初始化菜单树，菜单权限和操作权限
	 * 
	 * @param root
	 *            EasyUITree Root对象
	 */
	private static void recursionAllInitEasyUITreeUsePrefixId(SysMenuPermission menuPermission,
			EasyUITreeEntity root) {
		
		Set<SysMenuPermission> childMenus = menuPermission.getSysMenuPermissions();
		// 目录
		if (childMenus.size() > 0) {
			
			for (SysMenuPermission child : childMenus) {
				EasyUITreeEntity easyUITreeChild = new EasyUITreeEntity(
						"menu_"+child.getMenuPermissionId() + "", child.getName(),
						child.getAction(), "open", child.getIcon());
				easyUITreeChild.setRemark(child.getRemark());
				easyUITreeChild.setType(EasyUITreeEntity.MENU_PERMISSION);
				root.getChildren().add(easyUITreeChild);
				recursionAllInitEasyUITreeUsePrefixId(child, easyUITreeChild); // 递归初始化
				
				initOperationPermissionTreeUsePrefixId(easyUITreeChild, child.getSysOperationPermissions());
				
			}
		}
		
	}
	/**
	 *  初始化菜单下的权限树
	 * @param easyUITreeEntity EasyUITree节点实体
	 * @param sysOperationPermissions 操作权限集合
	 */
	private static void initOperationPermissionTreeUsePrefixId(EasyUITreeEntity easyUITreeEntity, Set<SysOperationPermission> sysOperationPermissions){
		if(sysOperationPermissions!=null){
			for (SysOperationPermission sysOperationPermission : sysOperationPermissions) {
				EasyUITreeEntity treeEntity=new EasyUITreeEntity("operation_"+sysOperationPermission.getOperationPermissionId()+"", sysOperationPermission.getName());
				treeEntity.setRemark(sysOperationPermission.getRemark());
				treeEntity.setUrl(sysOperationPermission.getAction());
				treeEntity.setType(EasyUITreeEntity.OPERATION_PERMISSION);
				easyUITreeEntity.getChildren().add(treeEntity);
			}
			
		}
	}
	/**
	 *  初始化菜单下的权限树
	 * @param easyUITreeEntity EasyUITree节点实体
	 * @param sysOperationPermissions 操作权限集合
	 */
	private static void initOperationPermissionTree(EasyUITreeEntity easyUITreeEntity, Set<SysOperationPermission> sysOperationPermissions){
		if(sysOperationPermissions!=null){
			for (SysOperationPermission sysOperationPermission : sysOperationPermissions) {
				EasyUITreeEntity treeEntity=new EasyUITreeEntity(sysOperationPermission.getOperationPermissionId()+"", sysOperationPermission.getName());
				treeEntity.setRemark(sysOperationPermission.getRemark());
				treeEntity.setUrl(sysOperationPermission.getAction());
				treeEntity.setType(EasyUITreeEntity.OPERATION_PERMISSION);
				easyUITreeEntity.getChildren().add(treeEntity);
			}
			
		}
	}

	/**
	 * 递归初始化菜单树，菜单权限和操作权限
	 * 
	 * @param root
	 *            EasyUITree Root对象
	 */
	private static void recursionAllInitEasyUITree(SysMenuPermission menuPermission,
			EasyUITreeEntity root) {
		
		Set<SysMenuPermission> childMenus = menuPermission.getSysMenuPermissions();
		// 目录
		if (childMenus.size() > 0) {
			
			for (SysMenuPermission child : childMenus) {
				EasyUITreeEntity easyUITreeChild = new EasyUITreeEntity(
						child.getMenuPermissionId() + "", child.getName(),
						child.getAction(), "open", child.getIcon());
				easyUITreeChild.setRemark(child.getRemark());
				easyUITreeChild.setType(EasyUITreeEntity.MENU_PERMISSION);
				root.getChildren().add(easyUITreeChild);
				recursionAllInitEasyUITree(child, easyUITreeChild); // 递归初始化
				
				initOperationPermissionTree(easyUITreeChild, child.getSysOperationPermissions());
				
			}
		}
		
	}
	/**
	 * 递归初始化菜单树，菜单权限
	 * 
	 * @param root
	 *            EasyUITree Root对象
	 */
	private static void recursionInitEasyUITree(SysMenuPermission menuPermission,
			EasyUITreeEntity root) {

		Set<SysMenuPermission> childMenus = menuPermission.getSysMenuPermissions();
		// 目录
		if (childMenus.size() > 0) {

			for (SysMenuPermission child : childMenus) {
				EasyUITreeEntity easyUITreeChild = new EasyUITreeEntity(
						child.getMenuPermissionId() + "", child.getName(),
						child.getAction(), "open", child.getIcon());
				easyUITreeChild.setRemark(child.getRemark());
				root.getChildren().add(easyUITreeChild);
				recursionInitEasyUITree(child, easyUITreeChild); // 递归初始化
			}
		}

	}

	/**
	 * 递归初始化菜单树，菜单权限
	 * 
	 * @param root
	 *            EasyUITree Root对象
	 */
	private static void recursionInitEasyUITree(EasyUITreeEntity root) {
		String menuPermissionId = root.getId();
		try {
			List<SysMenuPermission> childMenus = (List<SysMenuPermission>) Ognl.getValue(
					"#menus.{?#this.sysMenuPermission!=null&&#this.sysMenuPermission.menuPermissionId=="
							+ menuPermissionId + "}", context, context.getRoot());
			sortMenuPermission(childMenus); //按SortNum排序菜单
			// 目录
			if (childMenus.size() > 0) {
				// root.setIconCls(null);
			}

			for (SysMenuPermission child : childMenus) {
				EasyUITreeEntity easyUITreeChild = new EasyUITreeEntity(
						child.getMenuPermissionId() + "", child.getName(),
						child.getAction());
				// easyUITreeChild.setIconCls("icon-application");
				easyUITreeChild.setIconCls(child.getIcon());
				easyUITreeChild.setRemark(child.getRemark());
				root.getChildren().add(easyUITreeChild);

				recursionInitEasyUITree(easyUITreeChild); // 递归初始化
			}

		} catch (OgnlException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 按照SortNum对菜单排序
	 * @param rootMenus
	 */
	private static void sortMenuPermission(List<SysMenuPermission> rootMenus){
		// 按sortNum排序
		Collections.sort(rootMenus,new Comparator<SysMenuPermission>() {
			@Override
			public int compare(SysMenuPermission o1, SysMenuPermission o2) {
				return o1.getSortNum()-o2.getSortNum();
			}
		});
	}
}
