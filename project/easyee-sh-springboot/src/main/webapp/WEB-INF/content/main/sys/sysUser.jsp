<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="s"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>
<script type="text/javascript">
	//用户操作命名空间
	var sysUser = {};
	$(function() {
		/*
		 * datagrid数据格式化
		 */
		sysUser.formatStatus = function(val, row) {
			if (val != 0) {
				return '<span style="color:red;">禁用</span>';
			} else {
				return "正常";
			}
		}
		sysUser.formatRoles = function(val, row) {
			var roles = "";
			if (val) {
				for (var i = 0; i < val.length; i++) {
					if(val[i]!=null){ roles += val[i] + ", "; }
				}
				roles = roles.substring(0, roles.length - 2);
			}

			return roles;
		}
		/*
		 * 搜索
		 */
		/*
		 * sysUser.doSearch = function() { $("#sysUserDataGrid").edatagrid("load", {
		 * name : $("#name3").val(), sex : $("#sex3").combobox("getValue") }); }
		 */
		/*
		 * 数据表格初始化
		 */
		var dg = $("#sysUserDataGrid");
		dg.initDatagrid({
			iconCls : 'icon-group',
			/*
			 * 行编辑:saveUrl、updateUrl、destroyUrl配合uiEx使用
			 */
			url : "SysUser/list",
			saveUrl : "SysUser/save",
			updateUrl : "SysUser/update",
			destroyUrl : "SysUser/delete",
			showHeaderContextMenu:true, // 表头添加右键菜单，可选择显示的列
			// clickRowEdit:true, //注册单击行编辑，可以代替edatagrid实现带行编辑的CRUD
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			checkbox : true,
			singleSelect : false,
			checkOnSelect : true,
			//双击操作
			onDblClickRow : function(rowIndex, rowData) {
				toEdit(rowData);
			},
			menuSelector:"#sysUserContextMenu",
			showContextMenu:true,
			mutipleDelete: true, // 多行提交删除
			mutipleDeleteProperty:"userId" // 多行删除时提及给服务器的属性和值，不会添加sendRowDataPrefix前缀，支持使用数组指定多个属性名
		});

		/*
		 * 增删改
		 */

		//增加
		sysUser.toAdd = function() {
			uiEx.resetForm("#sysUserAddForm");
			uiEx.openDialog("#sysUserAddDialog", "用户添加");
			/*
			 * 初始化角色复选框
			 */
			$.getJSON(
							"SysRole/all",
							null,
							function(data) {
								var roleChecks = "";
								if(data.list){
									$.each(data.list,
										function(i, role) {
												roleChecks += '<input type="checkbox" name="roleIds" value="'+role.roleId+'"  id="sysUserAdd_'+role.roleId+'"/> <label for="sysUserAdd_'+role.roleId+'">'
																	+ role.name
																	+ '</label>';
										});
		
										$("#sysUserAddRoles").html(roleChecks);
								}
							});
		}
		//点击按钮修改
		sysUser.toEdit = function() {
			var row = $("#sysUserDataGrid").datagrid('getSelected');
			if (row) {
				toEdit(row);
			} else {
				uiEx.msg("请选择要修改的行");
			}
		}

		//打开修改	
		function toEdit(row) {
			uiEx.resetForm("#sysUserEditForm");
			uiEx.openDialog("#sysUserEditDialog", "用户修改");
			uiEx.loadForm("#sysUserEditForm", row, "");
			/*
			 * 初始化角色复选框
			 */
			$.getJSON("SysRole/all",
							null,
							function(data) {
								var roleChecks = "";
								var chks = "";
								if(data.list){
									// 权限选择
									$.each(
									data.list,
									function(i, role) {
										chks = EasyEE.has(
												row.roleIds,
												role.roleId) ? "checked='checked'"
												: "";
										
										roleChecks += '<input type="checkbox" name="roleIds" '+chks+' value="'+role.roleId+'"  id="sysUserEdit_'+role.roleId+'"/> <label for="sysUserEdit_'+role.roleId+'">'
												+ role.name
												+ '</label>';
									});
									$("#sysUserEditRoles").html(roleChecks);
								}
							});
		}
		;
		//删除
		sysUser.toDelete = function() {
			//datagridSelector,  showMsg, reloadDataGrid, successKey, successValue
			
			var rows = $("#sysUserDataGrid").datagrid('getSelections');
			if (rows) {
			    dg.rowDelete(true, false, "statusCode", "200");
			/* 	var ids = "";
				$.each(rows, function(i, v) {
					ids += v.userId + ",";
				}); 
				
				$.post("SysUser/delete","",function(data){
					if(data.statusCode==200){
						
					}
				},"json"); */
				
			} else {
				uiEx.msg("请选择要删除的行");
			}

		};
		//搜索
		sysUser.doSearch = function() {
			dg.datagrid("load", {
				'name' : $("#sysUserName").val(),
				'realName' : $("#sysRealName").val(),
				'status' : $("#sysUserStatus").combobox("getValue")
			});
		}

	});
</script>
<%-- 2. 页面内容 --%>
<table id="sysUserDataGrid" title="用户列表" style=""
	toolbar="#sysUserToolbar" idField="userId" rownumbers="true"
	fitColumns="true" nowrap="false" fit="true" >
	<thead>
		<tr>
			<th field="ck" checkbox="true" width="50" sortable="true">多选框</th>
			<th field="name" width="50" sortable="true">名称</th>
			<shiro:hasPermission name="showRealNameColumn">
			<th field="realName" width="50" sortable="true">真实姓名</th>
			</shiro:hasPermission>
			<th field="status" width="50" formatter="sysUser.formatStatus"
				sortable="true">状态</th>
			<th field="roleNames" width="50" formatter="sysUser.formatRoles">角色</th>
		</tr>
	</thead>
</table>
<div id="sysUserToolbar">
	<div>
		<span>用户名:</span> <input name="name" id="sysUserName" class="easyui-textbox" />
		<span>真实姓名:</span> <input name="realName" id="sysRealName" class="easyui-textbox" />
		<span>状态:</span> <select name="status" class="easyui-combobox"
			id="sysUserStatus" style="width:90px;"
			data-options="editable:false,panelHeight:'auto'">
			<option value="">--全部--</option>
			<option value="0">正常</option>
			<option value="1">禁用</option>
		</select> <a class="easyui-linkbutton" iconCls="icon-search" plain="true"
			onclick="sysUser.doSearch()"><s:message code="label.search"></s:message></a>
	</div>
	<shiro:hasPermission name="sysUserAddBtn">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true" onclick="sysUser.toAdd()">添加用户</a>
	</shiro:hasPermission>
		
		<shiro:hasPermission name="sysUserUpdateBtn">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true"
		onclick="sysUser.toEdit()">修改用户</a> 
	</shiro:hasPermission>
		
		<shiro:hasPermission name="sysUserDelBtn">
		<a class="easyui-linkbutton"
		iconCls="icon-remove" plain="true" onclick="sysUser.toDelete()">删除用户</a>
</shiro:hasPermission>
<%-- grid右键菜单 --%>
<div id="sysUserContextMenu" class="easyui-menu" style="width:120px;">
<shiro:hasPermission name="sysUserAddBtn">
	<div onclick="sysUser.toAdd()" data-options="iconCls:'icon-add'">添加</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="sysUserUpdateBtn">
	<div onclick="sysUser.toEdit()" data-options="iconCls:'icon-edit'">修改</div>
	</shiro:hasPermission>
	<shiro:hasPermission name="sysUserDelBtn">
	<div onclick="sysUser.toDelete()" data-options="iconCls:'icon-remove'">删除</div>
	</shiro:hasPermission>
</div>
</div>

<%-- grid右键菜单 --%>
<div id="sysRoleContextMenu" class="easyui-menu" style="width:120px;">
	<div onclick="sysRole.toAdd()" data-options="iconCls:'icon-add'">添加</div>
	<div onclick="sysRole.toEdit()" data-options="iconCls:'icon-edit'">修改</div>
	<div onclick="sysRole.toDelete()" data-options="iconCls:'icon-remove'">删除</div>
</div>

<%-- 3. 包含的Dialog页面等其他内容 --%>
<%@ include file="../../dialog/sys/sysUserAdd.jsp"%>
<%@ include file="../../dialog/sys/sysUserEdit.jsp"%>
