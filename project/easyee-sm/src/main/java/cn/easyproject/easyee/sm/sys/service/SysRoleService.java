package cn.easyproject.easyee.sm.sys.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.sys.criteria.SysRoleCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysRole;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@SuppressWarnings("rawtypes")
@Transactional
public interface SysRoleService{
	public void add(SysRole SysRole);
	public void delete(int id);
	public void update(SysRole SysRole);
	@Transactional(readOnly=true)
	public SysRole get(int id);
	@Transactional(readOnly=true)
	public List list();
	@Transactional(readOnly=true)
	public void findByPage(PageBean pb,SysRoleCriteria sysrole);
	@Transactional(readOnly=true)
	public int findMaxPage(int rowsPerPage);
	@Transactional(readOnly=true)
	public boolean existsName(String name);
	@Transactional(readOnly=true)
	public boolean existsName(String name,Integer roleId);
	/**
	 * 获得角色所有的权限Id集合（包括菜单权限和操作权限）
	 * @param roleId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List getAllPermissionsIds(int roleId);
	
	
}
