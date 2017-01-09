package cn.easyproject.easyee.sh.hr.service;

import java.io.Serializable;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.hr.criteria.EmpCriteria;
import cn.easyproject.easyee.sh.hr.entity.Emp;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Transactional
public interface EmpService {
	
	/**
	 * Save
	 * @param emp Object
	 */
	public void save(Emp emp);
	
	/**
	 * Delete Object
	 * @param empno oid
	 */
	public void delete(Serializable empno);
	
	/**
	 * Delete All
	 * @param empnos oids
	 */
	public void delete(String[] empnos);
	
	/**
	 * Delete All with cascade
	 * @param empno2
	 */
	public void deleteCascade(String[] empnos);
	
	/**
	 * Update
	 * @param emp
	 */
	public void update(Emp emp);
	
	/**
	 * Get
	 * @param empno oid
	 * @return Object
	 */
	@Transactional(readOnly=true)
	public Emp get(Integer empno);
	
	/**
	 * Pagination
	 * @param pageBean PageBean
	 * @param empCriteria Criteria
	 */
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public void findByPage(PageBean pageBean,EmpCriteria empCriteria);
	
	/**
	 * Max Page
	 * @param rowPerPage Row Per Page
	 * @return maxPage
	 */
	@Transactional(readOnly=true)
	public int findMaxPage(int rowPerPage);
	
	/**
	 * Max Page
	 * @param rowPerPage Row Per Page
	 * @return maxPage
	 */
	@Transactional(readOnly=true)
	public int findEmpCountByDeptno(int deptno);
}
