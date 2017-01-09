package cn.easyproject.easyee.sh.sys.service.impl;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sh.base.service.BaseService;
import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysUser;
import cn.easyproject.easyee.sh.sys.service.SysUserService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseService implements SysUserService {

	@Override
	public void add(SysUser sysuser) {
		commonDAO.persist(sysuser);
	}

	@Override
	public void delete(String[] ids) {
		StringBuilder sb = new StringBuilder();
		sb.append("");
		for (String id : ids) {
			sb.append(id + ",");
		}
		sb.deleteCharAt(sb.length() - 1).append("");

		commonDAO.deleteByValues(SysUser.class, "userId", ids);
	}

	@Override
	public void update(SysUser sysuser) {

		commonDAO.merge(sysuser);
	}

	@Override
	public SysUser get(int id) {
		return this.get(id);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void findByPage(PageBean pb, SysUserCriteria sysUserCriteria) {
		pb.setEntityName("SysUser s");
		pb.setSelect("select s");
		// 按条件分页查询
		commonDAO.findByPage(pb, sysUserCriteria);
	}

	@Override
	public SysUser login(SysUser sysUser) {
		if (sysUser == null) {
			return null;
		}
		// 用户状态：0启用；1禁用；2删除
		String hql = "from SysUser where name=? and password=? and status in(0,1)";
		SysUser user = super.uniqueResult(commonDAO.find(hql, sysUser.getName(), sysUser.getPassword()));
		if (user != null) {
			// Hibernate.initialize(user.getSysRoles());
			commonDAO.initializeDeep(user.getSysRoles());
		}
		return user;
	}

	@Override
	public void changePwd(int id, String pwd) {
		commonDAO.updateByJpql("update SysUser set password=? where userId=?", pwd, id);
	}

	@Override
	public boolean existsName(String name) {
		return commonDAO.findCount("select count(*) from SysUser where lower(name)=?", name.toLowerCase()) > 0;
	}

	@Override
	public boolean existsName(String name, Integer userId) {
		// 修改用户时，检测用户名是否存在
		return commonDAO.findCount("select count(*) from SysUser where lower(name)=? and userId!=?", name.toLowerCase(),
				userId) > 0;
	}

	@Override
	public int findMaxPage(int rowsPerPage) {
		return commonDAO.findMaxPage("select count(*) from SysUser", rowsPerPage);
	}

	@Override
	public String getPwd(int id) {
		return commonDAO.find("select password from SysUser where userId=?", id).get(0).toString();
	}

	@Override
	public void delete(Integer id) {
		commonDAO.updateByJpql("delete from SysUser where userId=?", id);
	}

}
