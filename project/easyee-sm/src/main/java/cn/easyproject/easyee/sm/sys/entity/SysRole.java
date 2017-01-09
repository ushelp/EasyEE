package cn.easyproject.easyee.sm.sys.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * SysRole entity. 
 * @author easyproject.cn
 * @version 1.0
 */
public class SysRole implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer roleId;
	private String name;
	private Integer status;
	private String remark;
	@JsonIgnore
	private Set<SysOperationPermission> sysOperationPermissions = new HashSet<SysOperationPermission>(0);
	@JsonIgnore
	private Set<SysMenuPermission> sysMenuPermissions = new HashSet<SysMenuPermission>(0);
	
	@JsonIgnore
	private Set<SysUser> sysUsers = new HashSet<SysUser>(0);

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	public SysRole(Integer roleId) {
		super();
		this.roleId = roleId;
	}

	/** minimal constructor */
	public SysRole(String name) {
		this.name = name;
	}

	/** full constructor */
	public SysRole(String name, Integer status, String remark,
			Set<SysOperationPermission> sysOperationPermissions, Set<SysMenuPermission> sysMenuPermissions, Set<SysUser> sysUsers) {
		this.name = name;
		this.status = status;
		this.remark = remark;
		this.sysOperationPermissions = sysOperationPermissions;
		this.sysMenuPermissions = sysMenuPermissions;
		this.sysUsers = sysUsers;
	}

	// Property accessors

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@JsonIgnoreProperties
	@SuppressWarnings("rawtypes")
	public Set getSysOperationPermissions() {
		return this.sysOperationPermissions;
	}
	
	public void setSysOperationPermissions(Set<SysOperationPermission> sysOperationPermissions) {
		this.sysOperationPermissions = sysOperationPermissions;
	}
	@SuppressWarnings("rawtypes")
	public Set getSysMenuPermissions() {
		return this.sysMenuPermissions;
	}

	public void setSysMenuPermissions(Set<SysMenuPermission> sysMenuPermissions) {
		this.sysMenuPermissions = sysMenuPermissions;
	}
	@SuppressWarnings("rawtypes")
	public Set getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	@Override
	public String toString() {
		return "SysRole [roleId=" + roleId + ", name=" + name + ", status="
				+ status + ", remark=" + remark + "]";
	}

}