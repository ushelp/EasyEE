package cn.easyproject.easyee.sh.base.util;


/**
 * 按条件查询抽象父类（可以结合分页PageBean使用）
 * 
 * SysUserCriteria Demo: <br>
 *
 * <pre><code>
 * public class SysUserCriteria extends EasyCriteria implements java.io.Serializable {
 * 	// 1. Criteria field
 * 	private String name;
 * 	private String realName;
 * 	private Integer status; // 0 is ON; 1 is OFF; 2 is REMOVED
 * 
 * 	// 2. Constructor
 * 	public SysUserCriteria() {
 * 	}
 * 
 * 	public SysUserCriteria(String name, String realName, Integer status) {
 * 		super();
 * 		this.name = name;
 * 		this.realName = realName;
 * 		this.status = status;
 * 	}
 * 
 * 	// 3. Condition genertator abstract method implements
 * 	public String getCondition() {
 * 		values.clear(); // **Must clear old values**
 * 		StringBuffer condition = new StringBuffer();
 * 		if (StringUtils.isNotNullAndEmpty(this.getName())) {
 * 			condition.append(" and name like ?");
 * 			values.add("%" + this.getName() + "%");
 * 		}
 * 		if (StringUtils.isNotNullAndEmpty(this.getRealName())) {
 * 			condition.append(" and realName like ?");
 * 			values.add("%" + this.getRealName() + "%");
 * 		}
 * 		if (StringUtils.isNotNullAndEmpty(this.getStatus())) {
 * 			condition.append(" and status=?");
 * 			values.add(this.getStatus());
 * 		}
 * 		return condition.toString();
 * 	}
 * 
 * 	// 4. Setters&amp;Getters...
 * 
 * }
 * </code></pre>
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since 1.0.0
 */


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
