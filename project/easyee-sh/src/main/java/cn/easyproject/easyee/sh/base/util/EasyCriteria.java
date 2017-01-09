package cn.easyproject.easyee.sh.base.util;

import java.util.ArrayList;
import java.util.List;

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
 * @see https://github.com/ushelp/EasyCommonDAO
 * 
 */
public abstract class EasyCriteria {
	protected List<Object> values = new ArrayList<Object>();

	public abstract String getCondition();

	public List<Object> getValues() {
		return values;
	}

	public void setValues(List<Object> values) {
		this.values = values;
	}
}
