package cn.easyproject.easyee.ssh.sys.entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SysOperationPermission entity. 
 * @author easyproject.cn
 * @version 1.0
 */
@Entity
@Table(name="sys_operation")
@SuppressWarnings("rawtypes")
public class SysOperationPermission implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="OPERATION_PERMISSION_ID")
	private Integer operationPermissionId;
	
	@ManyToOne
	@JoinColumn(name="MENU_PERMISSION_ID")
	private SysMenuPermission sysMenuPermission;
	private String name;
	private String action;
	private String remark;
	@ManyToMany
	@JoinTable(
		name="sys_role_operation"
		, joinColumns={
			@JoinColumn(name="OPERATION_PERMISSION_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ROLE_ID")
			}
		)
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);

	// Constructors

	/** default constructor */
	public SysOperationPermission() {
	}

	/** minimal constructor */
	public SysOperationPermission(String name) {
		this.name = name;
	}

	/** full constructor */
	public SysOperationPermission(SysMenuPermission sysMenuPermission, String name,
			String action, String remark) {
		this.sysMenuPermission = sysMenuPermission;
		this.name = name;
		this.action = action;
		this.remark = remark;
	}

	// Property accessors

	public Integer getOperationPermissionId() {
		return this.operationPermissionId;
	}

	public void setOperationPermissionId(Integer operationPermissionId) {
		this.operationPermissionId = operationPermissionId;
	}

	public SysMenuPermission getSysMenuPermission() {
		return this.sysMenuPermission;
	}

	public void setSysMenuPermission(SysMenuPermission sysMenuPermission) {
		this.sysMenuPermission = sysMenuPermission;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysOperationPermission [operationPermissionId=" + operationPermissionId
				+ ", name=" + name + ", action=" + action + ", remark="
				+ remark + "]";
	}

	public Set getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}


}