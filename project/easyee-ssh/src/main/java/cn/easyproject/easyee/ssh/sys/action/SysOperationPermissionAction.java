package cn.easyproject.easyee.ssh.sys.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easyee.ssh.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.ssh.sys.service.SysOperationPermissionService;
import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.StatusCode;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@ParentPackage("easyssh-default")
@Namespace("/SysOperationPermission")
@SuppressWarnings({ "rawtypes"})
public class SysOperationPermissionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger=LoggerFactory.getLogger(SysOperationPermissionAction.class);
	
	private SysOperationPermission sysOperationPermission;
	private SysOperationPermissionService sysOperationPermissionService;
	private String menuId;

	@Override
	@Action(value="page",results={
			@Result(location="/WEB-INF/content/main/sys/sysOperationPermission.jsp")
	})
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	@Action("list")
	public String list(){
		jsonRoot=new Object[]{};
		if(isNotNullAndEmpty(menuId)){
			List list=sysOperationPermissionService.list(Integer.valueOf(menuId));
			jsonRoot=list;
			setJsonMap("rows",list,"sysMenuPermission.menuPermissionId",menuId);
		}
		return JSON;
	}
	@Action("update")
	public String update(){
		try {
			sysOperationPermissionService.update(sysOperationPermission);
			msg=getText("msg.updateSuccess");
			super.reloadPermissions(); //刷新权限
		} catch (Exception e) {
			logger.error(getText("sys.OperationPermissionAction.updateException"),e);
			e.printStackTrace();
			msg=getText("msg.updateFail");
			statusCode=StatusCode.ERROR;
		}
		setJsonMsgMap();
		return JSON;
	}
	@Action("save")
	public String save(){
		try {
			sysOperationPermissionService.add(sysOperationPermission);
			msg=getText("msg.saveSuccess");
			super.reloadPermissions(); //刷新权限
		} catch (Exception e) {
			logger.error(getText("sys.OperationPermissionAction.saveException"),e);
			e.printStackTrace();
			msg=getText("msg.saveFail");
			statusCode=StatusCode.ERROR;
		}
	
		Map<Object,Object> rowData = new HashMap<Object,Object>();
		rowData.put("operationPermissionId", sysOperationPermission.getOperationPermissionId());
		setJsonMsgMap("rowData", rowData);
		return JSON;
	}
	@Action("delete")
	public String delete(){
		try {
			sysOperationPermissionService.delete(sysOperationPermissionService.get(sysOperationPermission.getOperationPermissionId()));;
			msg=getText("msg.deleteSuccess");
			super.reloadPermissions(); //刷新权限
		} catch (Exception e) {
			logger.error(getText("sys.OperationPermissionAction.deleteException"),e);
			e.printStackTrace();
			msg=getText("msg.deleteSuccess");
			statusCode=StatusCode.ERROR;
		}
		setJsonMsgMap();
		return JSON;
	}

	public void setSysOperationPermission(SysOperationPermission sysOperationPermission) {
		this.sysOperationPermission = sysOperationPermission;
	}


	public SysOperationPermission getSysOperationPermission() {
		return sysOperationPermission;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public void setSysOperationPermissionService(
			SysOperationPermissionService sysOperationPermissionService) {
		this.sysOperationPermissionService = sysOperationPermissionService;
	}

	
}
