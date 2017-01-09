package cn.easyproject.easyee.ssh.sys.criteria;


import cn.easyproject.easyee.ssh.base.util.EasyCriteria;
import cn.easyproject.easyee.ssh.base.util.StringUtils;

/**
 * SysRole entity.
 * 
 * @author easyproject.cn
 * @version 1.0
 */
public class SysRoleCriteria extends EasyCriteria implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
 	 * 1. 条件属性
 	 */
	private String name;
	private Integer status;
	 /*
 	 * 2. 构造方法
 	 */
	
	public SysRoleCriteria(String name, Integer status) {
		super();
		this.name = name;
		this.status = status;
	}

	public SysRoleCriteria() {
		super();
	}

	/*
 	 * 3. 条件生成抽象方法实现
 	 */
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if(StringUtils.isNotNullAndEmpty(this.getName())){
			condition.append(" and name like ?");
			values.add("%"+this.getName()+"%");
		}
		if(StringUtils.isNotNullAndEmpty(this.getStatus())){
			condition.append(" and status=?");
			values.add(this.getStatus());
		}
		return condition.toString();
	}
	/*
 	 * 4. Setters & Getters...
 	 */ 
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}