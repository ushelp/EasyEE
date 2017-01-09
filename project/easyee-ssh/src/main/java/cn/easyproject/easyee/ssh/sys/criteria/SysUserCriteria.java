package cn.easyproject.easyee.ssh.sys.criteria;


import cn.easyproject.easyee.ssh.base.util.EasyCriteria;
import cn.easyproject.easyee.ssh.base.util.StringUtils;

/**
 * SysUser查询标准条件类
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 条件属性
	 */
	private String name;
	private String realName;
	private Integer status; // 用户状态：0启用；1禁用；2删除

	 /*
 	 * 2. 构造方法
 	 */
	public SysUserCriteria() {
	}

	public SysUserCriteria(String name, String realName, Integer status) {
		super();
		this.name = name;
		this.realName = realName;
		this.status = status;
	}

	/*
 	 * 3. 条件生成抽象方法实现
 	 */
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotNullAndEmpty(this.getName())) {
			condition.append(" and name like ?");
			values.add("%" + this.getName() + "%");
		}
		if (StringUtils.isNotNullAndEmpty(this.getRealName())) {
			condition.append(" and realName like ?");
			values.add("%" + this.getRealName() + "%");
		}
		if (StringUtils.isNotNullAndEmpty(this.getStatus())) {
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

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


}
