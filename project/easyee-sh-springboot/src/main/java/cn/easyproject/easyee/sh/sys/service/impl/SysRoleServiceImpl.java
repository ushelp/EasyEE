package cn.easyproject.easyee.sh.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sh.base.service.BaseService;
import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysRoleCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysRole;
import cn.easyproject.easyee.sh.sys.service.SysRoleService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Service("sysRoleService")
@SuppressWarnings("rawtypes")
public class SysRoleServiceImpl extends BaseService implements SysRoleService {

	@Override
	public void add(SysRole SysRole) {
		commonDAO.persist(SysRole);
	}

	@Override
	public void delete(int id) {
		commonDAO.remove(SysRole.class, id);
	}

	@Override
	public void update(SysRole SysRole) {
		commonDAO.merge(SysRole);
	}

	@Override
	public SysRole get(int id) {
		return commonDAO.find(SysRole.class, id);
	}

	@Override
	public List list() {
		return commonDAO.findByCache("from SysRole", "SysRole.list");
	}

	@Override
	public void findByPage(PageBean pb, SysRoleCriteria sysRoleCriteria) {
		pb.setEntityName("SysRole s");
		pb.setSelect("select s");

		// 按条件分页查询
		commonDAO.findByPage(pb, sysRoleCriteria);
	}

	@Override
	public int findMaxPage(int rowsPerPage) {
		return commonDAO.findMaxPage("select count(*) from SysRole", rowsPerPage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getAllPermissionsIds(int roleId) {
		// 查询角色的菜单权限Id和操作权限Id
		List menuIds = commonDAO.findMapResultBySQL(
				"select 'menu' as TYPE,menu_Permission_Id as ID from sys_role_menu where ROLE_ID=?", roleId);
		List permissionIds = commonDAO.findMapResultBySQL(
				"select 'operation' as TYPE,Operation_Permission_Id as ID from sys_role_operation where ROLE_ID=?",
				roleId);
		// 返回所有权限id
		menuIds.addAll(permissionIds);
		return menuIds;
	}

	@Override
	public boolean existsName(String name) {
		return commonDAO.findCount("select count(*) from SysRole where lower(name)=?", name.toLowerCase()) > 0;
	}

	@Override
	public boolean existsName(String name, Integer roleId) {
		// 修改用户时，检测名是否存在
		return commonDAO.findCount("select count(*) from SysRole where lower(name)=? and roleId!=?", name.toLowerCase(),
				roleId) > 0;
	}

}
