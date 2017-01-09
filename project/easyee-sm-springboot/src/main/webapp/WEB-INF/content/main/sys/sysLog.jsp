<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>
<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>
<script type="text/javascript">
	//用户操作命名空间
	var sysLog = {};
	$(function() {
		/*
		 * datagrid数据格式化
		 */
		sysLog.formatStatus = function(val, row) {
			if (val != 0) {
				return '<span style="color:red;">禁用</span>';
			} else {
				return "正常";
			}
		}
		sysLog.formatRoles = function(val, row) {
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
		 * sysLog.doSearch = function() { $("#sysLogDataGrid").edatagrid("load", {
		 * name : $("#name3").val(), sex : $("#sex3").combobox("getValue") }); }
		 */
		/*
		 * 数据表格初始化
		 */
		var dg = $("#sysLogDataGrid");
		dg.initDatagrid({
			iconCls : 'icon-group',
			/*
			 * 行编辑:saveUrl、updateUrl、destroyUrl配合uiEx使用
			 */
			url : "SysLog/list",
			saveUrl : "SysLog/save",
			updateUrl : "SysLog/update",
			destroyUrl : "SysLog/delete",
			showHeaderContextMenu:true, // 表头添加右键菜单，可选择显示的列
			// clickRowEdit:true, //注册单击行编辑，可以代替edatagrid实现带行编辑的CRUD
			pageSize : 10,
			pageList : [ 5, 10, 15, 20 ],
			checkbox : false,
			singleSelect : false,
			checkOnSelect : true,
			//双击操作
			onDblClickRow : function(rowIndex, rowData) {
				toEdit(rowData);
			},
			menuSelector:"#sysLogContextMenu",
			showContextMenu:true,
			sendRowDataPrefix:"sysLog.",
			mutipleDelete: true, // 多行提交删除
			mutipleDeleteProperty:"userId" // 多行删除时提及给服务器的属性和值，不会添加sendRowDataPrefix前缀，支持使用数组指定多个属性名
		});
		//搜索
		sysLog.doSearch = function() {
			dg.datagrid("load", {
				'account' : $("#sysLogAccount").val(),
				'ip' : $("#sysLogIp").val(),
				'startTime' : $("#sysLogStartTime").datetimebox("getValue"),
				'endTime' : $("#sysLogEndTime").datetimebox("getValue")
			});
		}

	});
</script>

<%-- 2. 页面内容 --%>
<table id="sysLogDataGrid" title="用户列表" style="width: 100%"
	toolbar="#sysLogToolbar" idField="logId" rownumbers="true"
	fitColumns="true" nowrap="false" >
	<thead>
		<tr>
			<th field="action" width="50" sortable="true">操作动作</th>
			<th field="parameters" width="50" sortable="true">操作参数</th>
			<th field="res" width="50" sortable="true">操作结果</th>
			<th field="account" width="50" sortable="true">操作用户</th>
			<th field="ip" width="50" sortable="true">操作IP</th>
			<th field="logTime" width="50" sortable="true">操作日期</th>
		</tr>
	</thead>
</table>
<div id="sysLogToolbar">
	<div>
		<span>账户:</span> <input name="account" id="sysLogAccount" class="easyui-textbox" />
		<span>IP:</span> <input name="ip" id="sysLogIp" class="easyui-textbox" />
		<span>操作日期:</span>
		 <input name="startTime" id="sysLogStartTime" class="easyui-datetimebox" style="width:200px" data-options="
            prompt: '开始时间',
            icons:[{
                iconCls:'icon-clear',
                handler: function(e){
                    $(e.data.target).datetimespinner('clear');
                }
            }]
            "> - 
		 <input name="endTime" id="sysLogEndTime" class="easyui-datetimebox" style="width:200px" data-options="
            prompt: '结束时间',
            icons:[{
                iconCls:'icon-clear',
                handler: function(e){
                    $(e.data.target).datetimespinner('clear');
                }
            }]
            ">
		<a class="easyui-linkbutton" iconCls="icon-search" plain="true"
			onclick="sysLog.doSearch()">Search</a>
	</div>
	
</div>
<%-- grid右键菜单 --%>


<%-- 3. 包含的Dialog页面等其他内容 --%>
	