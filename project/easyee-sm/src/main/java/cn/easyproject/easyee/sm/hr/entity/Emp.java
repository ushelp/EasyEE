package cn.easyproject.easyee.sm.hr.entity;

/**
 * ModuleEmp entity. @author MyEclipse Persistence Tools
 */
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class Emp implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer empno;
	private Dept dept;
	private String ename;
	private String job;

	// Constructors

	/** default constructor */
	public Emp() {
	}

	/** full constructor */
	public Emp(Dept dept, String ename, String job) {
		this.dept = dept;
		this.ename = ename;
		this.job = job;
	}

	// Property accessors

	public Integer getEmpno() {
		return this.empno;
	}

	public void setEmpno(Integer empno) {
		this.empno = empno;
	}

	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}