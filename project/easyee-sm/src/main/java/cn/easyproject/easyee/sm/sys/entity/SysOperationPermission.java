package cn.easyproject.easyee.sm.sys.entity;

import java.util.HashSet;
import java.util.Set;

/**
 * SysOperationPermission entity. 
 * @author easyproject.cn
 * @version 1.0
 */
public class SysOperationPermission implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer operationPermissionId;
	
	private SysMenuPermission sysMenuPermission;
	private String name;
	private String action;
	private String remark;
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

	@SuppressWarnings("rawtypes")
	public Set getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}


}