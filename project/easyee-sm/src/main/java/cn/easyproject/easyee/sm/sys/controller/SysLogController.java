package cn.easyproject.easyee.sm.sys.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sm.base.controller.BaseController;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.sys.criteria.SysLogCriteria;
import cn.easyproject.easyee.sm.sys.service.SysLogService;

@RestController
@RequestMapping("SysLog")
public class SysLogController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(SysLogController.class);
	
	@Resource
	private SysLogService sysLogService;

	
	/**
	 * 转向显示页面
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView mv){
		mv.setViewName("main/sys/sysLog");
		return mv;
	}
	
	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("list")
	public Map<Object, Object> list(SysLogCriteria sysLogCriteria) throws Exception {
		String sort = ServletRequestUtils.getStringParameter(request, "sort", "");
		String order = ServletRequestUtils.getStringParameter(request, "order", "");

		if (!isNotNullAndEmpty(sort) || sort.equals("logTime")) {
			sort = "log_Time";
		}
		if (!isNotNullAndEmpty(order)) {
			order = "desc";
		}

		PageBean pb = super.getPageBean(); // 获得分页对象
		pb.setSort(sort);
		pb.setSortOrder(order);

		sysLogService.findByPage(pb, sysLogCriteria);

		return super.setJsonPaginationMap(pb);
	}

}
