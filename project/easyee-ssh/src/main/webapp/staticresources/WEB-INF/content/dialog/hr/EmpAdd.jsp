<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>

<script type="text/javascript">
$(function() {
	
	/*
	 * 执行增改
	 */
	//执行添加或修改
	Emp.add = function() {
		var dg=$("#empDataGrid");
		var url = "Emp/save";
		uiEx.submitURLAjax("#empAddForm", url, function(data) {
			data = eval("(" + data + ")");
			// 执行成功，刷新dg
			if (data.statusCode==200) {
				//uiEx.reloadSelTab("#tabs");
				//dg.datagrid('reload'); //刷新表格，在第当前页
				dg.datagrid({"pageNumber":data.page});//刷新表格
				//dg.datagrid('load'); //刷新表格
				uiEx.closeDialog("#empAddDialog");
			} 
		},{"rows":dg.datagrid("options").pageSize});
	}
});

</script>
<!-- 添加或修改Dialog -->
<div id="empAddDialog" class="easyui-dialog"
	style="width:480px;height:330px;padding:10px 20px;" resizable="true" closed="true"
	buttons="#empAdd-dlg-buttons">
	<div class="ftitle">用户信息</div>
	<form id="empAddForm" method="post">
			<input name="userId" type="hidden">
			 <table cellpadding="5">
	    		<tr>
	    			<td>员工姓名:</td>
	    			<td><input class="easyui-textbox" name="emp.ename"
				class="easyui-validatebox" required="true"
				data-options="prompt:'input username...'" style="width:180px"><span class="required">*</span></td>
	    		</tr>
	    		<tr>
	    			<td>职务:</td>
	    			<td><input class="easyui-textbox" name="emp.job" style="width:180px"
				class="easyui-validatebox"></td>
	    		</tr>
	    		<tr>
	    			<td>部门:</td>
	    			<td>
						<input id="addDeptno" name="emp.dept.deptno" required="true"> <span class="required">*</span>				
					</td>
	    		</tr>
	    	</table>
	</form>
</div>
<div id="empAdd-dlg-buttons">
	<a class="easyui-linkbutton" id="empAddSaveBtn" iconCls="icon-ok"
		onclick="Emp.add()">添加</a> <a class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:uiEx.closeDialog('#empAddDialog')">取消</a>
</div>