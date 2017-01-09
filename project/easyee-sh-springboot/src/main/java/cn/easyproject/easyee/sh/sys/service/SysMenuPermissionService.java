package cn.easyproject.easyee.sh.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sh.sys.entity.SysMenuPermission;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface SysMenuPermissionService {
	public void add(SysMenuPermission sysMenuPermission);

	public void delete(SysMenuPermission sysMenuPermission);

	public void update(SysMenuPermission sysMenuPermission);

	@Transactional(readOnly = true)
	public SysMenuPermission get(int id);

	/**
	 * 获得菜单权限
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<SysMenuPermission> list();

	/**
	 * 获得菜单权限和操作权限
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<SysMenuPermission> listAll();

	@Transactional(readOnly = true)
	public int getMaxSortNum(Integer parentId);

	@Transactional(readOnly = true)
	public int getSortNum(Integer menuPermissionId);

	public void move(Integer menuPermissionId, boolean up);

	@Transactional(readOnly = true)
	public boolean hashChildMenu(Integer menuPermissionId);

	/**
	 * 查询角色的所有菜单权限ID
	 * 
	 * @param roleId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<String> getIdsByRoleId(Integer roleId);

	/**
	 * 根据用户ID查询用户所有菜单权限
	 * 
	 * @param userId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<SysMenuPermission> listByUserId(int userId);
}
