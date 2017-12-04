<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CKEditor+CKFinder</title>
</head>
<body>

<!-- CKEditor -->
<%@ taglib uri="http://cksource.com/ckfinder" prefix="ckfinder"%>
<script src="/staticresources/ckeditor/ckeditor.js" type="text/javascript" charset="utf-8"></script>
<script src="/staticresources/ckfinder/ckfinder.js" type="text/javascript" charset="utf-8"></script>
<script src="/staticresources/ckfinder/config.js" type="text/javascript" charset="utf-8"></script>

	<textarea name="editor" id="editor" rows="10" cols="80">
	    This is my textarea to be replaced with CKEditor.
	</textarea>

	<button type="button" onclick="getValue()">获取内容</button>
	
	<script>
		window.onload=function(){
			var editor=CKEDITOR.replace('editor', {});
			CKFinder.setupCKEditor( editor, { 
				basePath : '/staticresources/ckfinder/', 
				skin : 'bootstrap',
			});
		}
		
		function getValue() {
			// 获取 ckeditor 编辑器实例
			var oEditor = CKEDITOR.instances.editor;
			// 获取编辑器数据
			var data = oEditor.getData();
			alert(data);
		}
	</script>
</body>
</html>