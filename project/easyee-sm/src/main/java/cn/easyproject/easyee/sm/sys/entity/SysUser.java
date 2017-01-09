package cn.easyproject.easyee.sm.sys.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * SysUser entity.
 * @author easyproject.cn
 * @version 1.0
 */
@JsonIgnoreProperties({"password"})
public class SysUser implements java.io.Serializable {

	
	public static final int STATUS_LOCK=1;
	
	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userId;

//	@JsonIgnore
	private String password;
	private String name;
	private String realName;
	/**
	 * 用户状态：0启用；1禁用；2删除
	 */
	private Integer status;
	
	
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	


	// Constructors

	/** default constructor */
	public SysUser() {
	}

	/** minimal constructor */
	public SysUser(String name, String password) {
		this.name = name;
		this.password = password;
	}

	/** full constructor */

	
	

	public SysUser(String name, String password, Integer status, Set<SysRole> sysRoles) {
		super();
		this.name = name;
		this.password = password;
		this.status = status;
		this.sysRoles = sysRoles;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}


	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public SysUser(String password, String name, String realName,
			Integer status, Set<SysRole> sysRoles) {
		super();
		this.password = password;
		this.name = name;
		this.realName = realName;
		this.status = status;
		this.sysRoles = sysRoles;
	}

	public SysUser(Integer userId, String password, String name,
			String realName, Integer status, Set<SysRole> sysRoles) {
		super();
		this.userId = userId;
		this.password = password;
		this.name = name;
		this.realName = realName;
		this.status = status;
		this.sysRoles = sysRoles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((realName == null) ? 0 : realName.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysUser other = (SysUser) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (realName == null) {
			if (other.realName != null)
				return false;
		} else if (!realName.equals(other.realName))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SysUser [userId=" + userId + ", password=" + password
				+ ", name=" + name + ", realName=" + realName + ", status="
				+ status + "]";
	}

	

}