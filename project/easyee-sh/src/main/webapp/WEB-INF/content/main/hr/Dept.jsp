<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.springframework.org/tags"  prefix="s"%>
<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>
<script type="text/javascript">
	//部门操作命名空间
	var Dept = {};
	$(function() {
		/*
		 * datagrid数据格式化
		 */
		/*
		 * 数据表格初始化
		 */
		var dg = $("#deptDataGrid");

		$("#deptDataGrid").initEdatagrid({
			/*
			 * 行编辑:saveUrl、updateUrl、destroyUrl配合uiEx使用
			 */
			url : "Dept/list",
			saveUrl : "Dept/save",
			updateUrl : "Dept/update",
			destroyUrl : "Dept/delete",
			idField : "deptno",
			showHeaderContextMenu : true, // 表头添加右键菜单，可选择显示的列
			pagination : true,
			checkbox : true,
			checkOnSelect : true,
			singleSelect : false,
			autoSave : true,
			//queryParam:{"rows":dg.datagrid("options").pageSize},
			clickEdit : true, //单击编辑
			showMsg : true, // 显示操作消息
			//右键菜单
			menuSelector : "#deptContextMenu",
			showContextMenu : true,
			sendRowDataPrefix : "", //提交数据前缀
			successKey : "statusCode", //服务器端返回的成功标记key
			successValue : "200" //服务器端返回的成功标记value
		});

		/*
		 * 增删改
		 */

		//搜索
		Dept.doSearch = function() {
			dg.datagrid("load", {
				'dname' : $("#deptName").val(),
				'loc' : $("#deptLoc").val()
			});
		}
		Dept.clearAll = function() {
			$("DeptSearchForm")[0].reset();
			dg.datagrid("load", {
			});
		}

	});
</script>

<%-- 2. 页面内容 --%>
<table id="deptDataGrid" title="部门列表" style="width: 100%" toolbar="#deptToolbar" idField="deptno" rownumbers="true" fitColumns="true" nowrap="false">
	<thead>
		<tr>
			<th field="ck" checkbox="true" width="50" sortable="true">多选框</th>
	<%-- 	<th field="deptno" width="50" sortable="true">部门编号</th> --%>
			<th field="dname" width="50" sortable="true" editor="{type:'textbox',options:{required:true}}">部门名称</th>
			<th field="loc" width="50"  sortable="true" editor="{type:'textbox'}">地址</th>
		</tr>
	</thead>
</table>

<%-- 2. 搜索 ToolBar --%>
<div id="deptToolbar">
	<div>
	<form action="" id="DeptSearchForm" onclick="Dept.doSearch()">
		<span>部门名称:</span> <input name="dname" id="deptName" class="easyui-textbox" />
		<span>地址:</span> <input name="loc" id="deptLoc" class="easyui-textbox" />
		<a class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="Dept.doSearch()">Search</a>
		<a class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="Dept.clearAll()"><s:message code="label.clear"></s:message></a>
	</form>
	</div>
	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="$('#deptDataGrid').edatagrid('addRow')">添加部门</a>
	<shiro:hasPermission name="deptDeleteShow">
		<a class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="$('#deptDataGrid').edatagrid('destroyRow')">删除部门</a>
	</shiro:hasPermission>
	<a class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#deptDataGrid').edatagrid('cancelRow')">撤销修改</a>
</div>

<%-- 3. grid右键菜单 --%>
<div id="deptContextMenu" class="easyui-menu" style="width:120px;">
	<div onclick="$('#deptDataGrid').edatagrid('addRow')" data-options="iconCls:'icon-add'">添加</div>
	<shiro:hasPermission name="deptDeleteShow">
		<div onclick="$('#deptDataGrid').edatagrid('destroyRow')" data-options="iconCls:'icon-remove'">删除</div>
	</shiro:hasPermission>
	<a class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="javascript:$('#deptDataGrid').edatagrid('cancelRow')">撤销修改</a>
 </div>


<%-- 4. 包含的Dialog页面等其他内容 --%>

