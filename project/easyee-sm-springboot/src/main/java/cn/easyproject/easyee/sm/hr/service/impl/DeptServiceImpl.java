package cn.easyproject.easyee.sm.hr.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.hr.criteria.DeptCriteria;
import cn.easyproject.easyee.sm.hr.dao.DeptDAO;
import cn.easyproject.easyee.sm.hr.entity.Dept;
import cn.easyproject.easyee.sm.hr.service.DeptService;

/**
 * 业务实现类统一继承 BaseService 类
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Service("deptService")
public class DeptServiceImpl extends BaseService implements DeptService {

	@Resource
	DeptDAO deptDAO;
	
	@Override
	public void save(Dept dept) {
		deptDAO.save(dept);
	}

	@Override
	public void delete(Serializable deptno) {
		deptDAO.delete(deptno);
	}

	@Override
	public void update(Dept dept) {
		deptDAO.update(dept);
	}

	@Override
	public Dept get(Integer deptno) {
		return deptDAO.get(deptno);
	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public void findByPage(PageBean pageBean, DeptCriteria deptCriteria) {
		pageBean.setFrom("module_dept dept");
		pageBean.setSelect("dept.*");
		pageBean.setEasyCriteria(deptCriteria);
		// 按条件分页查询
		deptDAO.pagination(pageBean);
	}

	@Override
	public List<Dept> findAll() {
		return deptDAO.findAll();
	}
}
