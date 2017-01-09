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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * SysMenuPermission entity.
 * 
 * @author easyproject.cn
 * @version 1.0
 */
@Entity
@Table(name = "sys_menu")
@SuppressWarnings("rawtypes")
public class SysMenuPermission implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_PERMISSION_ID")
	private Integer menuPermissionId;
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private SysMenuPermission sysMenuPermission;
	private String name;
	private String action;
	@Column(name = "SORT_NUM")
	private Integer sortNum;
	private String icon;
	private String remark;

	@ManyToMany
	@JoinTable(name = "sys_role_menu", joinColumns = {
			@JoinColumn(name = "MENU_PERMISSION_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	private Set<SysRole> sysRoles = new HashSet<SysRole>(0);

	@OneToMany(mappedBy = "sysMenuPermission")
	@OrderBy("OPERATION_PERMISSION_ID asc")
	private Set<SysOperationPermission> sysOperationPermissions = new HashSet<SysOperationPermission>(0);

	@OneToMany(mappedBy = "sysMenuPermission")
	@OrderBy("sort_num asc")
	private Set<SysMenuPermission> sysMenuPermissions = new HashSet<SysMenuPermission>(0);

	// Constructors

	/** default constructor */
	public SysMenuPermission() {
	}

	/** minimal constructor */
	public SysMenuPermission(String name) {
		this.name = name;
	}

	/** full constructor */

	// Property accessors

	public Integer getMenuPermissionId() {
		return this.menuPermissionId;
	}

	public SysMenuPermission(Integer menuPermissionId, SysMenuPermission sysMenuPermission, String name, String action,
			Integer sortNum, String icon, String remark, Set<SysRole> sysRoles,
			Set<SysOperationPermission> sysOperationPermissions, Set<SysMenuPermission> sysMenuPermissions) {
		super();
		this.menuPermissionId = menuPermissionId;
		this.sysMenuPermission = sysMenuPermission;
		this.name = name;
		this.action = action;
		this.sortNum = sortNum;
		this.icon = icon;
		this.remark = remark;
		this.sysRoles = sysRoles;
		this.sysOperationPermissions = sysOperationPermissions;
		this.sysMenuPermissions = sysMenuPermissions;
	}

	public void setMenuPermissionId(Integer menuPermissionId) {
		this.menuPermissionId = menuPermissionId;
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

	public Integer getSortNum() {
		return this.sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}

	public Set<SysOperationPermission> getSysOperationPermissions() {
		return this.sysOperationPermissions;
	}

	public void setSysOperationPermissions(Set<SysOperationPermission> sysOperationPermissions) {
		this.sysOperationPermissions = sysOperationPermissions;
	}

	public Set<SysMenuPermission> getSysMenuPermissions() {
		return this.sysMenuPermissions;
	}

	public void setSysMenuPermissions(Set<SysMenuPermission> sysMenuPermissions) {
		this.sysMenuPermissions = sysMenuPermissions;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "SysMenuPermission [menuPermissionId=" + menuPermissionId + ", name=" + name + ", action=" + action
				+ ", sortNum=" + sortNum + ", icon=" + icon + ", remark=" + remark + "]\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuPermissionId == null) ? 0 : menuPermissionId.hashCode());
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
		SysMenuPermission other = (SysMenuPermission) obj;
		if (menuPermissionId == null) {
			if (other.menuPermissionId != null)
				return false;
		} else if (!menuPermissionId.equals(other.menuPermissionId))
			return false;
		return true;
	}

}