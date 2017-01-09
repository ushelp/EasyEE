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
	//菜单权限操作命名空间
	var sysMenuPermission = {};
	$(function() {

		$("#sysMenuPermissionDataGrid").initTreegrid({
			url : 'SysMenuPermission/list',
			rownumbers : true,
			idField : "id",
			treeField : 'text',
			lines : true,
			//双击操作
			onDblClickRow : function(rowData) {
				toEdit(rowData);
			},
			// 表头添加右键菜单，可选择显示的列
			showHeaderContextMenu : true,
			menuSelector : "#sysMenuPermissionContextMenu",
			showContextMenu : true
		});

		/*
		 * 去添加
		 */
		sysMenuPermission.toAdd = function(parent) {
			uiEx.resetForm("#sysMenuPermissionAddForm");
			uiEx.openDialog("#sysMenuPermissionAddDialog", "菜单权限添加");
<%-- 支持创建根节点：url:'SysMenuPermission/list2 --%>
	$("#sysMenuPermissionAddParent").combotree(
					{
						url : 'SysMenuPermission/list',
						required : true,
						onLoadSuccess : function(node, data) {
							/*
							 * 父菜单默认选择第一项
							 */
							if (parent) {
								var row = $("#sysMenuPermissionDataGrid").treegrid(
										'getSelected');
								if (row) {

									$('#sysMenuPermissionAddParent').combotree(
											'setValue', row.id);
									return true;
								}
							}
							$('#sysMenuPermissionAddParent').combotree('setValue',
									data[0].id);

						}
					});
		}

		//点击按钮修改
		sysMenuPermission.toEdit = function() {
			var row = $("#sysMenuPermissionDataGrid").treegrid('getSelected');
			if (row) {
				toEdit(row);
			} else {
				uiEx.msg("请选择要修改的行");
			}
		}
		//次序移动
		sysMenuPermission.toMove = function(up) {
			var dg = $("#sysMenuPermissionDataGrid");
			var row = dg.treegrid('getSelected');
			if(!row){
				uiEx.msg("请选择要移动的菜单");
				return;
			}

			var childNode = dg.treegrid('getChildren', row._parentId);

			if (childNode.length == 1) {
				uiEx.msg("当前仅有一个菜单无需移动");
				return;
			}

			if (row) {
				if (up > 0) { //上移
					if(row==childNode[0]){
						uiEx.msg("已是第一个，无需上移");
						return;
					}
					up = true;
				} else { //下移
					if(row==childNode[childNode.length-1]){
						uiEx.msg("已是最后一个，无需下移");
						return;
					}
					up = false;
				}

				var url = "SysMenuPermission/move";
				$.getJSON(url, {
					"sysMenuPermission.menuPermissionId" : row.id,
					"up" : up
				}, function(data) {
					if (data.statusCode==200) {
						dg.treegrid('reload'); //刷新表格
					}
				});
			} else {
				uiEx.msg("请选择要修改的行");
			}

		}

		//删除
		sysMenuPermission.toDelete = function() {

			uiEx.confirm("确定要删除该菜单吗？不可恢复！", function(r) {
				if(r){
					var dg = $("#sysMenuPermissionDataGrid");
				var row = dg.treegrid('getSelected');

				if (row.children.length != 0) {
					uiEx.msg("该菜单拥有子菜单，请先删除子菜单！");
					return;
				}
				if (row) {
					var url = "SysMenuPermission/delete";
					$.getJSON(url, {
						"sysMenuPermission.menuPermissionId" : row.id
					}, function(data) {
						if (data.statusCode==200) {
							dg.treegrid('reload'); //刷新表格
						}
					});
				} else {
					uiEx.msg("请选择要删除的行");
				}
				}
			});

		}

		//打开修改	
		function toEdit(row) {
			console.info(row);
			$('#sysMenuPermissionEditParent').combotree("enable");
			uiEx.resetForm("#sysMenuPermissionEditForm");
			uiEx.openDialog("#sysMenuPermissionEditDialog", "菜单权限修改");

			var rowData = {
				"name" : row.text,
				"action" : row.url,
				"name" : row.text,
				"remark" : row.remark,
				"icon" : row.iconCls,
				"menuPermissionId" : row.id
			};
			console.info(rowData);
			uiEx.loadForm("#sysMenuPermissionEditForm", rowData, "sysMenuPermission.");
			//父菜单选择
			$("#sysMenuPermissionEditParent").combotree(
					{
						url : 'SysMenuPermission/list',
						onLoadSuccess : function(node, data) {
							//选择父菜单

							if (row._parentId == undefined
									|| row._parentId == null) {
								$('#sysMenuPermissionEditParent').combotree(
										"disable");
								$('#sysMenuPermissionEditParent').combotree(
										'setValue', "");
							} else {
								$('#sysMenuPermissionEditParent').combotree(
										'setValue', row._parentId);
							}
						}
					});

		}

	});
</script>

<%-- 2. 页面内容 --%>
<table id="sysMenuPermissionDataGrid" title="菜单权限列表" style=""
	toolbar="#sysMenuPermissionToolbar" rownumbers="true"  style="width: 100%" fitColumns="true">
	<thead>
		<tr>
			<th data-options="field:'text'" width="220">菜单名称</th>
			<th data-options="field:'url'" width="300" align="left">菜单动作</th>
			<th data-options="field:'remark'" width="450">备注</th>
		</tr>
	</thead>
</table>
<div id="sysMenuPermissionToolbar">

	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true"
		onclick="sysMenuPermission.toAdd(true)">添加菜单权限</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true"
		onclick="sysMenuPermission.toEdit()">修改菜单权限</a> <a
		href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-arrow_up',plain:true"
		onclick="sysMenuPermission.toMove(1)">菜单上移</a> <a href="javascript:void(0)"
		class="easyui-linkbutton"
		data-options="iconCls:'icon-arrow_down',plain:true"
		onclick="sysMenuPermission.toMove(-1)">菜单下移</a> <a
		class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="sysMenuPermission.toDelete()">删除菜单权限</a>

</div>


<%-- grid右键菜单 --%>
<div id="sysMenuPermissionContextMenu" class="easyui-menu"
	style="width:120px;">
	<div onclick="sysMenuPermission.toMove(1)" data-options="iconCls:'icon-add'">上移</div>
	<div onclick="sysMenuPermission.toMove(-1)"
		data-options="iconCls:'icon-remove'">下移</div>
	<div class="menu-sep"></div>
	<div onclick="sysMenuPermission.toAdd(true)"
		data-options="iconCls:'icon-add'">添加</div>
	<div onclick="sysMenuPermission.toEdit()" data-options="iconCls:'icon-edit'">修改</div>
	<div onclick="sysMenuPermission.toDelete()"
		data-options="iconCls:'icon-remove'">删除</div>
	<div class="menu-sep"></div>
	<div onclick="uiEx.collapse('#sysMenuPermissionDataGrid')">折叠目录</div>
	<div onclick="uiEx.expand('#sysMenuPermissionDataGrid')">展开目录</div>
</div>

<%-- 3. 包含的Dialog页面等其他内容 --%>
<%@ include file="../../dialog/sys/sysMenuPermissionAdd.jsp"%>
<%@ include file="../../dialog/sys/sysMenuPermissionEdit.jsp"%>