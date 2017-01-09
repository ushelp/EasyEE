package cn.easyproject.easyee.sm.hr.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easyee.sm.base.controller.BaseController;
import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.util.StatusCode;
import cn.easyproject.easyee.sm.hr.criteria.EmpCriteria;
import cn.easyproject.easyee.sm.hr.entity.Emp;
import cn.easyproject.easyee.sm.hr.service.DeptService;
import cn.easyproject.easyee.sm.hr.service.EmpService;

/**
 * 所有Controller处理类统一继承BaseController
 * 
 * BaseController中定义了一下内容：
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
@RequestMapping("Emp")
public class EmpController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(EmpController.class);
	
	@Resource
	private EmpService empService;
	@Resource
	private DeptService deptService;



	/**
	 * 转向显示页面
	 * 
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView mv){
		mv.setViewName("main/hr/Emp");
		return mv;
	}

	/**
	 * CRUD
	 * 
	 * @return
	 */
	@RequestMapping("save")
	public Map<Object, Object> save(Emp emp) {
		// 保存用户
		try {
			empService.save(emp);

			// 处理成功 消息
			super.setMsgKey("msg.saveSuccess");
		} catch (Exception e) {
			e.printStackTrace();
			super.setMsgKey("msg.saveFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK // 默认为OK
		}

		/*
		 * Ajax响应信息 statusCode: 响应状态码; msg: 响应消息; callback: 执行回调函数, locationUrl:
		 * 跳转页面
		 */
		// EasyUI框架响应结果都是JSON
		// JSON数据初始化，包含EasySSH Ajax响应信息
		// super.setJsonMsgMap();
		// 添加数据后，使用rowData信息更新行的内容
		return super.setJsonMsgMap("rowData", emp);
		
		// 如果需要刷新，跳转到最后一页
//		int page = empService.findMaxPage(rows);
//		return super.setJsonMsgMap("rowData", dept, "page", page);
	}

	/**
	 * 分页
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("list")
	public Map<Object, Object> list(EmpCriteria empCriteria) {
		PageBean pb = super.getPageBean(); // 获得分页对

		empService.findByPage(pb, empCriteria);
		// EasyUI框架响应结果都是JSON
		// JSON数据初始化，包含EasySSH Ajax响应信息和分页信息
		// 使用抽取的集合作为分页数据
		// List<Map> list = EasyObjectExtract.extract(pb.getData(),
		// "empno","ename",
		// "job", "dept.dname#dname");
		// // 使用抽取的集合作为分页数据
		// pb.setData(list);

		return super.setJsonPaginationMap(pb, "allDept", deptService.findAll());
	}

	@RequestMapping("delete")
	public Map<Object, Object> delete(String[] empno) {
		// try {
		// empService.delete(emp.getEmpno());
		// } catch (Exception e) {
		// e.printStackTrace();
		// super.setStatusCode(StatusCode.ERROR); //默认为OK
		// }
		// super.setJsonMsgMap();
		// 批量删除
		try {
			empService.delete(empno);
			// sysUserService.delete(sysUser.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			// 出错输出 500 响应
			// ServletControllerContext.getResponse().setStatus(500);
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
	}

	@RequestMapping("update")
	public Map<Object, Object> update(Emp emp) {
		try {
			empService.update(emp);
			super.setMsgKey("msg.updateSuccess");

		} catch (Exception e) {
			e.printStackTrace();
			super.setMsgKey("msg.updateFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
	}

	/**
	 * 查询所有部门，以JSON返回，添加修改用户时，下拉列表使用
	 * 
	 * @return
	 */
	@RequestMapping("allDept")
	public Object allDept() {
		return deptService.findAll();
	}

}
