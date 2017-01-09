package cn.easyproject.easyee.sm.sys.dao;

import java.util.List;
import java.util.Map;

import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface SysOperationPermissionDAO{
	public void add(SysOperationPermission sysOperationPermission);
	public void delete(SysOperationPermission sysOperationPermission);
	public void deleteOperationRoles(SysOperationPermission sysOperationPermission);
	public void deleteByIds(String[] ids);
	public void deleteOperationRolesByIds(String[] ids);
	public void deleteByMenuPermissionId(int menuPermissionId);
	
	public void update(SysOperationPermission sysOperationPermission);
	public SysOperationPermission get(int id);
	@SuppressWarnings("rawtypes")
	public List<Map> list(Integer menuId);
	/**
	 * 查询角色的所有操作权限ID
	 * @param roleId
	 * @return
	 */
	public List<String> getIdsByRoleId(Integer roleId);
	/**
	 * 获得权限动作和名称备注的映射，包括Menu和Operation权限
	 * @return
	 */
	public List<Map<String,String>> getAllOpreationNames();
}
