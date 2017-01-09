<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%-- 1. 页面Datagrid初始化相关JS --%>
<%-- JS代码必须包含在页面中，引入外部JS文件会导致表格界面在未完成初始化前就显示，出现短暂的未初始化界面 --%>

<script type="text/javascript">
$(function() {
	var dg=$("#sysUserDataGrid");
	/*
	 * 执行增改
	 */
	//执行添加或修改
	sysUser.edit = function() {
		var url = "SysUser/update";
		uiEx.submitURLAjax("#sysUserEditForm", url, function(data) {
			data = eval("(" + data + ")");
			if (data.statusCode==200) {
				
				//uiEx.reloadSelTab("#tabs");
				dg.datagrid('reload'); //刷新表格，在第当前页
				//dg.datagrid({"pageNumber":data.page});//刷新表格
				//dg.datagrid('load'); //刷新表格
				uiEx.closeDialog("#sysUserEditDialog");
			} 
		},{"rows":dg.datagrid("options").pageSize});
	}
});

</script>
<div style="display: none;">
<!-- 添加或修改Dialog -->
<div id="sysUserEditDialog" class="easyui-dialog"
	style="width:480px;height:330px;padding:10px 20px;" resizable="true" closed="true"
	buttons="#sysUserEdit-dlg-buttons">
	<div class="ftitle">用户信息</div>
	<form id="sysUserEditForm" method="post">
			<input name="sysUser.userId" type="hidden">
			 <table cellpadding="5">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox easyui-validatebox" name="sysUser.name"
				  required="true"
				data-options="prompt:'input username...'" style="width: 200px"><span class="required">*</span></td>
	    		</tr>
	    		<tr>
	    			<td>真实姓名:</td>
	    			<td><input class="easyui-textbox easyui-validatebox" name="sysUser.realName"
				 style="width: 200px"></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input type="password" class="easyui-textbox easyui-validatebox" name="sysUser.password"
				 id="editPwd" validType="minLength['6']"  style="width: 200px">（不填写则不修改）</td>
	    		</tr>
	    		<tr>
	    			<td>确认密码:</td>
	    			<td><input type="password" class="easyui-textbox easyui-validatebox" name="confirmPwd"
						 validType="equals['#editPwd','与两次密码不一致.']"  style="width: 200px"></td>
	    		</tr>
	    		<tr>
	    			<td>状态:</td>
	    			<td>
						<input type="radio" name="sysUser.status" value="0" id="sysUserEditOn" checked="checked"  />
						<label for="sysUserEditOn">启用</label> 
						<input type="radio" name="sysUser.status" value="1" id="sysUserEditOff" />
						<label for="sysUserEditOff">禁用</label>
					</td>
	    		</tr>
	    		<tr>
	    			<td>角色:</td>
	    			<td>
							<div id="sysUserEditRoles"></div>
					</td>
	    		</tr>
	    	</table>
	</form>
</div>
<div id="sysUserEdit-dlg-buttons">
	<a class="easyui-linkbutton" id="sysUserEditSaveBtn" iconCls="icon-ok"
		onclick="sysUser.edit()">修改</a> <a class="easyui-linkbutton"
		iconCls="icon-cancel"
		onclick="javascript:uiEx.closeDialog('#sysUserEditDialog')">取消</a>
</div></div>