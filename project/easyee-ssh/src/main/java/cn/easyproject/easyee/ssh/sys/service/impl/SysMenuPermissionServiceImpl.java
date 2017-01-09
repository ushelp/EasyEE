package cn.easyproject.easyee.ssh.sys.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.ssh.sys.entity.SysMenuPermission;
import cn.easyproject.easyee.ssh.sys.service.SysMenuPermissionService;
import cn.easyproject.easyee.ssh.base.service.BaseService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Service("sysMenuPermissionService")
@SuppressWarnings({"unchecked"})
public class SysMenuPermissionServiceImpl extends BaseService implements
		SysMenuPermissionService {

	@Override
	public void add(SysMenuPermission sysMenuPermission) {
		commonDAO.persist(sysMenuPermission);
	}


	@Override
	public void update(SysMenuPermission sysMenuPermission) {
		commonDAO.merge(sysMenuPermission);
	}

	@Override
	public SysMenuPermission get(int id) {
		return commonDAO.find(SysMenuPermission.class, id);
	}

	@Override
	public List<SysMenuPermission> list() {
		List<SysMenuPermission> list = commonDAO.findByCache("from SysMenuPermission",
				"SysMenuPermission.list");
		for (SysMenuPermission sysMenuPermission : list) {
			commonDAO.initializeDeep(sysMenuPermission.getSysMenuPermissions());
		}
		return list;
	}

	
	@Override
	public List<SysMenuPermission> listAll() {
		List<SysMenuPermission> list = commonDAO.findByCache("from SysMenuPermission",
				"SysMenuPermission.list");
		for (SysMenuPermission sysMenuPermission : list) {
			commonDAO.initializeDeep(sysMenuPermission.getSysMenuPermissions());
			commonDAO.initializeDeep(sysMenuPermission.getSysOperationPermissions());
		}
		return list;
	}
	
	@Override
	public int getMaxSortNum(Integer parentId) {

		Object res = null;
		if (parentId != -1) { // 非根节点
			res = commonDAO
					.findVal(
							"select max(sortNum) from SysMenuPermission where sysMenuPermission.menuPermissionId=?",
							parentId);
		} else {
			// 根节点
			res = commonDAO
					.findVal("select max(s.sortNum) from SysMenuPermission s where s.sysMenuPermission is null");
		}

		return res != null ? Integer.valueOf(res.toString()) : 0;
	}

	@Override
	public int getSortNum(Integer menuPermissionId) {
		Object res = commonDAO.findVal(
				"select sortNum from SysMenuPermission where menuPermissionId=?",
				menuPermissionId);
		return res != null ? Integer.valueOf(res.toString()) : 0;
	}

	@Override
	public void move(Integer menuPermissionId, boolean up) {

		SysMenuPermission sysMenuPermission = commonDAO.find(SysMenuPermission.class,
				menuPermissionId);
		//查找上移或下移需要交换的相邻对象
		// 下移
		String hql = "from SysMenuPermission where sysMenuPermission.menuPermissionId=? and sortNum=(select min(sortNum) from SysMenuPermission where sysMenuPermission.menuPermissionId=? and sortNum>?)";
		if (up) {
			// 上移
			 hql = "from SysMenuPermission where sysMenuPermission.menuPermissionId=? and sortNum=(select max(sortNum) from SysMenuPermission where sysMenuPermission.menuPermissionId=? and sortNum<?)";
		}
		
		Integer parentId=null;
		//如果没有父菜单
		if(sysMenuPermission.getSysMenuPermission()!=null){
			parentId=sysMenuPermission.getSysMenuPermission().getMenuPermissionId();
		}
		SysMenuPermission neighbour = commonDAO.findVal(
				hql,
				parentId, parentId, sysMenuPermission.getSortNum());
		// 存在相邻节点，则更新
		if(sysMenuPermission!=null&&neighbour!=null){
			//如果需要交换，则交换次序
			if(neighbour.getMenuPermissionId()!=sysMenuPermission.getMenuPermissionId()){
				int temp=neighbour.getSortNum();
				neighbour.setSortNum(sysMenuPermission.getSortNum());
				sysMenuPermission.setSortNum(temp);
			}
			
			commonDAO.merge(sysMenuPermission);
			commonDAO.merge(neighbour);
		}
		
	}

	@Override
	public boolean hashChildMenu(Integer menuPermissionId) {
		return commonDAO.findCount("select count(*) from SysMenuPermission where sysMenuPermission.menuPermissionId=?", menuPermissionId)>0?true:false;
	}

	@Override
	public void delete(SysMenuPermission sysMenuPermission) {
		// 删除一对多，多方的操作权限
		commonDAO.updateByJpql("delete from SysOperationPermission where sysMenuPermission.menuPermissionId=?", sysMenuPermission.getMenuPermissionId());
		commonDAO.updateByJpql("delete from SysMenuPermission where menuPermissionId=?",
				sysMenuPermission.getMenuPermissionId());
	}

	@Override
	public List<String> getIdsByRoleId(Integer roleId) {
		return commonDAO.findBySQL("select menu_Permission_Id from sys_role_menu where ROLE_ID=?",roleId);
	}


	@Override
	public List<SysMenuPermission> listByUserId(int userId) {
		//SELECT t FROM Teacher t join t.students s join s.books b where b.name = 'a' 
		// 查询用户启用的角色及其菜单权限
		List<SysMenuPermission> list = commonDAO.findByCache("select r.sysMenuPermissions from SysRole r join r.sysUsers u where u.userId=? and r.status=0",
				"SysMenuPermission.list",userId);
		for (SysMenuPermission sysMenuPermission : list) {
			commonDAO.initializeDeep(sysMenuPermission.getSysMenuPermissions());
		}
		
		return list;
	}
	

}
