package cn.easyproject.easyee.sm.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.sys.entity.SysRole;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@SuppressWarnings("rawtypes")
public interface SysRoleDAO{
	public void add(SysRole SysRole);
	public void addRoleMenus(SysRole SysRole);
	public void addRoleOperations(SysRole SysRole);
	
	public void deleteRoleMenus(int roleId);
	public void deleteRoleOperations(int roleId);
	public void deleteRoleUsers(int roleId);
	public void delete(int roleId);
	
	public void update(SysRole SysRole);
	
	public SysRole get(int id);
	public List list();
	public List pagination(PageBean pb);
	public int findMaxRow();
	public int existsName(String name);
	public int existsNameWhenEdit(@Param("name") String name,@Param("roleId") Integer roleId);
	/**
	 * 获得角色所有的权限Id集合（包括菜单权限和操作权限）
	 * @param roleId
	 * @return
	 */
	public List<Map<String, String>> getAllPermissionsIds(int roleId);
	
	
}
