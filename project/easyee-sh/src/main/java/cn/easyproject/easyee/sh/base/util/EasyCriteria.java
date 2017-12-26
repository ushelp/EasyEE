package cn.easyproject.easyee.sh.base.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 按条件查询抽象父类（可以结合分页PageBean使用）
 * @author easyproject.cn
 * @version 1.0
 * 
 * SysUser查询标准条件类使用Demo: <br/>

 <pre>
 public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
 	// 1. 添加条件属性
 	private String name;
 	private String realName;
 	private Integer status; // 用户状态：0启用；1禁用；2删除
 
 	// 2. 生成构造方法
 	public SysUserCriteria() {
 	}
 
 	public SysUserCriteria(String name, String realName, Integer status) {
 		super();
 		this.name = name;
 		this.realName = realName;
 		this.status = status;
 	}
 
 	// 3. 条件生成抽象方法实现
 	public String getCondition() {
		values.clear(); //清除条件数据
 		StringBuffer condition = new StringBuffer();
 		if (StringUtils.isNotNullAndEmpty(this.getName())) {
 			condition.append(" and name like :name");
 			values.put("name", "%" + this.getName() + "%");
 		}
 		if (StringUtils.isNotNullAndEmpty(this.getRealName())) {
 			condition.append(" and realName like :realName");
 			values.put("realName", "%" + this.getRealName() + "%");
 		}
 		if (StringUtils.isNotNullAndEmpty(this.getStatus())) {
 			condition.append(" and status=:status");
 			values.add("status", this.getStatus());
 		}
 		return condition.toString();
 	}
 
 	// 4. Setters&Getters...
 
 }
 </pre>
 */
public abstract class EasyCriteria {
	
	protected Map<String, Object> values = new HashMap<String, Object>();
	
	public abstract String getCondition() ;
	
	
	public Map<String, Object> getValues() {
		return values;
	}
	public void setValues( Map<String, Object> values) {
		this.values = values;
	}
}
