package cn.easyproject.easyee.sh.sys.service;

import java.util.List;

import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysRoleCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysRole;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@SuppressWarnings("rawtypes")
public interface SysRoleService{
	public void add(SysRole SysRole);
	public void delete(int id);
	public void update(SysRole SysRole);
	public SysRole get(int id);
	public List list();
	public void findByPage(PageBean pb,SysRoleCriteria sysrole);
	public int findMaxPage(int rowsPerPage);
	public boolean existsName(String name);
	public boolean existsName(String name,Integer roleId);
	/**
	 * 获得角色所有的权限Id集合（包括菜单权限和操作权限）
	 * @param roleId
	 * @return
	 */
	public List getAllPermissionsIds(int roleId);
	
	
}
