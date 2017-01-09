package cn.easyproject.easyee.ssh.sys.service;

import cn.easyproject.easyee.ssh.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.ssh.sys.entity.SysLog;
import cn.easyproject.easyee.ssh.base.util.PageBean;

public interface SysLogService {
	public void add(SysLog sysLog);
	@SuppressWarnings("rawtypes")
	public void findByPage(PageBean pb,SysLogCriteria sysLogCriteria);
}
