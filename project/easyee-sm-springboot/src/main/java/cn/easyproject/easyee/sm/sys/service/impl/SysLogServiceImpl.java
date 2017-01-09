package cn.easyproject.easyee.sm.sys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sm.sys.dao.SysLogDAO;
import cn.easyproject.easyee.sm.sys.entity.SysLog;
import cn.easyproject.easyee.sm.sys.service.SysLogService;

@Service("sysLogService")
@SuppressWarnings("rawtypes")
public class SysLogServiceImpl extends BaseService  implements SysLogService {
	@Resource
	SysLogDAO sysLogDAO;
	
	@Override
	public void add(SysLog sysLog) {
		sysLogDAO.save(sysLog);
	}
	@Override
	public void findByPage(PageBean pageBean, SysLogCriteria sysLogCriteria) {
		pageBean.setSelect("*");
		pageBean.setFrom("sys_log s");
		pageBean.setEasyCriteria(sysLogCriteria);
		// 按条件分页查询
		sysLogDAO.pagination(pageBean);
	}

}
