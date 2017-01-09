package cn.easyproject.easyee.ssh.sys.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.ssh.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.ssh.sys.util.EasyUIUtil;
import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.StatusCode;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@ParentPackage("easyssh-default")
@Namespace("/SysMenuPermission")
@SuppressWarnings({ "rawtypes","unchecked" })
public class SysMenuPermissionAction extends BaseAction {

	public static Logger logger=LoggerFactory.getLogger(SysMenuPermissionAction.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SysMenuPermission sysMenuPermission;
	private SysMenuPermissionService sysMenuPermissionService;

	private boolean up;

	
	
	@Override
	@Action(value="page",results={
			@Result(location="/WEB-INF/content/main/sys/sysMenuPermission.jsp")
	})
	public String execute() throws Exception {
		return SUCCESS;
	}
	
	@Action("save")
	public String save() throws Exception {
		msg = getText("msg.saveSuccess");
		try {
			//设置排序位置为最大
			sysMenuPermission.setSortNum(sysMenuPermissionService.getMaxSortNum(sysMenuPermission.getSysMenuPermission().getMenuPermissionId())+1);
			//添加根节点
			if(sysMenuPermission.getSysMenuPermission().getMenuPermissionId()==-1){
				sysMenuPermission.setSysMenuPermission(null);
			}
			sysMenuPermissionService.add(sysMenuPermission);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionAction.saveException"), e);
			msg = getText("msg.saveFail");
			statusCode=StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}
	/**
	 * 更新
	 * @return
	 * @throws Exception
	 */
	@Action("update")
	public String update() throws Exception {
		msg = getText("msg.updateSuccess");
		try {
			SysMenuPermission sysMenuPermissionOld=sysMenuPermissionService.get(sysMenuPermission.getMenuPermissionId());
			
			//排序位置不变
			sysMenuPermission.setSortNum(sysMenuPermissionOld.getSortNum());
			//角色信息不变
			sysMenuPermission.setSysRoles(sysMenuPermissionOld.getSysRoles());
			
			if(sysMenuPermission.getSysMenuPermission()!=null&&sysMenuPermission.getSysMenuPermission().getMenuPermissionId()==null){
				sysMenuPermission.setSysMenuPermission(null);
			}
			
			sysMenuPermissionService.update(sysMenuPermission);
			reloadPermissions(); //自动刷新菜单权限
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionAction.updateException"), e);
			msg = getText("msg.updateFail");
			statusCode=StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}
	/**
	 * 更新
	 * @return
	 * @throws Exception
	 */
	@Action("move")
	public String move() throws Exception {
		msg = getText("msg.moveSuccess");
		try {
			sysMenuPermissionService.move(sysMenuPermission.getMenuPermissionId(), up);
			reloadPermissions(); //自动刷新菜单权限
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionAction.moveException"), e);
			msg = getText("msg.moveFail");
			statusCode=StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}
	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	@Action("delete")
	public String delete() throws Exception {
		msg = getText("msg.deleteSuccess");
		try {
			
			sysMenuPermission=sysMenuPermissionService.get(sysMenuPermission.getMenuPermissionId());
			if(sysMenuPermissionService.hashChildMenu(sysMenuPermission.getMenuPermissionId())){
				msg = getText("sys.MenuPermissionAction.deleteMsg");
				statusCode=StatusCode.ERROR;
			}else{
				sysMenuPermissionService.delete(sysMenuPermission);
				reloadPermissions(); //自动刷新菜单权限
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionAction.deleteException"), e);
			msg = getText("sys.MenuPermissionAction.deleteException");
			statusCode=StatusCode.ERROR;
		}
		super.setJsonMsgMap();
		return JSON;
	}

	/**
	 * 获得菜单权限，单个根节点
	 * @return
	 * @throws Exception
	 */
	@Action("list")
	public String list() throws Exception {
		List menus=sysMenuPermissionService.list();
		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeFromRootMenu(menus);
		jsonRoot=list;
		return JSON;
	}
	/**
	 * 获得菜单权限和操作权限，listAllForSysRole可以防止菜单和操作权限id重复
	 * @return
	 * @throws Exception
	 */
//	public String listAll() throws Exception {
//		List menus=sysMenuPermissionService.listAll();
//		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeFromMenuAndOperation(menus);
//		jsonRoot=list;
//		return JSON;
//	}
	
	/**
	 * 针对角色加载权限，获得菜单权限和操作权限 <br/>
	 * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
	 * @return
	 * @throws Exception
	 */
	@Action("listAllForSysRole")
	public String listAllForSysRole() throws Exception {
		List menus=sysMenuPermissionService.listAll();
		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeUsePrefixIdFromMenuAndOperation(menus);
		jsonRoot=list;
		return JSON;
	}
	
	/**
	 * 获得菜单权限，允许多个根节点，根节点ID为-1
	 * @return
	 * @throws Exception
	 */
	@Action("list2")
	public String list2() throws Exception {
		List menus=sysMenuPermissionService.list();
		List<EasyUITreeEntity> list=EasyUIUtil.getEasyUITreeFromRootMenu(menus);
		list.add(0, new EasyUITreeEntity("-1", "根节点"));
		jsonRoot=list;
		return JSON;
	}
	
	/**
	 * 使用SysMenuPermission直接生成treegrid，不用转换为EasyUITreeEntity
	 * 仅测试，未使用
	 * @return
	 * @throws Exception
	 */
	@Action("list_none")
	private String list_none() throws Exception {
		List menus=sysMenuPermissionService.list();
		//treeGrid格式1：children层级
//		EasyObjectSetNull.setNull(menus, "sysOperationPermissions","{sysMenuPermissions}.sysOperationPermissions");
//		jsonRoot=new Object[]{EasyObjectExtract.extract(menus.get(0), "menuPermissionId","action","icon","name","remark","sortNum","sysMenuPermission","sysMenuPermissions#children")};

		//treeGrid格式2：_parentId
		setJsonMap("rows",EasyObjectExtract.extract(menus, "menuPermissionId","action","icon","name","remark","sortNum","sysMenuPermission.menuPermissionId#_parentId"));
		
		return JSON;
	}

	

	public SysMenuPermission getSysMenuPermission() {
		return sysMenuPermission;
	}

	public void setSysMenuPermission(SysMenuPermission sysMenuPermission) {
		this.sysMenuPermission = sysMenuPermission;
	}

	public SysMenuPermissionService getSysMenuPermissionService() {
		return sysMenuPermissionService;
	}

	public void setSysMenuPermissionService(SysMenuPermissionService sysMenuPermissionService) {
		this.sysMenuPermissionService = sysMenuPermissionService;
	}
	public void setUp(boolean up) {
		this.up = up;
	}

}
