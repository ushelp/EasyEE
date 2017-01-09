package cn.easyproject.easyee.sm.sys.dao;

import java.util.List;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.sys.entity.SysLog;

@SuppressWarnings("rawtypes")
public interface SysLogDAO {
	public void save(SysLog sysLog);
	public List pagination(PageBean pb);
}
