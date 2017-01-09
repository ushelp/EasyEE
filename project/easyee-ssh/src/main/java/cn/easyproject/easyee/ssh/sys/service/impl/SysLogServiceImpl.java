package cn.easyproject.easyee.ssh.sys.service.impl;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.ssh.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.ssh.sys.entity.SysLog;
import cn.easyproject.easyee.ssh.sys.service.SysLogService;
import cn.easyproject.easyee.ssh.base.service.BaseService;
import cn.easyproject.easyee.ssh.base.util.PageBean;

@Service("sysLogService")
public class SysLogServiceImpl extends BaseService  implements SysLogService {

	@Override
	public void add(SysLog sysLog) {
		commonDAO.persist(sysLog);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void findByPage(PageBean pb, SysLogCriteria sysLogCriteria) {
		pb.setEntityName("SysLog s");
		pb.setSelect("select s");
		pb.setSort("s.logId");
		pb.setSortOrder("desc");
		// 按条件分页查询
		commonDAO.findByPage(pb,sysLogCriteria);
	}

}
