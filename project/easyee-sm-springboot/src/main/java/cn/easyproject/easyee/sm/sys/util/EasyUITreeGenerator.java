package cn.easyproject.easyee.sm.sys.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;


/**
 * 针对EasySSH 的 EasyUI Tree Generator
 * 
 * @author easyproject.cn
 * 
 */
public class EasyUITreeGenerator {
	
	/**
	 * 将所有菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	
	static List<EasyUITreeEntity> getEasyUITreeMenu(List<SysMenuPermission> menus) {
		
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		sortMenuPermission(menus);
		for (SysMenuPermission sysMenuPermission : menus) {
			EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity();
			easyUITreeRoot.setId(sysMenuPermission.getMenuPermissionId()+"");
			easyUITreeRoot.setText(sysMenuPermission.getName());
			easyUITreeRoot.setUrl(sysMenuPermission.getAction());
			easyUITreeRoot.setIconCls(sysMenuPermission.getIcon());
			easyUITreeRoot.setParentId(sysMenuPermission.getSysMenuPermission()!=null?sysMenuPermission.getSysMenuPermission().getMenuPermissionId()+"":"0");
			easyUITreeRoot.setRemark(sysMenuPermission.getRemark());
			easyUITreeRoot.setState("OPEN");
			easyUITreeRoot.setType(EasyUITreeEntity.MENU_PERMISSION);
			easyUITreeRoot.setSortNum(sysMenuPermission.getSortNum());
			list.add(easyUITreeRoot);
		}
		sortEasyUITreeEntity(list);
		return list;
	}
	
	/**
	 * 将所有菜单和操作权限集合，转换为EasyUI菜单tree集合
	 * 
	 * @param menusAndOperations
	 *           List<Map<Object, Object>> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	static List<EasyUITreeEntity> getEasyUITreeMenuAndOperation(List<Map<Object, Object>> menusAndOperations) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		
		for (Map<Object, Object> map : menusAndOperations) {
			EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity();
			easyUITreeRoot.setId(map.get("ID")+"");
			easyUITreeRoot.setText(map.get("NAME")+"");
			easyUITreeRoot.setUrl(map.get("ACTION")+"");
			easyUITreeRoot.setIconCls(map.get("ICON")+"");
			easyUITreeRoot.setParentId(map.get("PARENTID")+"");
			easyUITreeRoot.setRemark(map.get("REMARK")+"");
			easyUITreeRoot.setState("OPEN");
			easyUITreeRoot.setType(map.get("TYPE")+"");
			easyUITreeRoot.setSortNum(Integer.valueOf(map.get("SORTNUM")+""));
			list.add(easyUITreeRoot);
		}
		sortEasyUITreeEntity(list);
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
		sortMenuPermission(menus);
		for (SysMenuPermission sysMenuPermission : menus) {
			EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity();
			easyUITreeRoot.setId(sysMenuPermission.getMenuPermissionId()+"");
			easyUITreeRoot.setText(sysMenuPermission.getName());
			easyUITreeRoot.setUrl(sysMenuPermission.getAction());
			easyUITreeRoot.setIconCls(sysMenuPermission.getIcon());
			easyUITreeRoot.setParentId(sysMenuPermission.getSysMenuPermission()!=null?sysMenuPermission.getSysMenuPermission().getMenuPermissionId()+"":"0");
			easyUITreeRoot.setRemark(sysMenuPermission.getRemark());
			easyUITreeRoot.setState("OPEN");
			easyUITreeRoot.setType(EasyUITreeEntity.MENU_PERMISSION);
			easyUITreeRoot.setSortNum(sysMenuPermission.getSortNum());
			list.add(easyUITreeRoot);
		}
		sortEasyUITreeEntity(list);
		return list;
	}
	
	
	/**
	 * 将所有菜单和操作权限集合，转换为EasyUI菜单tree集合，针对角色加载权限
	 * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
	 * 
	 * @param userMenusAndOperations
	 *            List<Map<Object, Object>> 菜单权限
	 *            
	 * @return EasyUI菜单tree集合
	 */
	static List<EasyUITreeEntity> getEasyUITreeMenuAndOperationUsePrefixId(List<Map<Object, Object>> userMenusAndOperations) {
		List<EasyUITreeEntity> list = new ArrayList<EasyUITreeEntity>();
		
		for (Map<Object, Object> map : userMenusAndOperations) {
//			System.out.println(map);
			EasyUITreeEntity easyUITreeRoot = new EasyUITreeEntity();
			String prefix=map.get("TYPE").equals(EasyUITreeEntity.MENU_PERMISSION)?"menu_":"operation_";
			easyUITreeRoot.setId(prefix+map.get("ID"));
			easyUITreeRoot.setText(map.get("NAME")+"");
			easyUITreeRoot.setUrl(map.get("ACTION")+"");
			easyUITreeRoot.setIconCls(map.get("ICON")+"");
			easyUITreeRoot.setParentId("menu_"+map.get("PARENTID")+"");
			easyUITreeRoot.setRemark(map.get("REMARK")+"");
			easyUITreeRoot.setState("OPEN");
			easyUITreeRoot.setType(map.get("TYPE")+"");
			easyUITreeRoot.setSortNum(Integer.valueOf(map.get("SORTNUM")+""));
			list.add(easyUITreeRoot);
		}
		sortEasyUITreeEntity(list);
		return list;
	}
	
	
	
	
	
	/**
	 * 按照SortNum对菜单排序
	 * @param menus
	 */
	private static void sortMenuPermission(List<SysMenuPermission> menus){
		// 按sortNum排序
		Collections.sort(menus,new Comparator<SysMenuPermission>() {
			@Override
			public int compare(SysMenuPermission o1, SysMenuPermission o2) {
				return o1.getSortNum()-o2.getSortNum();
			}
		});
	}


	/**
	 * 按照SortNum对菜单排序
	 * 
	 * @param menus
	 */
	private static void sortEasyUITreeEntity(List<EasyUITreeEntity> trees) {
		List<EasyUITreeEntity> menuTrees = new ArrayList<EasyUITreeEntity>();
		List<EasyUITreeEntity> operationTrees = new ArrayList<EasyUITreeEntity>();
		for (EasyUITreeEntity easyUITreeEntity : trees) {
			if (easyUITreeEntity.getType().equals(EasyUITreeEntity.OPERATION_PERMISSION)) {
				operationTrees.add(easyUITreeEntity);
			} else {
				menuTrees.add(easyUITreeEntity);
			}
		}
		// 按sortNum排序
		Collections.sort(menuTrees, new Comparator<EasyUITreeEntity>() {
			@Override
			public int compare(EasyUITreeEntity o1, EasyUITreeEntity o2) {
				return o1.getSortNum() - o2.getSortNum();
			}
		});
		Collections.sort(operationTrees, new Comparator<EasyUITreeEntity>() {
			@Override
			public int compare(EasyUITreeEntity o1, EasyUITreeEntity o2) {
				return o1.getSortNum() - o2.getSortNum();
			}
		});
		menuTrees.addAll(operationTrees);
		trees.clear();
		trees.addAll(menuTrees);
	}
	
//	private static void sortEasyUITreeEntity(List<EasyUITreeEntity> trees){
//	// 按sortNum排序
//	Collections.sort(trees,new Comparator<EasyUITreeEntity>() {
//		@Override
//		public int compare(EasyUITreeEntity o1, EasyUITreeEntity o2) {
//			if(o1.getType().equals(EasyUITreeEntity.OPERATION_PERMISSION)){
//				return 1;
//			}
//			if(o2.getType().equals(EasyUITreeEntity.OPERATION_PERMISSION)){
//				return 1;
//			}
//			return o1.getSortNum()-o2.getSortNum();
//		}
//	});
//}
}
