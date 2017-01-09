package cn.easyproject.easyee.sm.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.dao.SysMenuPermissionDAO;
import cn.easyproject.easyee.sm.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.sm.sys.service.SysMenuPermissionService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Service("sysMenuPermissionService")
public class SysMenuPermissionServiceImpl extends BaseService implements
		SysMenuPermissionService {
	
	@Resource
	SysMenuPermissionDAO sysMenuPermissionDAO;

	@Override
	public void add(SysMenuPermission sysMenuPermission) {
		sysMenuPermissionDAO.add(sysMenuPermission);
	}


	@Override
	public void update(SysMenuPermission sysMenuPermission) {
		sysMenuPermissionDAO.update(sysMenuPermission);
	}
	
	@Override
	public void delete(SysMenuPermission sysMenuPermission) {
		// 删除一对多，多方的操作权限
		sysMenuPermissionDAO.deleteMenuRoles(sysMenuPermission);
		sysMenuPermissionDAO.deleteMenuOperationRoles(sysMenuPermission);
		sysMenuPermissionDAO.deleteMenuOperation(sysMenuPermission);
		sysMenuPermissionDAO.delete(sysMenuPermission);
	}

	@Override
	public SysMenuPermission get(int id) {
		return sysMenuPermissionDAO.get(id);
	}

	@Override
	public List<SysMenuPermission> list() {
		return sysMenuPermissionDAO.list();
	}

	
	
	
	@Override
	public int getMaxSortNum(Integer parentId) {
		return sysMenuPermissionDAO.getMaxSortNum(parentId);
	}

	@Override
	public int getSortNum(Integer menuPermissionId) {
		return  sysMenuPermissionDAO.getSortNum(menuPermissionId);
	}

	@SuppressWarnings("unused")
	@Override
	public void move(Integer menuPermissionId, boolean up) {

		SysMenuPermission sysMenuPermission = sysMenuPermissionDAO.get(menuPermissionId);
		//查找上移或下移需要交换的相邻对象
		SysMenuPermission neighbour=null;
		Integer parentId=null;
		//如果没有父菜单
		if(sysMenuPermission.getSysMenuPermission()!=null){
			parentId=sysMenuPermission.getSysMenuPermission().getMenuPermissionId();
		}
		
		if (up) {
			// 上移
			neighbour=sysMenuPermissionDAO.getUpNeighbor(sysMenuPermission);
		}else{
			// 下移
			neighbour=sysMenuPermissionDAO.getDownNeighbor(sysMenuPermission);
		}
		
	
		// 存在相邻节点，则更新
		if(sysMenuPermission!=null&&neighbour!=null){
			//如果需要交换，则交换次序
			if(neighbour.getMenuPermissionId()!=sysMenuPermission.getMenuPermissionId()){
				int temp=neighbour.getSortNum();
				neighbour.setSortNum(sysMenuPermission.getSortNum());
				sysMenuPermission.setSortNum(temp);
			}
			
			sysMenuPermissionDAO.move(sysMenuPermission);
			sysMenuPermissionDAO.move(neighbour);
		}
		
	}

	@Override
	public boolean hashChildMenu(Integer menuPermissionId) {
		return sysMenuPermissionDAO.hashChildMenu(menuPermissionId)>0?true:false;
	}

	

	@Override
	public List<String> getIdsByRoleId(Integer roleId) {
		return sysMenuPermissionDAO.getIdsByRoleId(roleId);
	}


	@Override
	public List<SysMenuPermission> listByUserId(int userId) {
		//SELECT t FROM Teacher t join t.students s join s.books b where b.name = 'a' 
		// 查询用户启用的角色及其菜单权限
		return sysMenuPermissionDAO.listByUserId(userId);
	}


	@Override
	public List<Map<String, String>> listAll() {
		return sysMenuPermissionDAO.listAll();
	}


	
	

}
