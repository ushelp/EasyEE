<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>
<script type="text/javascript">
	//员工操作命名空间
	var Emp = {};
	$(function() {
		/*
		 * datagrid数据格式化
		 */
		/*
		 * 数据表格初始化
		 */
		var dg = $("#empDataGrid");

		// 第一次DG加载，以后无需初始化combobox
		var firstLoad = true;

		$("#empDataGrid").initDatagrid({
			/*
			 * 行编辑:saveUrl、updateUrl、destroyUrl配合uiEx使用
			 */
			url : "Emp/list",
			saveUrl : "Emp/save",
			updateUrl : "Emp/update",
			destroyUrl : "Emp/delete",
			idField : "empno",
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
			menuSelector : "#empContextMenu",
			showContextMenu : true,
			sendRowDataPrefix : "emp.", //提交数据前缀
			successKey : "statusCode", //服务器端返回的成功标记key
			successValue : "200", //服务器端返回的成功标记value
			onLoadSuccess : function(data) {
				// 第一次DG加载，以后无需初始化combobox
				//if (firstLoad) {
					// 部门数据集合，添加和修改时无需去服务器查询
					Emp.deptList = data.allDept;
					$('#empDeptno').combobox({
						data : Emp.deptList,
						valueField : 'deptno',
						textField : 'dname',
						panelHeight : 'auto',
						editable : false
					});
					firstLoad = false;
				//}

			},
			//双击操作
			onDblClickRow : function(rowIndex, rowData) {
				toEdit(rowData);
			},
			mutipleDelete : true, // 多行提交删除
			mutipleDeleteProperty : "empno" // 多行删除时提及给服务器的属性和值，不会添加sendRowDataPrefix前缀，支持使用数组指定多个属性名
		});

		/*
		 * 增删改
		 */

		//搜索
		Emp.doSearch = function() {
			dg.datagrid("load", {
				'empCriteria.ename' : $("#empName").val(),
				'empCriteria.job' : $("#empJob").val(),
				'empCriteria.deptno' : $("#empDeptno").combobox("getValue")
			});
		}
		Emp.clearAll = function() {
			$("EmpSearchForm")[0].reset();
			dg.datagrid("load", {
			});
		}

		Emp.toAdd = function() {
			uiEx.resetForm("#empAddForm");
			uiEx.openDialog("#empAddDialog", "用户添加");
			/*
			 * 初始化部门combobox
			 */
			$.post("Emp/allDept","",function(data){
				/*
				 * 初始化部门combobox
				 */
				$('#addDeptno').combobox({
					data : data,
					valueField : 'deptno',
					textField : 'dname',
					panelHeight : 'auto',
					editable : false,
					onLoadSuccess : function() { //加载完成后,设置选中第一项
						var val = $(this).combobox("getData");
						$(this).combobox("select", val[0].deptno);
					}
				});
			})
		}

		//点击按钮修改
		Emp.toEdit = function() {
			var row = $("#empDataGrid").datagrid('getSelected');
			if (row) {
				toEdit(row);
			} else {
				uiEx.msg("请选择要修改的行");
			}
		}
		// 删除 
		Emp.toDelete = function() {
			var rows = $("#empDataGrid").datagrid('getSelections');
			if (rows) {
				dg.rowDelete(true, false, "statusCode", "200");
			} else {
				uiEx.msg("请选择要删除的行");
			}
		}

		//打开修改	
		function toEdit(row) {
			uiEx.resetForm("#empEditForm");
			uiEx.openDialog("#empEditDialog", "员工修改");

			uiEx.loadForm("#empEditForm", row, "emp.");
			/*
			 * 初始化部门combobox
			 */
			 $.post("Emp/allDept","",function(data){
				$('#editDeptno').combobox({
					data : data,
					valueField : 'deptno',
					textField : 'dname',
					panelHeight : 'auto',
					editable : false,
					onLoadSuccess : function() { //加载完成后,设置选中第一项
						var val = $(this).combobox("getData");
						$(this).combobox("select", row.dept.deptno);
					}
				});
			 });
		}
		;

	});
</script>

<%-- 2. 页面内容 --%>
<table id="empDataGrid" title="员工列表" style="width: 100%" toolbar="#empToolbar"
	idField="empno" rownumbers="true" fitColumns="true" nowrap="false">
	<thead>
		<tr>
			<th field="ck" checkbox="true" width="50" sortable="true">多选框</th>
			<th field="ename" width="50" sortable="true">员工姓名</th>
			<th field="job" width="50" sortable="true">职务</th>
			<th field="dept.dname" width="50" sortable="true">部门</th>
		</tr>
	</thead>
</table>
<div id="empToolbar">
	<div>
	<form action="" id="EmpSearchForm">
		<span>员工姓名:</span> <input name="empCriteria.ename" id="empName"
			class="easyui-textbox" /> <span>职务:</span> <input
			name="empCriteria.job" id="empJob" class="easyui-textbox" /> <span>部门:</span>
		<select name="empCriteria.deptno" class="easyui-combobox"
			id="empDeptno" style="width:90px;"
			data-options="editable:false,panelHeight:'auto'">
		</select> <a class="easyui-linkbutton" iconCls="icon-search" plain="true"
			onclick="Emp.doSearch()"><s:text name="label.search"></s:text></a>
		<a class="easyui-linkbutton" iconCls="icon-clear" plain="true" onclick="Emp.clearAll()"><s:text name="label.clear"></s:text></a>
	</form>
	</div>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-add',plain:true" onclick="Emp.toAdd()">添加员工</a>
	<a href="javascript:void(0)" class="easyui-linkbutton"
		data-options="iconCls:'icon-edit',plain:true" onclick="Emp.toEdit()">修改员工</a>
	<a class="easyui-linkbutton" iconCls="icon-remove" plain="true"
		onclick="Emp.toDelete()">删除员工</a>

</div>
<%-- grid右键菜单 --%>
<div id="empContextMenu" class="easyui-menu" style="width:120px;">
	<div onclick="Emp.toAdd()" data-options="iconCls:'icon-add'">添加员工</div>
	<div onclick="Emp.toEdit()" data-options="iconCls:'icon-edit'">修改员工</div>
	<div onclick="Emp.toDelete()" data-options="iconCls:'icon-remove'">删除员工</div>

</div>


<%-- 3. 包含的Dialog页面等其他内容 --%>
<%@ include file="/WEB-INF/content/dialog/hr/EmpAdd.jsp"%>
<%@ include file="/WEB-INF/content/dialog/hr/EmpEdit.jsp"%>
