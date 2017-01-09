package cn.easyproject.easyee.sm.hr.service.impl;


import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.hr.criteria.EmpCriteria;
import cn.easyproject.easyee.sm.hr.dao.EmpDAO;
import cn.easyproject.easyee.sm.hr.entity.Emp;
import cn.easyproject.easyee.sm.hr.service.EmpService;

/**
 * 业务实现类统一继承 BaseService 类
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 * 
 */
@Service("empService")
public class EmpServiceImpl extends BaseService implements EmpService {

	
	@Resource
	EmpDAO empDAO;
	
	@Override
	public void save(Emp emp) {
		empDAO.save(emp);
	}

	@Override
	public void delete(Serializable empno) {
		empDAO.delete(empno);
	}
	

	@Override
	public void update(Emp emp) {
		empDAO.update(emp);
	}

	@Override
	public Emp get(Integer empno) {
		return empDAO.get(empno);
	}

	@SuppressWarnings({ "rawtypes" })
	@Override
	public void findByPage(PageBean pageBean, EmpCriteria empCriteria) {
		pageBean.setFrom(" module_emp e, module_dept d ");
		pageBean.setSelect("e.empno, e.ename, e.job, d.deptno, d.dname");
		pageBean.addCondition("and e.deptno=d.deptno");
		pageBean.setPrimaryTable("e");
		pageBean.setEasyCriteria(empCriteria);
		// 按条件分页查询
		empDAO.pagination(pageBean);
	}


	@Override
	public void delete(String[] empnos) {
		empDAO.deleteByIds(empnos);
	}

	@Override
	public int findEmpCountByDeptno(int deptno) {
		return empDAO.findEmpCountByDeptno(deptno);
	}

}
