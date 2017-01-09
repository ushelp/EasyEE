package cn.easyproject.easyee.sh.sys.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.easyproject.easycommons.objectutils.EasyObjectExtract;
import cn.easyproject.easyee.sh.base.controller.BaseController;
import cn.easyproject.easyee.sh.base.util.StatusCode;
import cn.easyproject.easyee.sh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.sh.sys.util.EasyUITreeEntity;
import cn.easyproject.easyee.sh.sys.util.EasyUIUtil;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@RestController
@RequestMapping("SysMenuPermission")
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class SysMenuPermissionController extends BaseController {

	public static Logger logger = LoggerFactory.getLogger(SysMenuPermissionController.class);

	private static final long serialVersionUID = 1L;

	@Resource
	private SysMenuPermissionService sysMenuPermissionService;
	
	
	
	/**
	 * 转向显示页面
	 * @return
	 */
	@RequestMapping("page")
	public ModelAndView page(ModelAndView mv){
		mv.setViewName("main/sys/sysMenuPermission");
		return mv;
	}

	@RequestMapping("save")
	public Map<Object, Object> save(SysMenuPermission sysMenuPermission) throws Exception {
		super.setMsgKey("msg.saveSuccess");
		try {
			// 设置排序位置为最大
			sysMenuPermission.setSortNum(sysMenuPermissionService
					.getMaxSortNum(sysMenuPermission.getSysMenuPermission().getMenuPermissionId()) + 1);
			// 添加根节点
			if (sysMenuPermission.getSysMenuPermission().getMenuPermissionId() == -1) {
				sysMenuPermission.setSysMenuPermission(null);
			}
			sysMenuPermissionService.add(sysMenuPermission);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionController.saveException"), e);
			super.setMsgKey("msg.saveFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
	}

	/**
	 * 更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("update")
	public Map<Object, Object> update(SysMenuPermission sysMenuPermission) throws Exception {
		super.setMsgKey("msg.updateSuccess");
		try {
			SysMenuPermission sysMenuPermissionOld = sysMenuPermissionService
					.get(sysMenuPermission.getMenuPermissionId());

			// 排序位置不变
			sysMenuPermission.setSortNum(sysMenuPermissionOld.getSortNum());
			// 角色信息不变
			sysMenuPermission.setSysRoles(sysMenuPermissionOld.getSysRoles());

			if (sysMenuPermission.getSysMenuPermission() != null
					&& sysMenuPermission.getSysMenuPermission().getMenuPermissionId() == null) {
				sysMenuPermission.setSysMenuPermission(null);
			}
			sysMenuPermissionService.update(sysMenuPermission);
			reloadPermissions(); // 自动刷新菜单权限
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionController.updateException"), e);
			super.setMsgKey("msg.updateFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
		
	}

	/**
	 * 更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("move")
	public Map<Object, Object> move(SysMenuPermission sysMenuPermission,boolean up) throws Exception {
		super.setMsgKey("msg.moveSuccess");
		try {
			sysMenuPermissionService.move(sysMenuPermission.getMenuPermissionId(), up);
			reloadPermissions(); // 自动刷新菜单权限

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionController.moveException"), e);
			super.setMsgKey("msg.moveFail");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
		
	}

	/**
	 * 删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("delete")
	public Map<Object, Object> delete(SysMenuPermission sysMenuPermission) throws Exception {
		super.setMsgKey("msg.deleteSuccess");
		try {

			sysMenuPermission = sysMenuPermissionService.get(sysMenuPermission.getMenuPermissionId());
			if (sysMenuPermissionService.hashChildMenu(sysMenuPermission.getMenuPermissionId())) {
				super.setMsgKey("sys.MenuPermissionController.deleteMsg");
				super.setStatusCode(StatusCode.ERROR); //默认为OK
			} else {
				sysMenuPermissionService.delete(sysMenuPermission);
				reloadPermissions(); // 自动刷新菜单权限
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(getText("sys.MenuPermissionController.deleteException"), e);
			super.setMsgKey("sys.MenuPermissionController.deleteException");
			super.setStatusCode(StatusCode.ERROR); //默认为OK
		}
		return super.setJsonMsgMap();
	}

	/**
	 * 获得菜单权限，单个根节点
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list")
	public Object list() throws Exception {
		List menus = sysMenuPermissionService.list();
		List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeFromRootMenu(menus);
		return list;
	}
	/**
	 * 获得菜单权限和操作权限，listAllForSysRole可以防止菜单和操作权限id重复
	 * 
	 * @return
	 * @throws Exception
	 */
	// public String listAll() throws Exception {
	// List menus=sysMenuPermissionService.listAll();
	// List<EasyUITreeEntity>
	// list=EasyUIUtil.getEasyUITreeFromMenuAndOperation(menus);
	// jsonRoot=list;
	// 
	// }

	/**
	 * 针对角色加载权限，获得菜单权限和操作权限 <br/>
	 * 为了防止角色权限和操作权限id相同导致的菜单id重复，将id前加上标识前缀：menu_ID, operation_ID
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("listAllForSysRole")
	public Object listAllForSysRole() throws Exception {
		List menus = sysMenuPermissionService.listAll();
		List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeUsePrefixIdFromMenuAndOperation(menus);
		return list;
		
	}

	/**
	 * 获得菜单权限，允许多个根节点，根节点ID为-1
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("list2")
	public Object list2() throws Exception {
		List menus = sysMenuPermissionService.list();
		List<EasyUITreeEntity> list = EasyUIUtil.getEasyUITreeFromRootMenu(menus);
		list.add(0, new EasyUITreeEntity("-1", "根节点"));
		return list;
		
	}

	/**
	 * 使用SysMenuPermission直接生成treegrid，不用转换为EasyUITreeEntity 仅测试，未使用
	 * 
	 * @return
	 * @throws Exception
	 */
	private Map<Object, Object> list_none() throws Exception {
		List menus = sysMenuPermissionService.list();
		// treeGrid格式1：children层级
		// EasyObjectSetNull.setNull(menus,
		// "sysOperationPermissions","{sysMenuPermissions}.sysOperationPermissions");
		// jsonRoot=new Object[]{EasyObjectExtract.extract(menus.get(0),
		// "menuPermissionId","action","icon","name","remark","sortNum","sysMenuPermission","sysMenuPermissions#children")};

		// treeGrid格式2：_parentId
		return setJsonMap("rows", EasyObjectExtract.extract(menus, "menuPermissionId", "action", "icon", "name", "remark",
				"sortNum", "sysMenuPermission.menuPermissionId#_parentId"));

	}

}
