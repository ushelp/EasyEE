package cn.easyproject.easyee.ssh.sys.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.easyproject.easyee.ssh.base.action.BaseAction;

/**
 * 
 * 页面跳转
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@ParentPackage("easyssh-default")
public class ForwardAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Action(value="/toLogin",results={
			@Result(location="/WEB-INF/content/login.jsp")
	})
	public String toLogin() {
		return SUCCESS;
	}
	
	@Action(value="/toMain",results={
			@Result(location="/WEB-INF/content/main/main.jsp")
	})
	public String toMain() {
		return SUCCESS;
	}

	@Action(value="/toReports",results={
			@Result(location="/WEB-INF/content/main/reports/doChart.jsp")
	})
	public String toReports() {
		return SUCCESS;
	}

	

}
