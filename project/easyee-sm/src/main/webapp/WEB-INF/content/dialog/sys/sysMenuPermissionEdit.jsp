<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>

<script type="text/javascript">
$(function() {
	
	/*
	* 格式化 icon 组合框
	*/
	sysMenuPermission.formatEditIconItem=function (row){
	    var s = '<span  class="'+row.name+'" style="padding: 0 15px;"></span>' +
	            '<span style="color:#888">'+row.name+'</span>';
	    return s;
	}
	
	
	/*
	* 执行添加
	*/
	sysMenuPermission.edit=function(){
		var dg=$("#sysMenuPermissionDataGrid");
		var url = "SysMenuPermission/update";
		uiEx.submitURLAjax("#sysMenuPermissionEditForm", url, function(data) {
			data = eval("(" + data + ")");
			if (data.statusCode==200) {
				dg.treegrid('reload'); //刷新表格
				uiEx.closeDialog("#sysMenuPermissionEditDialog");
			} 
		});
	}

});

</script>
<!-- 添加或修改Dialog -->
<div id="sysMenuPermissionEditDialog" class="easyui-dialog"
	style="width:480px;height:330px;padding:10px 20px" resizable="true" closed="true"
	buttons="#sysMenuPermissionEdit-dlg-buttons">
	<div class="ftitle">菜单权限信息</div>
	<form id="sysMenuPermissionEditForm" method="post">
	<input type="hidden" name="menuPermissionId"/>
			 <table cellpadding="5">
	    		<tr>
	    			<td>菜单名称:</td>
	    			<td><input class="easyui-textbox easyui-validatebox" name="name"
				required="true" style="width: 200px" 
				data-options="prompt:'input menu name...'"> <span class="required">*</span></td>
	    		</tr>
	    		<tr>
	    			<td>菜单动作:</td>
	    			<td><input  style="width: 200px"  class="easyui-textbox" name="action"></td>
	    		</tr>
	    		<tr>
	    			<td>菜单图标类样式:</td>
	    			<td>
						 <select id="sysMenuPermissionEditIcon" data-options="formatter: sysMenuPermission.formatEditIconItem,                
						 url: 'staticresources/easyee/json/icon.json',
                method: 'get',
                valueField: 'name',
                textField: 'name',
                value:'icon-application'" style="width: 200px" class="easyui-combobox" name="icon" >
						 </select>（可手动设置）
						</td>
	    		</tr>
	    		<tr>
	    			<td>父菜单:</td>
	    			<td>
	    				    <input id="sysMenuPermissionEditParent" name="sysMenuPermission.menuPermissionId" class="easyui-combotree" style="width:200px;"/>

	    			</td>
	    		</tr>
	    		<tr>
	    			<td>菜单备注:</td>
	    			<td><input class="easyui-textbox" multiline="true" style="height:40px;width: 200px;" name="remark"></td>
	    		</tr>
	    	</table>
	</form>
</div>
<div id="sysMenuPermissionEdit-dlg-buttons">
	<a class="easyui-linkbutton" id="sysMenuPermissionEditSaveBtn" iconCls="icon-ok"
		onclick="sysMenuPermission.edit()">修改</a> <a class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:uiEx.closeDialog('#sysMenuPermissionEditDialog')">取消</a>
</div>