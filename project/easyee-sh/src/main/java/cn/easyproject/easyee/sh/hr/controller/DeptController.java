package cn.easyproject.easyee.sh.hr.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sh.base.controller.BaseController;
import cn.easyproject.easyee.sh.base.util.PageBean;
import cn.easyproject.easyee.sh.base.util.StatusCode;
import cn.easyproject.easyee.sh.hr.criteria.DeptCriteria;
import cn.easyproject.easyee.sh.hr.entity.Dept;
import cn.easyproject.easyee.sh.hr.service.DeptService;
import cn.easyproject.easyee.sh.hr.service.EmpService;
/**
 * 所有Controller处理类统一继承BaseController
 * 
 * BaseController 中定义了一下内容：
 * - request, application Servlet API
 * - 请求响应相关的JSON参数（EasyUI框架请求都是通过JSON响应）
 * - 初始化JSON响应数据的方法（setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean, Object...)）
 * - 获得分页对象：super.getPageBean() 
 * 
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@RestController
@RequestMapping("Dept")
public class DeptController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(DeptController.class);

	
	@Resource
	private DeptService deptService;
	
	@Resource
	private EmpService empService;
	
	/**
	 * 转向显示页面
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView mv){
		mv.setViewName("main/hr/Dept");
		return mv;
	}
	/**
	 * CRUD
	 * @return
	 */
	@RequestMapping("save")
	public Map<Object,Object> save(Dept dept){
		// 保存
		try {
			deptService.save(dept);
			// 处理成功 消息
			super.setMsgKey("msg.saveSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			super.setMsgKey("msg.saveFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		
		/*
		 * Ajax响应信息
		 * statusCode: 响应状态码;  
		 * msg: 响应消息;   
		 * callback: 执行回调函数,
		 * locationUrl: 跳转页面
		 */
		// EasyUI框架响应结果都是JSON
		// JSON数据初始化，包含EasySSH Ajax响应信息
		// super.setJsonMsgMap();
		
		
		// 添加数据后，使用rowData信息更新行的内容
		// 返回JSON
		return super.setJsonMsgMap("rowData", dept);
		
		// 如果需要刷新，跳转到最后一页
//		int page = deptService.findMaxPage(rows);
//		return super.setJsonMsgMap("rowData", dept, "page", page);
	}
	
	/**
	 * 分页
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("list")
	public Map<Object,Object> list(DeptCriteria deptCriteria){
		PageBean pb = super.getPageBean(); // 获得分页对
		deptService.findByPage(pb,deptCriteria);
		// EasyUI框架响应结果都是JSON
		// JSON数据初始化，包含EasySSH Ajax响应信息和分页信息
		return super.setJsonPaginationMap(pb);
	}

	@RequestMapping("delete")
	public Map<Object,Object> delete(Dept dept){
		try {
			if(empService.findEmpCountByDeptno(dept.getDeptno())==0){
				deptService.delete(dept.getDeptno());
			}else{
				super.setMsgKey("hr.DeptController.empExists");
				super.setStatusCode(StatusCode.ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
	}
	@RequestMapping("update")
	public Map<Object,Object> update(Dept dept){
		try {
			deptService.update(dept);
			super.setMsgKey("msg.updateSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			super.setMsgKey("msg.updateFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		
		return super.setJsonMsgMap();
	}
	
}
