package cn.easyproject.easyee.sm.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.sys.entity.SysUser;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface SysUserDAO{
	public SysUser login(SysUser sysUser);
	
	public void add(SysUser sysuser);
	public void addUserRoles(SysUser sysuser);
	public void deleteByIds(String[] ids);
	public void deleteUserRoles(int userId);
	public void delete(Integer id);
	public void update(SysUser sysuser);
	
	public SysUser get(int id);
	@SuppressWarnings("rawtypes")
	public List pagination(PageBean pb);
	public void changePwd(@Param("userId") int id,@Param("password") String pwd);
	public int existsName(String name);
	public int existsNameWhenEdit(@Param("name")String name,@Param("userId") Integer userId);
	public int findMaxRow();
	public String getPwd(int id);
}
