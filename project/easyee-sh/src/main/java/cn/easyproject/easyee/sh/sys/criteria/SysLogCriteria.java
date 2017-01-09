package cn.easyproject.easyee.sh.sys.criteria;


import java.text.ParseException;
import java.text.SimpleDateFormat;

import cn.easyproject.easyee.sh.base.util.EasyCriteria;
import cn.easyproject.easyee.sh.base.util.StringUtils;

/**
 * SysLog查询标准条件类
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
public class SysLogCriteria extends EasyCriteria implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * 条件属性
	 */
	private String startTime;
	private String endTime;
	private String account; 
	private String ip; 

	 /*
 	 * 2. 构造方法
 	 */
	public SysLogCriteria() {
	}

	

	public SysLogCriteria(String startTime, String endTime, String account,
			String ip) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.account = account;
		this.ip = ip;
	}


	/*
 	 * 3. 条件生成抽象方法实现
 	 */
	public String getCondition() {
		values.clear(); //清除条件数据
		StringBuffer condition = new StringBuffer();
		if (StringUtils.isNotNullAndEmpty(this.getIp())) {
			condition.append(" and ip like ?");
			values.add("%"+this.getIp()+"%");
		}
		if (StringUtils.isNotNullAndEmpty(this.getAccount())) {
			condition.append(" and account like ?");
			values.add("%"+this.getAccount()+"%");
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (StringUtils.isNotNullAndEmpty(this.getStartTime())) {
			try {
				values.add(sdf.parse(this.getStartTime()));
				condition.append(" and logTime>=?");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotNullAndEmpty(this.getEndTime())) {
			try {
				values.add(sdf.parse(this.getEndTime()));
				condition.append(" and logTime<=?");
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return condition.toString();
	}



	public String getStartTime() {
		return startTime;
	}



	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}



	public String getEndTime() {
		return endTime;
	}



	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



	public String getAccount() {
		return account;
	}



	public void setAccount(String account) {
		this.account = account;
	}



	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}

	/*
 	 * 4. Setters & Getters...
 	 */ 
	


}
