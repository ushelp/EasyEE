1. 使用easyuiex/easy.jquery.edatagrid.js替代jquery.edatagrid.js
2. 修改了源码，为EasyUI Datagrid 的 Field 增加了支持子属性功能，如dept.dname
	修改版本：EasyUI 1.4.3
	修改代码行：10361
	修改内容：
	   var col=$(_78d).datagrid("getColumnOption",_793);
	   if(col){
		var _794=_791[_793];
		//新增
		try{
			if(_793.indexOf(".")!=-1){
				_794=eval("(_791['" + _793.replace(/\./g, "']['") + "'])");
			}
		}catch(e){}
		//新增
