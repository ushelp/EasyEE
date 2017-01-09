<%@page import="java.text.SimpleDateFormat"%>
<%@page import="cn.easyproject.easyshiro.EasyLockUser"%>
<%@page import="net.sf.ehcache.Cache"%>
<%@page import="net.sf.ehcache.CacheManager"%>
<%@page import="org.apache.shiro.SecurityUtils"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	<%!
	String lockLogin="shiro-lockLoginCache";
	String lockLCheck="shiro-lockCheckCache";
	
	Cache cache=CacheManager.getInstance().getCache(lockLogin); 
	Cache cache2=CacheManager.getInstance().getCache(lockLCheck); 
	
	/**
	 * 清除所有锁定用户和IP信息
	 */
	public void unlockAll(){
		cache.removeAll();
		cache2.removeAll();
	}
	

	public void unlock(String key){
		cache.remove(key);
		cache2.remove(key);
	}
	%>    
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String t=request.getParameter("t");
if(t!=null){
	
	if(t.equals("unlock")){
		String key=request.getParameter("v");
		unlock(request.getParameter("v"));
		%>
		<script type="text/javascript">
		alert('Already unlock.');
		window.location.href="<%=basePath%>locklogin/admin.jsp";
		</script> 
		
		<%
	}else if(t.equals("clearAll")){
		unlockAll();
		%>
		<script type="text/javascript">
		alert('Already unlock all.');
		window.location.href="<%=basePath%>locklogin/admin.jsp";
		</script> 
		
		<%
		
	}
	
}

%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
    <base href="<%=basePath%>">
	<title>EasyShiro LockLogin Manager</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="EasyShiro, Shiro, Java, Security, EasyProject">
	<meta http-equiv="description" content="This is EasyShiro LockLogin Management">
	<!--<link rel="stylesheet" type="text/css" href="styles.css">-->
	<style type="text/css">
	body{margin: 0;padding: 0}  
	h1{font-size:48px; text-align: center; background-color: #3254A0; color: #fff;margin: 0;line-height: 100px; height: 100px;}
	#search{background-color:#F9F9F9; height: 80px; text-align: center;line-height: 80px; border-bottom: 1px solid #DFDFDF;}
	.txt{padding-left:5px; font-weight:bold; width: 300px; height: 40px; border: 1px solid #999; font-size: 16px; margin-right: 20px;}
	.btn{background-color: #097F39; color:#fff; font-size:16px; font-weight:bold; width: 140px; height: 42px; border: 0;}
	.btn:hover{background-color: #2FB043}
	.btn2{background-color: #FF7800; color:#fff; font-size:16px; font-weight:bold; width: 140px; height: 42px; border: 0;}
	.btn:hover{background-color: #FF8442}
	.bottom{
	border-top:1px solid #ccc;text-align: center; padding:10px; color: #999;}
	#tab{font-size: 16px;}
	#tab th{background: #F0F0E9;}
	#tab td{font-size: 17px;}
	#tab a{font-size: 17px; color:red;}
	table,table td,table th{border:1px solid #CECEC3;border-collapse:collapse;}
     #content{padding: 20px;}
     .unlock{background-color:#FF7800;color: #fff; border: 0; height: 30px; width: 50px;} 
     .unlock:hover{background-color:#FF8442 }
	</style>
</head>
<body> 

	<div>
		<h1>EasyShiro LockLogin Management</h1>
	</div>

	<div id="search">
		<form action="locklogin/admin.jsp" method="post" onsubmit="if(document.getElementById('t').value=='clearAll'){ return confirm('Unlock All?')}">
			<input type="hidden" value=""  name="t" id="t"/>
			<input type="text" value="${empty param.name?"":param.name }" class="txt" name="name" placeholder="Username or IP"/>
			<input type="submit" value="Search Locked" class="btn"  onclick="document.getElementById('t').value='search'"/>
			<input type="submit" value="Unlock All" class="btn2" onclick="document.getElementById('t').value='clearAll'"/>
		</form>
	</div>
	<div id="content">
	<%
	
	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// Search
	String name=request.getParameter("name");
	if(name!=null&& (!name.trim().equals(""))){
		String lockinfo=null;
		name=name.toLowerCase();
		String key1="user:"+name;
		String key2="ip:"+name;
		if(cache.get(key1)!=null){
			
			EasyLockUser user=(EasyLockUser)(cache.get(key1).getObjectValue());
		%>
			<form action="locklogin/admin.jsp" method="post" onsubmit="return confirm('Unlock the User?')">
			<input type="hidden" name="t" value="unlock"/>
			<input type="hidden" name="v" value="<%=key1 %>"/>
			
			<table id="tab" align="center" width="600" height="80">
				<tr>
					<th>Type</th>
					<th>Value</th>
					<th>Unlock Time</th>
					<th>Unlock</th>
				</tr>
				<tr align="center">
					<td>User</td>
					<td>${param.name }</td>
					<td><%=sdf.format(new Date(cache.get(key1).getExpirationTime())) %></td>
					<td><input class="unlock"  type="submit" value="unlock"/></td>
				</tr>
			</table>
			</form>
			<%
		}else if(cache.get(key2)!=null){
			EasyLockUser user=(EasyLockUser)(cache.get(key2).getObjectValue());
			%>
		<form action="locklogin/admin.jsp" method="post" onsubmit="return confirm('Unlock the IP?')">
			<input type="hidden" name="t" value="unlock"/>
			<input type="hidden" name="v" value="<%=key2 %>"/>
			<table id="tab" align="center" width="600" height="80">
				<tr>
					<th>Type</th>
					<th>Value</th>
					<th>Unlock Time</th>
					<th>Unlock</th>
				</tr>
				<tr align="center">
					<td>IP</td>
					<td>${param.name }</td>
					<td><%=sdf.format(new Date(cache.get(key2).getExpirationTime())) %></td>
					<td><input class="unlock" type="submit" value="unlock"/></td>
				</tr>
			</table>
			</form>
			<%
		}else{
			%>
			<div style="text-align:center; font-size: 20px; color: #FF0000;">
				'${param.name }' no lock information!
			</div>
			
			<%
		}
	}else{
		%>
		<div style="text-align: center;">
		EasyShiro locked result.
		</div>
		
		<%
		
	}
	
	%>
	
	</div>
	<div class="bottom" >
	EasyShiro - DESIGN BY EasyProject&copy;
	</div>
	
<%-- <%=cache.getKeys() %> --%>

</body>
</html>