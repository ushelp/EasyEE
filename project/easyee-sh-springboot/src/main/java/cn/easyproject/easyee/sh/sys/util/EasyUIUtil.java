package cn.easyproject.easyee.sh.sys.util;

import java.util.List;
import java.util.Set;

import cn.easyproject.easyee.sh.sys.entity.SysMenuPermission;

/**
 * 针对EasyEE 的 EasyUI 工具类
 * 
 * @author Administrator
 *
 */
public class EasyUIUtil {
	/**
	 * 将用户菜单权限集合转换为EasyUI菜单tree集合
	 * 
	 * @param menus
	 *            Set<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeFromUserMenuPermission(Set<SysMenuPermission> menus) {
		return EasyUITreeGenerator.getUserEasyUITreeMenu(menus);
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
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeFromMenuAndOperation(List<SysMenuPermission> menus) {
		return EasyUITreeGenerator.getEasyUITreeMenuAndOperation(menus);
	}

	/**
	 * 将所有菜单和操作权限集合转换为EasyUI菜单tree集合，针对角色加载权限
	 * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
	 * 
	 * @param menus
	 *            List<SysMenuPermission> 菜单权限
	 * @return EasyUI菜单tree集合
	 */
	public static List<EasyUITreeEntity> getEasyUITreeUsePrefixIdFromMenuAndOperation(List<SysMenuPermission> menus) {
		return EasyUITreeGenerator.getEasyUITreeMenuAndOperationUsePrefixId(menus);
	}

}
