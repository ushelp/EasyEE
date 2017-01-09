package cn.easyproject.easyee.sm.hr.criteria;

import cn.easyproject.easyee.sm.base.pagination.EasyCriteria;
import cn.easyproject.easyee.sm.base.util.StringUtils;


/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class DeptCriteria extends EasyCriteria implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
 	 * 1. 条件属性
 	 */
	private String dname;
	private String loc;
	
	
	 /*
 	 * 2. 构造方法
 	 */
	public DeptCriteria() {
		super();
	}

	public DeptCriteria(String dname, String loc) {
		super();
		this.dname = dname;
		this.loc = loc;
	}

	 /*
 	 * 3. 条件生成抽象方法实现
 	 */
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotNullAndEmpty(this.getDname())) {
			condition.append(" and dname like #{dname}");
			values.put("dname", "%" + this.getDname() + "%");
		}
		if (StringUtils.isNotNullAndEmpty(this.getLoc())) {
			condition.append(" and loc like #{loc}");
			values.put("loc", "%" + this.getLoc() + "%");
		}
		return condition.toString();
	}
	 /*
 	 * 4. Setters & Getters...
 	 */ 
	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}
}