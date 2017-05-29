<%@page import="org.apache.shiro.SecurityUtils"%>
<%@page import="org.apache.shiro.authc.AuthenticationException"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<base href="<%=basePath%>">

<title>EasyEE-SM Login</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="sh,easyee,javaee,framework,java">
<meta http-equiv="description" content="EasyEE-SM basic framework by EasyProject">
 

<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,700|Montserrat:400,700|Roboto:400,700,900" rel="stylesheet"> 
<!-- EasyUI CSS -->
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/metro-blue/easyui.css" id="themeLink">
<link rel="stylesheet" type="text/css" href="staticresources/easyui/themes/icon.css">
<style type="text/css">
* {
	font-size: 15px;
	font-family: 'Open Sans', Arial, Helvetica, sans-serif; 
}

h1,h1 span {
	font-size: 48px;
	font-family: 'Roboto', '微软雅黑'; 
	font-weight: 700;
}

.footer {
	margin: 10px auto;
}
.title{ margin-bottom: 20px;text-align: center;}
.fname {
	color: #0084FF;
	font-weight: normal;
	font-family: 'Oxygen', 微软雅黑; 
}
</style>
<!-- 全局变量 -->
<script type="text/javascript">
var EasyEE={
		basePath:'<%=basePath%>'
	}
</script>

<!-- EasyUI JS -->
<script type="text/javascript" src="staticresources/easyui/jquery.min.js"></script>
<script type="text/javascript" src="staticresources/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="staticresources/easyui/locale/easyui-lang-zh_CN.js"></script>

<!-- EasyUIEx -->
<link rel="stylesheet" type="text/css" href="staticresources/easyuiex/css/easyuiex.css">
<script type="text/javascript" src="staticresources/easyuiex/easy.easyuiex.min.js"></script>
<script type="text/javascript" src="staticresources/easyuiex/easy.easyuiex-validate.js"></script>
<!-- EasyUIEx的默认消息语言为中文，使用其他语言需要导入相应语言文件 -->
<script type="text/javascript" src="staticresources/easyuiex/lang/easy.easyuiex-lang-zh_CN.js"></script>
<%-- jquery Cookie plugin --%>
<script type="text/javascript" src="staticresources/easyee/jquery.cookie.js"></script>

<!-- 自定义页面相关JS -->
<script type="text/javascript" src="script/login.js"></script>


<%--
<script type="text/javascript">
 	//主题切换
	$(function(){
		$("#themeCombobox").combobox({
			  onChange : function(newValue,oldValue){
				  document.getElementById("themeLink").href="<%=basePath%>easyui/themes/"+ newValue + "/easyui.css";
				}
		});
	})
</script>
 --%>
 <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro" %>

<%-- <%
Object exceptionClassName=session.getAttribute("shiroLoginFailure"); 
if(exceptionClassName!=null){
	 String authExp = exceptionClassName.toString(); 
	 // you can do something...
	 out.println(exceptionClassName);
	 session.removeAttribute("shiroLoginFailure");
}
%> --%>
<!-- 登录消息提示JS -->
<c:if test="${!empty MSG}">

	<script type="text/javascript">
		$(function() {
			uiEx.alert("${MSG }", "info");
		})
	</script>
	<c:remove var="MSG" scope="session"/>
</c:if>
</head>

<body>
	<div
		style="text-align: center;overflow:auto;width:100%;height:100%;margin: 10px auto;">
		<h1><span style="color:#8FC31F">Easy</span><span style="color:#376E91">E</span><span style="color:#376E91">E</span>-<span style="">SM Platform</span></h1>
	<div class="title">
	( <span class="fname">SpringMVC</span>
			+ <span class="fname">MyBatis</span> 
			 + <span class="fname">EasyShiro</span> + <span class="fname">EasyFilter</span>  + <span class="fname">EasyUI</span> + <span class="fname">EasyUIEX</span> )
	
			<br />
	
	</div>
		<div style="margin: 10px auto;">
	<!-- 		theme： <input id="themeCombobox" class="easyui-combobox"
				data-options="" /> -->
			<%--  <select id="themeCombobox" class="easyui-combobox"  data-options="editable:false,panelHeight:'auto'" name="themeCombo" style="width:200px;">
		    <option value="default">default</option>
		    <option value="black">black</option>
		    <option value="bootstrap">bootstrap</option>
		    <option value="gray">gray</option>
		    <option value="metro">metro</option>
		    <option value="metro-blue">metro-blue</option>
		    <option value="metro-gray">metro-gray</option>
		    <option value="metro-green">metro-green</option>
		    <option value="metro-orange">metro-orange</option>
		    <option value="metro-red">metro-red</option>
		    <option value="ui-cupertino">ui-cupertino</option>
		    <option value="ui-dark-hive">ui-ui-dark-hive</option>
		    <option value="ui-pepper-grinder">ui-ui-pepper-grinder</option>
		    <option value="ui-sunny">ui-ui-sunny</option>
	    </select> --%>
		</div>
		<c:if test="${!empty IPLock}">
			<span style="color:#ff0000; font-weight:bold">您的 IP 地址由于连续登录错误过多，已被锁定 2 小时，请稍后再试。</span>
		</c:if>
		<div style="margin: 0 auto;width: 500px;">
			<div class="easyui-panel" title="User Login" style="width:500px;">
				<div style="padding:10px 60px 20px 60px;">
					<form id="loginForm" class="easyui-form" method="post"
						data-options="novalidate:true" action="toLogin">
						
						<table cellpadding="5" style="">
							
							<tr>
								<td width="90">Theme：</td>
								<td><input id="themeCombobox" class="easyui-combobox" data-options="" /></td>
							</tr>
							<tr>
								<td width="90">Username:</td>
								<td><input class="easyui-textbox" type="text"
									name="name" id="username" style="height:30px;width: 190px;"
									data-options="validType:[],required:true,prompt:'user name...'" value="demo"></input></td>
								<!-- 								<td><input class="easyui-textbox" type="text" name="uname" style="height:30px;width: 180px;"
									data-options="validType:['email','startChk[\'A\']'],required:true"></input></td> -->
							</tr>
							<tr>
								<td>Password:</td>
								<td><input class="easyui-textbox" type="password"
									name="password" style="height:30px;width: 190px;"
									data-options="required:true" value="111111"></input></td>
							</tr>
						<%--  <tr>
								<td>&nbsp;</td>
								<td>
								<input class="easyui-checkbox" type="checkbox"
									name="autoLogin" value="true" id="autoLogin"></input>
									<label for="autoLogin">下次自动登录</label>
									</td>
							</tr> --%>
							<%--	<tr>
								<td>记住我:</td>
								<td><input class="easyui-checkbox" type="checkbox"
									name="rememberMe" value="true"></input></td>
							</tr>  --%>
							
							<%-- <c:if test="${!empty sessionScope.ShowCAPTCHA }"></c:if> --%>
							<tr>
								<td>Verification:</td>
								<td>
								<input class="easyui-validatebox textbox"
									id="captcha" name="captcha"   
									style="height:30px;width: 80px;" data-options="required:true, validType:'minLength[4]' , tipPosition:'right',position:'bottom', deltaX:105"
									maxlength="4"></input> 
									<div style="display: none; float: right; border: 1px solid #ccc;" id="vcTr">
										<img  title="点击切换" alt="加载中..." align="middle"
											style="cursor: pointer;" width="100" height="28" id="vcImg" src="jsp/VerifyCode.jsp">
									</div></td>
							</tr>   
						<%--  </c:if> --%>  
							
							<!-- <tr style="display: none;" id="vcTr">
								<td></td>
								<td>

									<img alt="点击切换" style="cursor: pointer;" id="vcImg" src="jsp/VerifyCode.jsp">
									</td>
							</tr> -->
						</table>
					</form>
					<div style="text-align:center;padding:5px">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							id="loginBtn" iconCls="icon-man" style="padding: 3px 10px">Login</a> <a
							href="javascript:void(0)" iconCls="icon-clear"
							class="easyui-linkbutton" onclick="uiEx.clearForm('#loginForm')"style="padding: 3px 10px">Reset</a>
					</div>

				</div>
			</div>

		</div>
		<div class="footer">
			<p>
				© 2012 - 2099 Ray <a href="http://easyproject.cn/"
					style="color: #8EBB31;font-weight: bold;text-decoration: underline;">EasyProject</a>

			</p>
			<p>
				联系、反馈、定制、培训/Contact, Feedback, Custom, Train Email：<a
					href="mailto:inthinkcolor@gmail.com">inthinkcolor@gmail.com</a>
			</p>
			</div>

	</div>

</body>
</html>
