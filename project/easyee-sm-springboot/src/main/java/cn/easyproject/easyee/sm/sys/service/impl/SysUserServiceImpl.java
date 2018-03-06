package cn.easyproject.easyee.sm.sys.service.impl;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.easyproject.easyee.sm.base.pagination.PageBean;
import cn.easyproject.easyee.sm.base.service.BaseService;
import cn.easyproject.easyee.sm.sys.criteria.SysUserCriteria;
import cn.easyproject.easyee.sm.sys.dao.SysUserDAO;
import cn.easyproject.easyee.sm.sys.entity.SysUser;
import cn.easyproject.easyee.sm.sys.service.SysUserService;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseService implements SysUserService {

	@Resource
	SysUserDAO sysUserDAO;
	
	@Override
	public void add(SysUser sysuser) {
			sysUserDAO.add(sysuser);
			// 添加角色
			if(sysuser.getSysRoles().size()>0){
				sysUserDAO.addUserRoles(sysuser);
			}
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			sysUserDAO.deleteUserRoles(Integer.valueOf(id));
		}
		sysUserDAO.deleteByIds(ids);
	}

	@Override
	public void update(SysUser sysUser) {
		// 删除用户旧角色
		sysUserDAO.deleteUserRoles(sysUser.getUserId());
		// 更新用户
		sysUserDAO.update(sysUser); 
		// 添加新角色
		if(sysUser.getSysRoles().size()>0){
			sysUserDAO.addUserRoles(sysUser);
		}
		
	}

	@Override
	public SysUser get(int id) {
		return this.get(id);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void findByPage(PageBean pb,SysUserCriteria sysUserCriteria) {
		pb.setEasyCriteria(sysUserCriteria);
		String condition="";
		if(pb.getEasyCriteria()!=null){
			condition=pb.getEasyCriteria().getCondition();
			pb.setSqlParameterValues(pb.getEasyCriteria().getValues());
		}
		
		String sort="USER_ID";
		String sortOrder="ASC";
		
		if(isNotNullAndEmpty(pb.getSort())){
			sort=pb.getSort();
		}
		if(isNotNullAndEmpty(pb.getSortOrder())){
			sortOrder=pb.getSortOrder();
		}
		
		if(pb.getDialect()==PageBean.MYSQL_DIALECT){
			pb.setSql("SELECT " + 
					"	A.* " + 
					"FROM " + 
					"	( " + 
					"		SELECT " + 
					"			s.*, r.role_id AS ROLE_ID, " + 
					"			r. NAME AS ROLE_NAME " + 
					"		FROM " + 
					"			Sys_User s, " + 
					"			SYS_USER_ROLE ur, " + 
					"			sys_role r " + 
					"		WHERE " + 
					"			1 = 1 " + 
					"		AND s.user_id = ur.user_id " + 
					"		AND r.role_id = ur.role_id " + 
					"		UNION " + 
					"			SELECT " + 
					"				s.*,- 1,'' " + 
					"			FROM " + 
					"				Sys_User s " + 
					"			WHERE " + 
					"				NOT EXISTS (" + 
					"					SELECT " + 
					"						1 " + 
					"					FROM " + 
					"						sys_user_role ur " + 
					"					WHERE " + 
					"						ur.USER_ID = s.USER_ID " + 
					"				)" + 
					"	) A " + 
					" WHERE " + 
					"	A.user_id IN ( " + 
					"		SELECT " + 
					"			T.user_id " + 
					"		FROM " + 
					"			(" + 
					"				SELECT " + 
					"					user_id " + 
					"				FROM" + 
					"					Sys_User " + 
					"				WHERE " + 
					"					1 = 1 " +
					condition +
					"				ORDER BY " + 
					sort+" "+sortOrder+
					"					" + 
					"				LIMIT " 
					+((pb.getPageNo()-1)*pb.getRowsPerPage())+","+pb.getRowsPerPage()+
					"			) T" + 
					"	)");
		}else if(pb.getDialect()==PageBean.ORACLE_DIALECT || pb.getDialect()==PageBean.ORACLE_12C_DIALECT){
			int start=((pb.getPageNo()-1)*pb.getRowsPerPage());
			int end=start+pb.getRowsPerPage();
			String idsql="select * from ( "
					+ "select B.*,rownum r from ("
					+ "select user_id from sys_user where 1=1 "
					+condition + " ORDER BY " + sort+ " "+sortOrder
					+ ") B where rownum<="+end
					+ ") where r>"+start;
			
			pb.setSql("SELECT " + 
					"	A.* " + 
					"FROM " + 
					"	( " + 
					"		SELECT " + 
					"			s.*, r.role_id AS ROLE_ID, " + 
					"			r. NAME AS ROLE_NAME " + 
					"		FROM " + 
					"			Sys_User s, " + 
					"			SYS_USER_ROLE ur, " + 
					"			sys_role r " + 
					"		WHERE " + 
					"			1 = 1 " + 
					"		AND s.user_id = ur.user_id " + 
					"		AND r.role_id = ur.role_id " + 
					"		UNION " + 
					"			SELECT " + 
					"				s.*,- 1,'' " + 
					"			FROM " + 
					"				Sys_User s " + 
					"			WHERE " + 
					"				NOT EXISTS ( " + 
					"					SELECT " + 
					"						1 " + 
					"					FROM " + 
					"						sys_user_role ur " + 
					"					WHERE " + 
					"						ur.USER_ID = s.USER_ID " + 
					"				) " + 
					"	) A " + 
					" WHERE " + 
					"	A.user_id IN ( " + 
					"		SELECT " + 
					"			T.user_id " + 
					"		FROM " + 
					"			( " + 
							idsql+
					"			) T " + 
					"	)");
			
		}
		
		pb.setCountSQL("SELECT COUNT(*) FROM sys_user A "
				+ " WHERE 1=1 "
				+  condition
		);
		
		
		
				
		// 按条件分页查询
		sysUserDAO.pagination(pb);
	}

	@Override
	public SysUser login(SysUser sysUser) {
		if (sysUser == null) {
			return null;
		}
		// 用户状态：0启用；1禁用；2删除
		SysUser user = sysUserDAO.login(sysUser);
		return user;
	}

	@Override
	public void changePwd(int id, String pwd) {
			sysUserDAO.changePwd(id, pwd);
	}

	@Override
	public boolean existsName(String name) {
			return sysUserDAO.existsName(name)>0;
	}
	@Override
	public boolean existsName(String name, Integer userId) {
		//修改用户时，检测用户名是否存在
			return sysUserDAO.existsNameWhenEdit(name, userId)>0;
	}

	@Override
	public int findMaxPage(int rowsPerPage) {
		return (sysUserDAO.findMaxRow()-1)/rowsPerPage+1;
	}

	@Override
	public String getPwd(int id) {
		return sysUserDAO.getPwd(id);
	}

	@Override
	public void delete(Integer id) {
		// 删除用户旧角色
		sysUserDAO.deleteUserRoles(id);
		sysUserDAO.delete(id);
	}

}
