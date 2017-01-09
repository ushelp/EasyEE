package cn.easyproject.easyee.sm.hr.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.hr.criteria.DeptCriteria;
import cn.easyproject.easyee.sm.hr.entity.Dept;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface DeptService {
	public void save(Dept dept);
	public void delete(Serializable deptno);
	public void update(Dept dept);
	@Transactional(readOnly=true)
	public Dept get(Integer deptno);

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public void findByPage(PageBean pageBean, DeptCriteria deptCriteria);
	
	@Transactional(readOnly=true)
	public List<Dept> findAll();
}
