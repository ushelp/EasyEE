package cn.easyproject.easyee.ssh.sys.service;

import cn.easyproject.easyee.ssh.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.ssh.sys.entity.SysUser;
import cn.easyproject.easyee.ssh.base.util.PageBean;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface SysUserService{
	public SysUser login(SysUser sysUser);
	public void add(SysUser sysuser);
	public void delete(String[] ids);
	public void delete(Integer id);
	public void update(SysUser sysuser);
	public SysUser get(int id);
	@SuppressWarnings("rawtypes")
	public void findByPage(PageBean pb,SysUserCriteria sysuser);
	public void changePwd(int id,String pwd);
	public boolean existsName(String name);
	public boolean existsName(String name,Integer userId);
	public int findMaxPage(int rowsPerPage);
	public String getPwd(int id);
}
