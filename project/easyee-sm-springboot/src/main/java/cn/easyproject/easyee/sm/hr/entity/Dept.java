package cn.easyproject.easyee.sm.hr.entity;

//import java.util.HashSet;
//import java.util.Set;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class Dept implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer deptno;
	private String dname;
	private String loc;
//	private Set moduleEmps = new HashSet(0);

	// Constructors

	/** default constructor */
	public Dept() {
	}

	/** full constructor */
	public Dept(String dname, String loc) {
		this.dname = dname;
		this.loc = loc;
//		this.moduleEmps = moduleEmps;
	}

	// Property accessors

	public Integer getDeptno() {
		return this.deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

//	public Set getModuleEmps() {
//		return this.moduleEmps;
//	}
//
//	public void setModuleEmps(Set moduleEmps) {
//		this.moduleEmps = moduleEmps;
//	}

}