package cn.easyproject.easyee.ssh.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.ssh.sys.criteria.SysRoleCriteria;
import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.ssh.sys.entity.SysRole;
import cn.easyproject.easyee.ssh.sys.service.SysRoleService;
import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.PageBean;
import cn.easyproject.easyee.ssh.base.util.StatusCode;
/**
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@ParentPackage("easyssh-default")
@Namespace("/SysRole")
@SuppressWarnings("unchecked")
public class SysRoleAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger=LoggerFactory.getLogger(SysRoleAction.class);
	
	private SysRole sysRole;
	private SysRoleCriteria sysRoleCriteria;
	private SysRoleService sysRoleService;
	private String menus;
	private String operations;
	
	@Override
	@Action(value="page",results={
			@Result(location="/WEB-INF/content/main/sys/sysRole.jsp")
	})
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * 获得所有角色
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Action("list")
	public String list() throws Exception {
		PageBean pb = super.getPageBean(); // 获得分页对
		
		sysRoleService.findByPage(pb,sysRoleCriteria);

		// 从分页集合中抽取需要输出的分页数据，不需要的不用输出，而且可以防止no session异常
		Map<String, String> fieldMap = new HashMap<String, String>();

		List<Map> list = EasyObjectExtract.extract(pb.getData(), fieldMap,
				"roleId", "name", "status", "remark");
		// 使用抽取的集合作为分页数据
		pb.setData(list);

		super.setJsonPaginationMap(pb);
		return JSON;
	}
	/**
	 * 获得所有角色
	 * @return
	 * @throws Exception
	 */
	@Action("all")
	public String all() throws Exception {
		super.setJsonMap("list",sysRoleService.list());
		return JSON;
	}
	/**
	 * 获得所有权限的Id（菜单权限+操作权限），为了防止id重复，使用前缀标识
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Action("getAllPermissionsId")
	public String getAllPermissionsId()  {
		List<Map> all=sysRoleService.getAllPermissionsIds(sysRole.getRoleId());
		List allPermissions=new ArrayList();
		for (Map res : all) {
			allPermissions.add(res.get("TYPE")+"_"+res.get("ID"));
		}
		super.setJsonMap("list",allPermissions);
//		System.out.println("###############################：：："+allPermissions);
		return JSON;
	}
	
	/**
	 * 添加角色
	 * @return
	 */
	@Action("save")
	public String save()  {
		msg = getText("msg.saveFail");
		statusCode=StatusCode.ERROR;
		try {
			
			if(sysRoleService.existsName(sysRole.getName())){
				msg=getText("sys.RoleAction.roleExists");
			}else{
				//设置角色对应的菜单权限
				if(isNotNullAndEmpty(menus)){
					String[] menuIds=menus.split("#");
					for (String menuId : menuIds) {
						SysMenuPermission menuPermission=new SysMenuPermission();
						menuPermission.setMenuPermissionId(Integer.valueOf(menuId.replace("menu_", "")));
						sysRole.getSysMenuPermissions().add(menuPermission);
					}
				}
				//设置角色对应的操作权限
				if(isNotNullAndEmpty(operations)){
					String[] operationIds=operations.split("#");
					for (String operationId : operationIds) {
						SysOperationPermission operationPermission=new SysOperationPermission();
						operationPermission.setOperationPermissionId(Integer.valueOf(operationId.replace("operation_", "")));
						sysRole.getSysOperationPermissions().add(operationPermission);
					}
				}
				
				sysRoleService.add(sysRole);
				msg = getText("msg.saveSuccess");
				statusCode=StatusCode.OK;
				
				// 跳转到最后一页
				super.page = sysRoleService.findMaxPage(rows);
				super.reloadPermissions(); //刷新权限
			}
		} catch (Exception e) {
			logger.error(getText("sys.RoleAction.saveException"), e);
			e.printStackTrace();
		}
		setJsonMsgMap();
		return JSON;
	}
	/**
	 * 修改角色
	 * @return
	 */
	@Action("update")
	public String update()  {
		msg = getText("msg.updateFail");
		statusCode=StatusCode.ERROR;
		try {
			
			if(sysRoleService.existsName(sysRole.getName(), sysRole.getRoleId())){
				msg=getText("sys.RoleAction.roleExists");
			}else{
				SysRole sysRole2=sysRoleService.get(sysRole.getRoleId());
				//不修改用户角色关系
				sysRole.setSysUsers(sysRole2.getSysUsers());
				
				//设置角色对应的菜单权限
				if(isNotNullAndEmpty(menus)){
					String[] menuIds=menus.split("#");
					for (String menuId : menuIds) {
						SysMenuPermission menuPermission=new SysMenuPermission();
						menuPermission.setMenuPermissionId(Integer.valueOf(menuId.replace("menu_", "")));
						sysRole.getSysMenuPermissions().add(menuPermission);
					}
				}
				//设置角色对应的操作权限
				if(isNotNullAndEmpty(operations)){
					String[] operationIds=operations.split("#");
					for (String operationId : operationIds) {
						SysOperationPermission operationPermission=new SysOperationPermission();
						operationPermission.setOperationPermissionId(Integer.valueOf(operationId.replace("operation_", "")));
						sysRole.getSysOperationPermissions().add(operationPermission);
					}
				}
				
				sysRoleService.update(sysRole);
				msg = getText("msg.updateSuccess");
				statusCode=StatusCode.OK;
				
				// 跳转到最后一页
				super.page = sysRoleService.findMaxPage(rows);
				super.reloadPermissions(); //刷新权限
			}
		} catch (Exception e) {
			logger.error(getText("sys.RoleAction.updateException"), e);
			e.printStackTrace();
		}
		setJsonMsgMap();
		return JSON;
	}
	
	/**
	 * 删除角色
	 * 
	 * @return
	 */
	@Action("delete")
	public String delete() {
		try {
			sysRoleService.delete(sysRole.getRoleId());
			super.reloadPermissions(); //刷新权限
		} catch (Exception e) {
			e.printStackTrace();
			statusCode=StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}


	public SysRole getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRole sysRole) {
		this.sysRole = sysRole;
	}

	public void setSysRoleService(SysRoleService sysRoleService) {
		this.sysRoleService = sysRoleService;
	}

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}

	public String getOperations() {
		return operations;
	}

	public void setOperations(String operations) {
		this.operations = operations;
	}

	public SysRoleCriteria getSysRoleCriteria() {
		return sysRoleCriteria;
	}

	public void setSysRoleCriteria(SysRoleCriteria sysRoleCriteria) {
		this.sysRoleCriteria = sysRoleCriteria;
	}

	
}
