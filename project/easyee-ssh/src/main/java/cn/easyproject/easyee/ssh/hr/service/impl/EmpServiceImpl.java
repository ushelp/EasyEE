package cn.easyproject.easyee.ssh.hr.service.impl;


import java.io.Serializable;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.ssh.base.service.BaseService;
import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.hr.criteria.EmpCriteria;
import cn.easyproject.easyee.ssh.hr.entity.Emp;
import cn.easyproject.easyee.ssh.hr.service.EmpService;

/**
 * 业务实现类统一继承BaseService类
 * BaseService中注入了通用DAO，直接调用commonDAO的数据方法方法即可
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 * 
 */
@Service("empService")
public class EmpServiceImpl extends BaseService implements EmpService {

	@Override
	public void save(Emp emp) {
		commonDAO.persist(emp);
	}

	@Override
	public void delete(Serializable empno) {
		commonDAO.updateByJpql("delete from Emp where empno=?",empno);
	}
	
	@Override
	public void deleteCascade(String[] empnos) {
		commonDAO.deleteCascadeByValues(Emp.class, "empno", empnos);
	}


	@Override
	public void update(Emp emp) {
		commonDAO.merge(emp);
	}

	@Override
	public Emp get(Integer empno) {
		return commonDAO.find(Emp.class, empno);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void findByPage(PageBean pageBean, EmpCriteria empCriteria) {
		pageBean.setEntityName("Emp emp");
		pageBean.setSelect("select emp");
		
		// 按条件分页
		commonDAO.findByPage(pageBean,empCriteria);
	}

	@Override
	public int findMaxPage(int rowPerPage) {
		return commonDAO.findMaxPage("select count(*) from Emp", rowPerPage);
	}

	@Override
	public void delete(String[] empnos) {
		commonDAO.deleteByValues(Emp.class, "empno", empnos);
	}

	@Override
	public int findEmpCountByDeptno(int deptno) {
		return commonDAO.findCount("select count(emp) from Emp emp where emp.dept.deptno=?", deptno);
	}
	
}
