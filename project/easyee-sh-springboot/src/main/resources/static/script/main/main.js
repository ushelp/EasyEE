/**
 * 主界面相关处理函数
 */
$(function(){
	
	// 系统日期显示
	if($("#showtime").length>0){
		setInterval(function(){
			var now=new Date();
			var yyyy=now.getFullYear();
			var MM=now.getMonth()+1;
			var dd=now.getDate();
			var HH=now.getHours();
			var mm=now.getMinutes();
			var ss=now.getSeconds();
			
			MM=MM<10?"0"+MM:MM;
			dd=dd<10?"0"+dd:dd;
			mm=mm<10?"0"+mm:mm;
			ss=ss<10?"0"+ss:ss;
			$("#showtime").html(yyyy+"年"+MM+"月"+dd+"日 "+HH+":"+mm+":"+ss);
		},1000);
	}
	
	
	
	/**
	 * 打开修改密码对话框
	 */
	$("#btnChangePwd").on("click", function() {
		//$("#dialogChangePwd").dialog({ closed: false});
		//禁用表单验证，清除上一次弹出后的验证信息
		uiEx.disableValidate("#formChangePwd");
		uiEx.openDialog("#dialogChangePwd");
	});

	/**
	 * 提交密码修改信息
	 */
	$("#submitChangPwd").on("click", function() {
		uiEx.submitAjax("#formChangePwd", function(data) {
			data = eval("(" + data + ")");
			if (data.statusCode==200) {
				//uiEx.alert('密码修改成功！',"info");
				uiEx.msg(data.msg);
				uiEx.closeDialog("#dialogChangePwd");
			}else{
				//uiEx.alert(data.msg,"warning"); 
			}
		});

	});
	
	//请求菜单树信息
	/* $("#menu").initTree({
		data:[${menuJSON}],
		//url : "json/menuTree.json.js", 
	}); */
	
	//普通树菜单初始化
	uiEx.initTree(
			"#menu",  //树菜单selector
			"#tabs",  //打开树菜单url的tabSelector
	        //其他树参数
			{
				expandChilds: true, //点击父菜单，展开子菜单
				data: EasyEE.menuTreeJson
		       //url : "json/menuTree.json.js", 
			}
		);
	
	
	//为指定选项卡列表添加右键菜单
	uiEx.addTabsContextMenu("#tabs","#tabsMenu");
	//自动打开指定ID的菜单 
	//uiEx.openMenuById("#menu","#tabs",[111,112]);
	//uiEx.openMenuById("#menu","#tabs",112);
	//uiEx.openMenuByText("#menu","#tabs",["CRUD rowEidt","CRUD cellEidt"]);
//	uiEx.openMenuByText("#menu","#tabs","CRUD rowDetailEdit");
	
	/**
	 * easyui主题切换下拉菜单
	 */
	$("#themeCombobox")
			.combobox(
					{
						editable : false,
						panelHeight : "auto",
						valueField : "value",
						textField : 'text',
						method : 'get',
						url : EasyEE.basePath
								+ "staticresources/easyee/json/easyui.theme.combobox.json",
						/*
						 * "data": [{ "value":"default", "text":"default",
						 * "selected":true },{ "value":"black", "text":"black"
						 * },{ "value":"bootstrap", "text":"bootstrap" },{
						 * "value":"gray", "text":"gray" },{ "value":"metro",
						 * "text":"metro" }],
						 */
						onSelect : function(selObj) {
							
							
							document.getElementById("themeLink").href = EasyEE.basePath
									+ "staticresources/easyui/themes/"
									+ selObj.value
									+ "/easyui.css";
							
							$.cookie('ui_theme', selObj.value, { expires: 365, path: '/' });
						},
						// 加载成功后设置默认值
						onLoadSuccess : function() {
							var defaultTheme = "metro-blue";
							
							var theme=$.cookie('ui_theme');
							if(theme){
								defaultTheme = theme;
							}
							
							
							 $("#themeCombobox").combobox("setValue",
							 defaultTheme);
							 document.getElementById("themeLink").href=EasyEE.basePath+"staticresources/easyui/themes/"+defaultTheme+"/easyui.css";
						}
					});
	
})