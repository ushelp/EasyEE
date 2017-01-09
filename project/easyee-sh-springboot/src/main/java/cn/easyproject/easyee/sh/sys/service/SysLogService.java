package cn.easyproject.easyee.sh.sys.service;

import org.springframework.transaction.annotation.Transactional;

import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysLog;

@Transactional
public interface SysLogService {
	public void add(SysLog sysLog);

	@SuppressWarnings("rawtypes")
	@Transactional(readOnly = true)
	public void findByPage(PageBean pb, SysLogCriteria sysLogCriteria);
}
