package cn.easyproject.easyee.sm.sys.util;

import java.util.List;
import java.util.Map;

import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;

/**
 * 针对 EasyEE 的 EasyUI 工具类
 * 1. 所有菜单权限树
 * 2. 将所有菜单和操作权限树
 * 3. 用户的菜单权限树
 * 4. 用户的菜单和操作权限树
 * 
 * @author Administrator
 *
 */
public class EasyUIUtil {
	
	/**
	 * 将所有菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeFromRootMenu(List<SysMenuPermission> menus) {
		return EasyUITreeGenerator.getEasyUITreeMenu(menus);
	}
	
	/**
	 * 将所有菜单和操作权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menusAndOperations
	 *            List<Map<Object, Object>> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeFromMenuAndOperation(List<Map<Object, Object>> menusAndOperations) {
		return EasyUITreeGenerator.getEasyUITreeMenuAndOperation(menusAndOperations);
	}
	
	
	/**
	 * 将用户菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeFromUserMenuPermission(List<SysMenuPermission> menus) {
		return EasyUITreeGenerator.getUserEasyUITreeMenu(menus);
	}
	
	/**
	 * 将用户所有菜单和操作权限集合转换为EasyUI菜单tree集合，针对角色加载权限
	 * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
	 * @param userMenusAndOperations
	 *            List<Map<Object, Object>> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeUsePrefixIdFromMenuAndOperation(List<Map<Object, Object>> userMenusAndOperations) {
		return EasyUITreeGenerator.getEasyUITreeMenuAndOperationUsePrefixId(userMenusAndOperations);
	}
	
	
	


}
