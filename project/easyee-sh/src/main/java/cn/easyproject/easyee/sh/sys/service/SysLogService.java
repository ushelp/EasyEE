package cn.easyproject.easyee.sh.sys.service;

import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysLog;

public interface SysLogService {
	public void add(SysLog sysLog);
	@SuppressWarnings("rawtypes")
	public void findByPage(PageBean pb,SysLogCriteria sysLogCriteria);
}
