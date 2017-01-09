package cn.easyproject.easyee.ssh.sys.shiro;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import cn.easyproject.easyshiro.EasyJdbcRealmInterceptor;

public class RealmInterceptor implements EasyJdbcRealmInterceptor {

	@Override
	public void afterDoGetAuthenticationInfo(SimpleAuthenticationInfo info) {
		
	}

	@Override
	public void afterDoGetAuthorizationInfo(SimpleAuthorizationInfo info) {
//		Set<String> roleNames = new LinkedHashSet<String>();
		Set<String> permissions = new LinkedHashSet<String>();
		
		Set<String> sets=info.getStringPermissions();
		
		for (String permissionString : sets) {
			if(permissionString!=null&&(!permissionString.trim().equals(""))){
				for (String o : permissionString.split("#|,")) {
					if (o.trim().length() > 0) {
						permissions.add(o.trim());
					}
				}
			}
		}
		info.setStringPermissions(permissions); 
		
		// System.out.println("permission:  "+permissions);
		
//		Set<String> sets2=info.getRoles();
		
//		for (String roleNameString : roleNames) {
//			if(roleNameString!=null&&(!roleNameString.trim().equals(""))){
//				for (String o : permissionString.split("#|,")) {
//					if (o.trim().length() > 0) {
//						permissions.add(o.trim());
//					}
//				}
//			}
//		}
	}

}
