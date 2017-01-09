package cn.easyproject.easyee.sh.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sh.base.service.BaseService;
import cn.easyproject.easyee.sh.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.sh.sys.service.SysOperationPermissionService;

@Service("sysOperationPermissionService")
@SuppressWarnings({"rawtypes","unchecked"})
public class SysOperationPermissionServiceImpl extends BaseService implements SysOperationPermissionService {

	@Override
	public void add(SysOperationPermission sysOperationPermission) {
		commonDAO.persist(sysOperationPermission);
	}

	@Override
	public void delete(String[] ids) {
		commonDAO.batchUpdateSQL("delete from sys_operation where OPERATION_PERMISSION_ID=?", ids);
	}

	@Override
	public void delete(SysOperationPermission sysOperationPermission) {
		commonDAO.updateByJpql("delete from SysOperationPermission where operationPermissionId=?",sysOperationPermission.getOperationPermissionId());
	}

	@Override
	public void update(SysOperationPermission sysOperationPermission) {
//		commonDAO.merge(sysOperationPermission);
		
		String hql="update SysOperationPermission set name=?,action=?,remark=? where operationPermissionId=?";
		commonDAO.updateByJpql(hql, sysOperationPermission.getName(),sysOperationPermission.getAction(),sysOperationPermission.getRemark(),sysOperationPermission.getOperationPermissionId());
	}

	@Override
	public SysOperationPermission get(int id) {
		return commonDAO.find(SysOperationPermission.class, id);
	}

	
	@Override
	public List<Map> list(Integer menuId) {
		return commonDAO.findByCache("select new map(s.operationPermissionId as operationPermissionId, s.name as name,s.action as action,s.remark as remark,s.sysMenuPermission.menuPermissionId as menuId) from SysOperationPermission s where s.sysMenuPermission.menuPermissionId=?", "SysOperationPermission.list", menuId);
	}
	@Override
	public List<String> getIdsByRoleId(Integer roleId) {
		return commonDAO.findBySQL("select Operation_Permission_Id from sys_role_operation where ROLE_ID=?",roleId);
	}

	@Override
	public void deleteByMenuPermissionId(int menuPermissionId) {
		commonDAO.updateByJpql("delete from SysOperationPermission where sysMenuPermission.menuPermissionId=?", menuPermissionId);
	}

	@Override
	public Map<String, String> getAllOpreationNames() {
		Map<String,String> operationsName=new HashMap<String, String>();
		String hql="select new Map(action as action,name as name,remark as remark) from SysMenuPermission";
		List<Map<String,String>> list=commonDAO.find(hql);
		hql="select new Map(action as action,name as name,remark as remark) from SysOperationPermission";
		List<Map<String,String>> list2=commonDAO.find(hql);
		
		list.addAll(list2); //拼接集合
		
		String operationName=null; //权限动作对应的权限提示名称
		for(Map<String,String> map:list){
			if (isNotNullAndEmpty(map.get("action"))) {
				for (String o : map.get("action").split("#|,")) {
					o=o.trim();
					if(o.length()>0){
						String name=map.get("name");
						String remark=map.get("remark");
						operationName=name;
						//如果存在备注
						if(isNotNullAndEmpty(remark)){
							operationName+="（"+remark+"）";
						}
						operationsName.put(o,operationName);
					}
				}
			}
		}
	
		return operationsName;
	}
}
