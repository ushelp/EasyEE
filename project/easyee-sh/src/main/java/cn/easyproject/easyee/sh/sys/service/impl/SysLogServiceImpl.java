package cn.easyproject.easyee.sh.sys.service.impl;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sh.base.service.BaseService;
import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sh.sys.entity.SysLog;
import cn.easyproject.easyee.sh.sys.service.SysLogService;

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
		pb.setSort("logId");
		pb.setSortOrder("desc");
		// 按条件分页查询
		commonDAO.findByPage(pb,sysLogCriteria);
	}

}
