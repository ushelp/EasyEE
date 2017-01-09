package cn.easyproject.easyee.sm.hr.dao;

import java.io.Serializable;
import java.util.List;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.hr.entity.Emp;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@SuppressWarnings("rawtypes")
public interface EmpDAO {
	
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
	public void deleteByIds(String[] empnos);
	
	
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
	public Emp get(Integer empno);
	
	/**
	 * Pagination
	 * @param pageBean PageBean
	 * @param empCriteria Criteria
	 */
	public List pagination(PageBean pageBean);
	
	/**
	 * Max Page
	 * @param rowPerPage Row Per Page
	 * @return maxPage
	 */
	public int findEmpCountByDeptno(int deptno);
	
	public int findMaxRow();
}
