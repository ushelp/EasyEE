package cn.easyproject.easyee.ssh.sys.shiro;

import cn.easyproject.easyee.ssh.base.util.MD5;
import cn.easyproject.easyshiro.EasyUsernamePasswordEndcodeToken;
/**
 * 自定义用户名密码加密 Token
 * - 继承EasyUsernamePasswordEndcodeToken，实现密码加密
 * 
 * @author Ray
 * @author inthinkcolor@gmail.com
 * @author easyproject.cn
 * @since
 */
public class UsernamePasswordEncodeToken extends EasyUsernamePasswordEndcodeToken{
	
	   /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	 /*--------------------------------------------
   | UsernamePasswordToken 令牌内置属性  |
   ============================================*/
	
//   private String username;
//   private char[] password;
//   private boolean rememberMe = false;
//   private String host;
	
	/*--------------------------------------------
   | 数据库用户列信息，相关属性 由EasyJdbcRealm在登录认证时，从数据库查询并初始化  |
   ============================================*/
	// TODO 数据库列信息
	private Integer userId;
	private String name;
	private String realName;
	/**
	 * 用户状态：0启用；1禁用；2删除
	 */
	private Integer status;

	
	@Override
	public String encodePassword(){
		String pwd=MD5.getMd5(super.getPassword(),getName().toLowerCase());
		return pwd;
	}
	

	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getRealName() {
		return realName;
	}



	public void setRealName(String realName) {
		this.realName = realName;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



}
