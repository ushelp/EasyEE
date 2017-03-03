<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>
<script type="text/javascript">

	//角色操作命名空间
	var sysRole = {};
	$(function() {
		/*
		 * datagrid数据格式化
		 */
		sysRole.formatStatus = function(val, row) {
			if (val != 0) {
				return '<span style="color:red;">禁用</span>';
			} else {
				return "正常";
			}
		}

		/*
		 * 数据表格初始化
		 */
		var dg = $("#sysRoleDataGrid");
		dg.initDatagrid({
			iconCls : 'icon-group',
			/*
			 * 行编辑:saveUrl、updateUrl、destroyUrl配合uiEx使用
			 */
			url : "SysRole/list",
			saveUrl : "SysRole/save",
			updateUrl : "SysRole/update",
			destroyUrl : "SysRole/delete",
			showHeaderContextMenu : true, // 表头添加右键菜单，可选择显示的列
			// clickRowEdit:true, //注册单击行编辑，可以代替edatagrid实现带行编辑的CRUD
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			checkbox : true,
			singleSelect : false,
			checkOnSelect : true,
			//双击操作
			onDblClickRow : function(rowIndex, rowData) {
				sysRole.editIndex = rowIndex;
				toEdit(rowData);
			},
			menuSelector : "#sysRoleContextMenu",
			showContextMenu : true,
			sendRowDataPrefix : "sysRole."
		});

		sysRole.toAdd = function() {
			uiEx.resetForm("#sysRoleAddForm");
			uiEx.openDialog("#sysRoleAddDialog", "角色添加");

			uiEx.initTreeChk("#sysRoleAddPermissions", {
				url : "SysMenuPermission/listAllForSysRole",
				checkbox : true,
				animate : true,
				lines : true,
				noChildCascadeCheck : true,
				showTitle : "remark"
			});

			/* $("#sysRoleAddPermissions").combotree({
				url : 'SysMenuPermission/list',
				checkbox:true,
				cascadeCheck:true,
				onLoadSuccess : function(node, data) {
					//$("#sysRoleAddPermissions").combotree('setValue', data[0].id);
				}
			});  */
		}

		//点击按钮修改
		sysRole.toEdit = function() {
			var row = $("#sysRoleDataGrid").datagrid('getSelected');
			if (row) {
				toEdit(row);
			} else {
				uiEx.msg("请选择要修改的行");
			}
		}

		//打开修改	
		function toEdit(row) {
			uiEx.resetForm("#sysRoleEditForm");
			uiEx.openDialog("#sysRoleEditDialog", "角色修改");
			uiEx.loadForm("#sysRoleEditForm", row, "sysRole.");

			//查询角色所有权限
			$.post("SysRole/getAllPermissionsId", "sysRole.roleId="
					+ row.roleId, function(data) {
				/*
				 * 初始化权限复选框
				 */
				uiEx.initTreeChk("#sysRoleEditPermissions", {
					url : "SysMenuPermission/listAllForSysRole",
					checkbox : true,
					animate : true,
					lines : true,
					noChildCascadeCheck : true,
					showTitle : "remark"

				}, data.list);

			}, "json");

		}

		sysRole.toDelete = function() {
			// datagridSelector,  showMsg, reloadDataGrid, successKey, successValue

			var rows = $("#sysRoleDataGrid").datagrid('getSelections');
			if (rows) {
				dg.rowDelete(true, false, "statusCode", "200");
				/* var ids = "";
				$.each(rows, function(i, v) {
					ids += v.userId + ",";
				}); */
			} else {
				uiEx.msg("请选择要删除的行");
			}
		}

		/*
		 * 搜索
		 */

		sysRole.doSearch = function() {
			$("#sysRoleDataGrid").edatagrid(
					"load",
					{
						'sysRoleCriteria.name' : $("#sysRoleName").val(),
						'sysRoleCriteria.status' : $("#sysRoleStatus")
								.combobox("getValue")
					});
		}
	});
	
</script>
 
<%-- <div  class="easyui-panel" title="My Panel" data-options="noheader:true,fit:true">  
 <div class="easyui-layout" data-options="fit:true,border:false">
        <div data-options="region:'center',border:false" title="" style="overflow: hidden;width: 100%">
 --%>	<%-- 2. 页面内容 --%>
	<table id="sysRoleDataGrid" title="角色列表" style="width: 100%"
		toolbar="#sysRoleToolbar" idField="roleId" rownumbers="true"
		fitColumns="true" nowrap="false">
		<thead>
			<tr>
				<th field="ck" checkbox="true" width="50" sortable="true">多选框</th>
				<th field="name" width="50" sortable="true">名称</th>
				<th field="status" width="50" formatter="sysRole.formatStatus"
					sortable="true">状态</th>
				<th field="remark" width="50">备注</th>
			</tr>
		</thead>
	</table>
	<div id="sysRoleToolbar">
		<div>
			<span>角色名:</span> <input name="sysRoleCriteria.name" id="sysRoleName"
				class="easyui-textbox" /> <span>状态:</span> <select
				name="sysRoleCriteria.status" class="easyui-combobox"
				id="sysRoleStatus" style="width:90px;"
				data-options="editable:false,panelHeight:'auto'">
				<option value="">--全部--</option>
				<option value="0">正常</option>
				<option value="1">禁用</option>
			</select> <a class="easyui-linkbutton" iconCls="icon-search" plain="true"
				onclick="sysRole.doSearch()"><s:text name="label.search"></s:text></a>
		</div>
		<a href="javascript:void(0)" class="easyui-linkbutton"
			data-options="iconCls:'icon-add',plain:true"
			onclick="sysRole.toAdd()">添加角色</a> <a href="javascript:void(0)"
			class="easyui-linkbutton"
			data-options="iconCls:'icon-edit',plain:true"
			onclick="sysRole.toEdit()">修改角色</a> <a class="easyui-linkbutton"
			iconCls="icon-remove" plain="true" onclick="sysRole.toDelete()">删除角色</a>

	</div>

	<%-- grid右键菜单 --%>
	<div id="sysRoleContextMenu" class="easyui-menu" style="width:120px;">
		<div onclick="sysRole.toAdd()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="sysRole.toEdit()" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="sysRole.toDelete()" data-options="iconCls:'icon-remove'">删除</div>
	</div>
<%-- </div>
</div></div> --%>
	<%-- 3. 包含的Dialog页面等其他内容 --%>
	<%@ include file="../../dialog/sys/sysRoleAdd.jsp"%>
	<%@ include file="../../dialog/sys/sysRoleEdit.jsp"%>


