package cn.easyproject.easyee.ssh.sys.service;

import java.util.List;
import java.util.Map;

import cn.easyproject.easyee.ssh.sys.entity.SysOperationPermission;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface SysOperationPermissionService{
	public void add(SysOperationPermission sysOperationPermission);
	public void delete(String[] ids);
	public void deleteByMenuPermissionId(int menuPermissionId);
	public void delete(SysOperationPermission sysOperationPermission);
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
	public Map<String,String> getAllOpreationNames();
}
