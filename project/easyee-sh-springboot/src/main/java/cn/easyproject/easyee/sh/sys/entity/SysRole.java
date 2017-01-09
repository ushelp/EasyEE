package cn.easyproject.easyee.sh.sys.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * SysRole entity.
 * 
 * @author easyproject.cn
 * @version 1.0
 */
@Entity
@Table(name = "sys_role")
@SuppressWarnings("rawtypes")
public class SysRole implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	private Integer roleId;
	private String name;
	private Integer status;
	private String remark;
	@ManyToMany
	@JoinTable(name = "sys_role_operation", joinColumns = {
			@JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "OPERATION_PERMISSION_ID") })
	@JsonIgnore
	private Set<SysOperationPermission> sysOperationPermissions = new HashSet<SysOperationPermission>(0);
	@ManyToMany
	@JoinTable(name = "sys_role_menu", joinColumns = {
			@JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "MENU_PERMISSION_ID") })
	@OrderBy("SORT_NUM")
	@JsonIgnore
	private Set<SysMenuPermission> sysMenuPermissions = new HashSet<SysMenuPermission>(0);

	@ManyToMany
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "USER_ID") })
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
	public SysRole(String name, Integer status, String remark, Set<SysOperationPermission> sysOperationPermissions,
			Set<SysMenuPermission> sysMenuPermissions, Set<SysUser> sysUsers) {
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
	public Set getSysOperationPermissions() {
		return this.sysOperationPermissions;
	}

	public void setSysOperationPermissions(Set<SysOperationPermission> sysOperationPermissions) {
		this.sysOperationPermissions = sysOperationPermissions;
	}

	public Set getSysMenuPermissions() {
		return this.sysMenuPermissions;
	}

	public void setSysMenuPermissions(Set<SysMenuPermission> sysMenuPermissions) {
		this.sysMenuPermissions = sysMenuPermissions;
	}

	public Set getSysUsers() {
		return this.sysUsers;
	}

	public void setSysUsers(Set<SysUser> sysUsers) {
		this.sysUsers = sysUsers;
	}

	@Override
	public String toString() {
		return "SysRole [roleId=" + roleId + ", name=" + name + ", status=" + status + ", remark=" + remark + "]";
	}

}