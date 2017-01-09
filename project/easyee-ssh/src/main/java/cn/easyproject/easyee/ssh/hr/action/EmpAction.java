package cn.easyproject.easyee.ssh.hr.action;

import cn.easyproject.easyee.ssh.hr.criteria.EmpCriteria;
import cn.easyproject.easyee.ssh.hr.entity.Emp;
import cn.easyproject.easyee.ssh.hr.service.DeptService;
import cn.easyproject.easyee.ssh.hr.service.EmpService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.base.util.StatusCode;

/**
 * 所有Action处理类统一继承BaseAction
 * 
 * BaseAction中定义了一下内容： - request, application Servlet API -
 * 请求响应相关的JSON参数（EasyUI框架请求都是通过JSON响应） -
 * 初始化JSON响应数据的方法（setJsonMap，setJsonMsgMap，setJsonPaginationMap(PageBean,
 * Object...)） - EasyUI分页信息相关的属性 - result="json" 的 JSON 常量
 * 
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@ParentPackage("easyssh-default")
@Namespace("/Emp")
public class EmpAction extends BaseAction {
	public static Logger logger = LoggerFactory.getLogger(EmpAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 必须和@Service("empService")注解声明的业务Bean名称相同
	// 必须有setter方法
	private EmpService empService;
	private DeptService deptService;

	private Emp emp;
	private EmpCriteria empCriteria;

	private String[] empno;// 多行批量删除

	/**
	 * 转向显示页面
	 * 
	 * @return
	 */
	@Action(value = "page", results = { @Result(location = "/WEB-INF/content/main/hr/Emp.jsp") })
	public String page() {
		return SUCCESS;
	}

	/**
	 * CRUD
	 * 
	 * @return
	 */
	@Action("save")
	public String save() {
		// 保存用户
		try {
			empService.save(emp);

			// 处理成功 消息
			msg = getText("msg.saveSuccess");

			// 如果需要刷新，跳转到最后一页
			// super.page = empService.findMaxPage(rows);
		} catch (Exception e) {
			e.printStackTrace();
			msg = getText("msg.saveFail");
			statusCode = StatusCode.ERROR; // 默认为OK
		}

		/*
		 * Ajax响应信息 statusCode: 响应状态码; msg: 响应消息; callback: 执行回调函数, locationUrl:
		 * 跳转页面
		 */
		// EasyUI框架响应结果都是JSON
		// JSON数据初始化，包含EasySSH Ajax响应信息
		// super.setJsonMsgMap();
		// 添加数据后，使用rowData信息更新行的内容
		super.setJsonMsgMap("rowData", emp);

		// 返回JSON
		return JSON;
	}

	/**
	 * 分页
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Action("list")
	public String list() {
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

		super.setJsonPaginationMap(pb, "allDept", deptService.findAll());
		return JSON;
	}

	@Action("delete")
	public String delete() {
		// try {
		// empService.delete(emp.getEmpno());
		// } catch (Exception e) {
		// e.printStackTrace();
		// statusCode=StatusCode.ERROR;
		// }
		// super.setJsonMsgMap();
		// 批量删除
		try {
			empService.delete(empno);
			// sysUserService.delete(sysUser.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
			// 出错输出 500 响应
			// ServletActionContext.getResponse().setStatus(500);
			statusCode = StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}

	@Action("update")
	public String update() {
		try {
			empService.update(emp);
			msg = getText("msg.updateSuccess");

		} catch (Exception e) {
			e.printStackTrace();
			msg = getText("msg.updateFail");
			statusCode = StatusCode.ERROR;
		}
		setJsonMsgMap();
		return JSON;
	}

	/**
	 * 查询所有部门，以JSON返回，添加修改用户时，下拉列表使用
	 * 
	 * @return
	 */
	@Action("allDept")
	public String allDept() {
		setJsonRoot(deptService.findAll());
		return JSON;
	}

	public Emp getEmp() {
		return emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public void setEmpService(EmpService empService) {
		this.empService = empService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public EmpCriteria getEmpCriteria() {
		return empCriteria;
	}

	public void setEmpCriteria(EmpCriteria empCriteria) {
		this.empCriteria = empCriteria;
	}

	public void setEmpno(String[] empno) {
		this.empno = empno;
	}

}
