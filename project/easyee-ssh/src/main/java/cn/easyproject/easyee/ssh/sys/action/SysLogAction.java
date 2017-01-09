package cn.easyproject.easyee.ssh.sys.action;


import cn.easyproject.easyee.ssh.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.ssh.sys.service.SysLogService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.PageBean;

@ParentPackage("easyssh-default")
@Namespace("/SysLog")
public class SysLogAction extends BaseAction{
	public static Logger logger = LoggerFactory.getLogger(SysLogAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SysLogService sysLogService;
	private SysLogCriteria sysLogCriteria;
	
	
	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@Action(value="page",results={
			@Result(location="/WEB-INF/content/main/sys/sysLog.jsp")
	})
	public String execute() {
		return SUCCESS;
	}
	@SuppressWarnings("rawtypes")
	@Action("list")
	public String list() throws Exception {
		
		if (!isNotNullAndEmpty(sort)) {
			sort="logTime";
		}
		if (!isNotNullAndEmpty(order)) {
			order="desc";
		}
		
		
		PageBean pb = super.getPageBean(); // 获得分页对象
		pb.setSort(sort);
		pb.setSortOrder(order);
		sysLogService.findByPage(pb, sysLogCriteria);

		super.setJsonPaginationMap(pb);
		return JSON;
	}
	
	public SysLogCriteria getSysLogCriteria() {
		return sysLogCriteria;
	}
	public void setSysLogCriteria(SysLogCriteria sysLogCriteria) {
		this.sysLogCriteria = sysLogCriteria;
	}
	public void setSysLogService(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	
	
	
}
