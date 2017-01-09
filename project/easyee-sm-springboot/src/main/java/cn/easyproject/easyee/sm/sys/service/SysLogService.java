package cn.easyproject.easyee.sm.sys.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sm.sys.entity.SysLog;

@Transactional
public interface SysLogService {
	public void add(SysLog sysLog);
	@SuppressWarnings("rawtypes")
	@Transactional(readOnly=true)
	public void findByPage(PageBean pb,SysLogCriteria sysLogCriteria);
}
