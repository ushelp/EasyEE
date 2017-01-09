package cn.easyproject.easyee.sh.sys.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysUser;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface SysUserService {
	@Transactional(readOnly = true)
	public SysUser login(SysUser sysUser);

	public void add(SysUser sysuser);

	public void delete(String[] ids);

	public void delete(Integer id);

	public void update(SysUser sysuser);

	@Transactional(readOnly = true)
	public SysUser get(int id);

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public void findByPage(PageBean pb, SysUserCriteria sysuser);

	public void changePwd(int id, String pwd);

	@Transactional(readOnly = true)
	public boolean existsName(String name);

	@Transactional(readOnly = true)
	public boolean existsName(String name, Integer userId);

	@Transactional(readOnly = true)
	public int findMaxPage(int rowsPerPage);

	@Transactional(readOnly = true)
	public String getPwd(int id);
}
