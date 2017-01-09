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
	//操作权限操作命名空间
	var sysOperationPermission = {};
	$(function() {

		var menuId;

		$("#sysOperationPermissionMenuDataGrid").treegrid({
			url : 'SysMenuPermission/list',
			rownumbers : true,
			idField : "id",
			treeField : 'text',
			lines : true,
			onClickRow : function(row) {
				menuId = row.id;
				$("#sysOperationPermissionDataGrid").datagrid('load', {
					"menuId" : menuId
				});
			}
		});

		$("#sysOperationPermissionDataGrid").initEdatagrid({
			/*
			 * 行编辑:saveUrl、updateUrl、destroyUrl配合uiEx使用
			 */
			url : "SysOperationPermission/list",
			saveUrl : "SysOperationPermission/save",
			updateUrl : "SysOperationPermission/update",
			destroyUrl : "SysOperationPermission/delete",
			idField : "operationPermissionId",
			showHeaderContextMenu:true, // 表头添加右键菜单，可选择显示的列
			pagination : false,
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			checkbox : true,
			checkOnSelect : true,
			singleSelect : false,
			autoSave : true,
			clickEdit : true, //单击编辑
			showMsg : true, // 显示操作消息
			//右键菜单
			menuSelector:"#sysOperationPermissionContextMenu",
			showContextMenu:true,
			sendRowDataPrefix : "sysOperationPermission.", //提交数据前缀
			onAdd : function(index, row) {
				row['sysMenuPermission.menuPermissionId'] = menuId;
			},
			successKey:"statusCode",  //服务器端返回的成功标记key
			successValue:"200"  //服务器端返回的成功标记value
		});

		sysOperationPermission.toAdd = function() {
			if(menuId){
				$('#sysOperationPermissionDataGrid').edatagrid('addRow')
			}else{
				uiEx.alert("请先选择要添加操作权限的菜单项");
			}
		}
		
		 function setHeight(){
            var c = $('#sysOperationPermissionLayout');
            c.layout();
            c.layout('resize',{
                height: $(document).outerHeight()-190
            });
        }
        setHeight();
	});
</script>

<div id="sysOperationPermissionLayout" style="width:100%;height:95%" >
	<div id="p"
		data-options="region:'west',iconCls:'icon-menu',collapsible:false,split:true"
		title="菜单列表" style="width:30%;  ">
		<table id="sysOperationPermissionMenuDataGrid" style="width: 100%"  rownumbers="true"
			fitColumns="true">
			<thead>
				<tr>
					<th data-options="field:'text'" style="width:96%; ">菜单项</th>
				</tr>
			</thead>
		</table>
	</div>
	<div
		data-options="region:'center',iconCls:'icon-rights',collapsible:false"
		title="操作权限">
		<table id="sysOperationPermissionDataGrid" style="width: 100%"
			toolbar="#sysOperationPermissionToolbar">
			<thead>
				<tr>
					<th field="ck" checkbox="true" width="50" sortable="true">多选框</th>
					<th data-options="field:'name'"
						editor="{type:'textbox',options:{required:true}}" height="200"
						width="120">权限名称</th>
					<th data-options="field:'action'"
						editor="{type:'textbox',options:{required:true,prompt:'多个动作使用 ,或#分割'}}" width="180">权限动作</th>
					<th data-options="field:'remark'"
						editor="{type:'textbox',options:{multiline:true,width:200,height:40}}"
						width="200">权限备注</th>
				</tr>
			</thead>
		</table>

	</div>
</div>

<%-- 2. 页面内容 --%>
<div id="sysOperationPermissionToolbar">
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true"
		onclick="sysOperationPermission.toAdd()">添加操作权限</a> <a
		class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="$('#sysOperationPermissionDataGrid').edatagrid('destroyRow')">删除操作权限</a>
	<a class="easyui-linkbutton" iconCls="icon-undo" plain="true"
		onclick="javascript:$('#sysOperationPermissionDataGrid').edatagrid('cancelRow')">撤销修改</a>
</div>


<%-- grid右键操作 --%>
<div id="sysOperationPermissionContextMenu" class="easyui-menu"
	style="width:120px;">
	<div onclick="sysOperationPermission.toAdd()"
		data-options="iconCls:'icon-add'">添加</div>
	<div onclick="$('#sysOperationPermissionDataGrid').edatagrid('destroyRow')"
		data-options="iconCls:'icon-remove'">删除</div>
	<div onclick="$('#sysOperationPermissionDataGrid').edatagrid('cancelRow')"
		data-options="iconCls:'icon-undo'">撤销</div>
</div>

<%-- 3. 包含的Dialog页面等其他内容 --%>
