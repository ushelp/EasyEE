package cn.easyproject.easyee.sm.hr.dao;

import java.io.Serializable;
import java.util.List;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.hr.entity.Dept;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public interface DeptDAO {
	
	public void save(Dept dept);

	public void delete(Serializable deptno);

	public void update(Dept dept);

	public Dept get(Integer deptno);

	@SuppressWarnings("rawtypes")
	public List pagination(PageBean pageBean);

	public List<Dept> findAll();
	
	public int findMaxRow();
}
