package cn.easyproject.easyee.sm.sys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.dao.SysOperationPermissionDAO;
import cn.easyproject.easyee.sm.sys.entity.SysOperationPermission;
import cn.easyproject.easyee.sm.sys.service.SysOperationPermissionService;

@Service("sysOperationPermissionService")
@SuppressWarnings({"rawtypes"})
public class SysOperationPermissionServiceImpl extends BaseService implements SysOperationPermissionService {

	@Resource
	SysOperationPermissionDAO sysOperationPermissionDAO;
	
	@Override
	public void add(SysOperationPermission sysOperationPermission) {
		sysOperationPermissionDAO.add(sysOperationPermission);
	}

	@Override
	public void delete(String[] ids) {
		sysOperationPermissionDAO.deleteOperationRolesByIds(ids);
		sysOperationPermissionDAO.deleteByIds(ids);
	}

	@Override
	public void delete(SysOperationPermission sysOperationPermission) {
		sysOperationPermissionDAO.deleteOperationRoles(sysOperationPermission);
		sysOperationPermissionDAO.delete(sysOperationPermission);
	}

	@Override
	public void update(SysOperationPermission sysOperationPermission) {
		sysOperationPermissionDAO.update(sysOperationPermission);
	}

	@Override
	public SysOperationPermission get(int id) {
		return sysOperationPermissionDAO.get(id);
	}

	
	@Override
	public List<Map> list(Integer menuId) {
		return sysOperationPermissionDAO.list(menuId);
	}
	@Override
	public List<String> getIdsByRoleId(Integer roleId) {
		return sysOperationPermissionDAO.getIdsByRoleId(roleId);
	}

	@Override
	public void deleteByMenuPermissionId(int menuPermissionId) {
		sysOperationPermissionDAO.deleteByMenuPermissionId(menuPermissionId);
	}

	@Override
	public Map<String, String> getAllOpreationNames() {
		Map<String,String> operationsName=new HashMap<String, String>();
		List<Map<String,String>> list=sysOperationPermissionDAO.getAllOpreationNames();
		
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
