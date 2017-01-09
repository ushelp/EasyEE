package cn.easyproject.easyee.sh.hr.criteria;

import cn.easyproject.easyee.sh.base.util.EasyCriteria;
import cn.easyproject.easyee.sh.base.util.StringUtils;

/**
 * ModuleEmp entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class EmpCriteria extends EasyCriteria implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 1. 条件属性
	 */
	private Integer empno;
	private Integer deptno;
	private String ename;
	private String job;

	/*
	 * 2. 构造方法
	 */
	public EmpCriteria() {
		super();
	}

	/*
	 * 3. 条件生成抽象方法实现
	 */
	@Override
	public String getCondition() {
		values.clear(); // 清除条件数据
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotNullAndEmpty(this.getEname())) {
			condition.append(" and ename like ?");
			values.add("%" + this.getEname() + "%");
		}
		if (StringUtils.isNotNullAndEmpty(this.getEmpno())) {
			condition.append(" and empno=?");
			values.add(this.getEmpno());
		}
		if (StringUtils.isNotNullAndEmpty(this.getDeptno())) {
			condition.append(" and dept.deptno=?");
			values.add(this.getDeptno());
		}
		if (StringUtils.isNotNullAndEmpty(this.getJob())) {
			condition.append(" and job like ?");
			values.add("%" + this.getJob() + "%");
		}
		return condition.toString();
	}

	/*
	 * 4. Setters & Getters...
	 */
	public Integer getEmpno() {
		return empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Integer getDeptno() {
		return deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}