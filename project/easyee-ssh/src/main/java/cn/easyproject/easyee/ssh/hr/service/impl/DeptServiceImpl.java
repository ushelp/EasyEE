package cn.easyproject.easyee.ssh.hr.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.ssh.base.service.BaseService;
import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.hr.criteria.DeptCriteria;
import cn.easyproject.easyee.ssh.hr.entity.Dept;
import cn.easyproject.easyee.ssh.hr.service.DeptService;

/**
 * 业务实现类统一继承BaseService类
 * BaseService中注入了通用DAO，直接调用commonDAO的数据方法方法即可
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Service("deptService")
public class DeptServiceImpl extends BaseService implements DeptService {

	@Override
	public void save(Dept dept) {
		commonDAO.persist(dept);
	}

	@Override
	public void delete(Serializable deptno) {
		commonDAO.updateByJpql("delete from Dept where deptno=?",deptno);
	}

	@Override
	public void update(Dept dept) {
		commonDAO.merge(dept);
	}

	@Override
	public Dept get(Integer deptno) {
		return commonDAO.find(Dept.class, deptno);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void findByPage(PageBean pageBean, DeptCriteria deptCriteria) {
		pageBean.setEntityName("Dept dept");
		pageBean.setSelect("select dept");
		
		// 按条件分页查询
		commonDAO.findByPage(pageBean,deptCriteria);
	}

	@Override
	public int findMaxPage(int rowPerPage) {
		return commonDAO.findMaxPage("select count(*) from Dept", rowPerPage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dept> findAll() {
		return commonDAO.find("from Dept");
	}

}
