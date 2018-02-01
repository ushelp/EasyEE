/**
 * EasyUIEx
 * 
 * Version 2.2.9
 * 
 * http://easyproject.cn https://github.com/ushelp
 * 
 * Copyright 2014 Ray [ inthinkcolor@gmail.com ]
 * 
 * Dependencies: jQuery EasyUI
 * 
 */
(function() {
	var _uiEx = window.uiEx;

	/**
	 * 自定义命名空间，EasyUI扩展对象
	 */
	var uiEx = {
		/*
		 * ################# 消息内容和控制部分
		 */
		showRowEditMsg : false, // 是否在行编辑完后提示msg
		showRowAddMsg : false, // 是否在行添加完后提示msg
		showRowDeleteMsg : true, // 是否在行删除完后提示msg
		/*
		 * ################# 自定义消息框的默认参数
		 */
		msgDefaults : {
			timeout : 4000,
			showType : 'slide' // null、slide、fade、show。默认是 slide。
		// width:250,
		// height:100,

		},
		/*
		 * ################# 数据网格默认参数
		 */
		dataGridDefaults : {
			rownumbers : true, // 显示行号
			fitColumns : true, // 自动扩展或收缩列的大小以适应网格宽度和防止水平滚动条
			singleSelect : true, // 单选
			pagination : true, // 显示分页
			method : "post", // 提交方式
			striped : true
		// 表格条纹，奇偶行颜色交替
		},
		/*
		 * ################# 数据网格详细视图默认参数
		 */
		detailDataGridDefaults : {
			rownumbers : true, // 显示行号
			fitColumns : true, // 自动扩展或收缩列的大小以适应网格宽度和防止水平滚动条
			singleSelect : true, // 单选
			pagination : true, // 显示分页
			method : "post", // 提交方式
			striped : true, // 表格条纹，奇偶行颜色交替
			// 返回行明细内容的格式化函数
			detailFormatter : function(index, row) {
				return '<div class="ddv"></div>';
			}
		},
		/*
		 * ################# url中使用{expression}表达式，解析时用到的正则
		 */
		expReg : /\{([\s\S]+?)\}/g,
		/*
		 * ################# 验证提示消息
		 */
		msg : {}
	};

	/**
	 * 保存新Tab打开前的所有Dialog，关闭Tab时，自动销毁多余的Dialog
	 */
	uiEx.globalDialogMap = {};
	/**
	 * 保存新Tab打开前的所有ContextMenu(easyui-menu)，关闭Tab时，自动销毁多余的ContextMenu
	 */
	uiEx.globalContextMenuMap = {};

	/*
	 * ################# 内容变量，缓存部分
	 */
	/**
	 * 保存所有行编辑DataGrid的最后一次编辑行的行索引
	 */
	var dgLastEditIndex = {},
	/**
	 * 保存所有行编辑DataGrid的最后一次编辑行的类型：add、edit、editcell
	 */
	dgLastEditType = {},
	/**
	 * 保存所有行编辑DataGrid的最后一次编辑行的数据
	 */
	dgLastEditData = {},
	/**
	 * 保存所有最后一次列编辑的字段名
	 */
	dgLastCellField = {},
	/**
	 * 保存所有DataGrid的类型：datagrid、edatagrid、detaildatagrid
	 */
	dgType = {},
	/**
	 * 保存所有DataGrid的Header菜单
	 */
	dgHeaderMenu = {},
	/**
	 * 保存所有带复选框树加载的数据
	 */
	treeChkData = {};

	/*
	 * ################# 内部函数
	 */
	var
	/**
	 * EasyUIEx内部使用，点击行编辑时的操作封装
	 * 
	 * @param dgId
	 *            to edit datagrid id
	 * @param dg
	 *            to edit datagrid jquery object
	 * @param rowIndex
	 *            to edit row index
	 * @param rowData
	 *            to edit row data
	 */
	toEdit = function toEdit(dgId, dg, rowIndex, rowData) {
		if (endEditing(dg)) {
			dg.datagrid('selectRow', rowIndex).datagrid("beginEdit", rowIndex);
			dgLastEditIndex[dgId] = rowIndex;
			dgLastEditType[dgId] = "edit";
			var rowDataClone = {};
			$.extend(true, rowDataClone, rowData);
			dgLastEditData[dgId] = rowDataClone;
		} else {
			dg.datagrid("selectRow", rowIndex);
		}
	},
	/**
	 * 行编辑DataGrid最后一行编辑提交
	 * 
	 * @param datagridSelector
	 *            datagrid选择器或对象
	 * @param showMsg
	 *            是否显示提示信息
	 */
	endEditing = function(datagridSelector, showMsg) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		var lastEditIndex = dgLastEditIndex[dgId];
		if (lastEditIndex == undefined) {
			return true;
		}
		var type = dgLastEditType[dgId];
		if (dg.datagrid('validateRow', lastEditIndex)) {
			dg.datagrid("endEdit", lastEditIndex);

			// 行编辑状态监测，如果没有修改行数据，则不提交更新请求，减少不必要的请求
			if (jsonEquals(dg.datagrid('getRows')[lastEditIndex],
					dgLastEditData[dgId])) {
				uiEx.rowCancelEdit(datagridSelector);
				clearLast(dgId);
				return true;
			}

			if (type == "editcell") {
				var field = dgLastCellField[dgId];

				// 列编辑状态监测，如果没有修改行数据，则不提交更新请求，减少不必要的请求
				if (jsonEquals(
						dg.datagrid('getRows', lastEditIndex)[lastEditIndex][field],
						dgLastEditData[dgId])) {
					uiEx.rowCancelEdit(datagridSelector);
					clearLast(dgId);
					return true;
				}

			}

			// 执行更新请求
			var url = dg.datagrid("options").updateUrl;
			var success = uiEx.rowEditSuccessMsg;
			var failure = uiEx.rowEditFailureMsg;
			var method = dg.datagrid("options").method;

			if (type == "add") {
				if (showMsg == undefined) {
					showMsg = uiEx.showRowAddMsg;
				}
				url = dg.datagrid("options").saveUrl;
				success = uiEx.rowAddSuccessMsg;
				failure = uiEx.rowAddFailureMsg;
			} else if (type == "edit") {
				if (showMsg == undefined) {
					showMsg = uiEx.showRowEditMsg;
				}
			}

			var opts = dg.datagrid("options");
			var row = dg.datagrid('getRows')[lastEditIndex];
			var row2 = {};
			if (opts.sendRowDataPrefix) {
				$.each(row, function(i, v) {
					row2[opts.sendRowDataPrefix + i] = row[i];
				});
			} else {
				row2 = row;
			}
			// 同时提交分页参数
			if (dg.datagrid("options")) {
				if (dg.datagrid("options").pageSize) {
					row2.rows = dg.datagrid("options").pageSize;
				}
				if (dg.datagrid("options").pageNumber) {
					row2.page = dg.datagrid("options").pageNumber;
				}
			}
			$.ajax({
				url : url, // 请求的URL
				type : method, // 请求方式(POST、GET），默认GET
				// 发送到服务器端的数据，也可以{key1: 'value1', key2: 'value2'}
				data : row2,
				error : function() {
					if (showMsg) {
						uiEx.msg(failure);
					}
				},
				success : function(data) {
					if (showMsg) {
						uiEx.msg(success);
					}
					dg.datagrid('updateRow', {
						index : lastEditIndex,
						row : data.rowData
					});
				}
			});

			clearLast(dgId);

			return true;
		} else {
			return false;
		}
	},
	/**
	 * 内部调用函数：为指定DataGrid创建列头右键菜单
	 * 
	 * @datagridSelector DataGrid选择器或对象
	 * @return 菜单Menu对象
	 */
	createColumnMenu = function(datagridSelector) {
		var cmenu = $("<div/>").appendTo("body");
		cmenu.menu({
			onClick : function(item) {
				if (item.iconCls == "icon-ok") {
					$(datagridSelector).datagrid("hideColumn", item.name);
					cmenu.menu("setIcon", {
						target : item.target,
						iconCls : 'icon-empty'
					});
				} else {
					$(datagridSelector).datagrid("showColumn", item.name);
					cmenu.menu("setIcon", {
						target : item.target,
						iconCls : "icon-ok"
					});
				}
			}
		});
		var fields = $(datagridSelector).datagrid("getColumnFields");
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			var col = $(datagridSelector).datagrid("getColumnOption", field);
			cmenu.menu("appendItem", {
				text : col.title,
				name : field,
				iconCls : 'icon-ok'
			});
		}
		return cmenu;
	},
	/**
	 * 清除最后数据，加载数据网格时，撤销或操作完成后进行清理
	 * 
	 * @param dgId
	 *            datagrid id
	 */
	clearLast = function(dgId) {
		dgLastEditIndex[dgId] = undefined;
		dgLastEditData[dgId] = undefined;
		dgLastEditType[dgId] = undefined;
		dgLastCellField[dgId] = undefined;
	},
	/**
	 * 判断两个JSON对象字面值是否相等（不包含函数属性！）
	 * 
	 * @param data
	 *            json对象1，不包含函数属性！
	 * @param data2
	 *            json对象2，不包含函数属性！
	 */
	jsonEquals = function(data, data2) {
		var obj = false;
		var obj2 = false;
		if ((typeof data != "string") && (typeof data != "number")
				&& (typeof data != "boolean")) {
			for ( var i in data) {
				obj = true;
				break;
			}
			for ( var i in data2) {
				obj2 = true;
				break;
			}
		}
		if (obj && obj2) {
			for ( var i in data) {
				if (!jsonEquals(data[i], data2[i])) {
					return false;
				}
			}
			return true;
		} else {
			return (data + "") == (data2 + "");
		}
	},
	/**
	 * 为dataGrid添加右键菜单 showContextMenu:true onRowContextMenu事件处理
	 * 必须在初始化treegrid时设置menuSelector参数指定菜单选择器
	 * menuSelector:"#sysMenuRightContextMenu",
	 * 
	 * @param e
	 *            事件对象
	 * @param index
	 *            row Index
	 * @param row
	 *            row对象
	 * 
	 */
	showDatagridContextMenu = function(e, index, row) {
		e.preventDefault();
		if (index != -1) {
			$(this).datagrid('selectRow', index);
			$($(this).datagrid('options').menuSelector).menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	},
	/**
	 * 为treeGrid添加右键菜单 showContextMenu:true onContextMenu事件处理
	 * 必须在初始化treegrid时设置menuSelector参数指定菜单选择器
	 * menuSelector:"#sysMenuRightContextMenu",
	 * 
	 * @param e
	 *            事件对象
	 * @param row
	 *            row对象
	 * 
	 */
	showTreegridContextMenu = function(e, row) {
		e.preventDefault();
		$(this).treegrid('select', row.id);
		$($(this).treegrid('options').menuSelector).menu('show', {
			left : e.pageX,
			top : e.pageY
		});
	},
	/**
	 * 表头添加右键菜单，可选择显示的列 showHeaderContextMenu :true onHeaderContextMenu事件处理
	 * 
	 * @param e
	 *            事件对象
	 * @param field
	 *            列字段
	 */
	showHeaderMenu = function(e, field) {
		e.preventDefault();
		var datagridSelector = this;
		var dgId = $(this).attr("id");

		var cmenu = dgHeaderMenu[dgId];
		if (cmenu == undefined) {
			cmenu = createColumnMenu(datagridSelector);
			dgHeaderMenu[dgId] = cmenu;
		}
		cmenu.menu("show", {
			left : e.pageX,
			top : e.pageY
		});
	},
	/**
	 * 在DataGrid单击时实现行编辑，可以代替edatagrid实现带行编辑的datagrid clickRowEdit:true
	 * onClickRow事件处理
	 * 
	 * @param rowIndex
	 *            单击的行索引
	 * @param rowData
	 *            可选；单击的行数据
	 */
	clickRowEdit = function(rowIndex, rowData) {
		var dg = $(this);
		var dgId = dg.attr("id");
		var lastEditIndex = dgLastEditIndex[dgId];
		if (lastEditIndex != rowIndex) {
			toEdit(dgId, dg, rowIndex, rowData);
		}
	},
	/**
	 * 
	 * datagrid：onClickCell事件处理注册函数，能在DataGrid的onClickCell时实现列编辑
	 * 在数据网格初始化时通过clickCellEdit:true;属性传入 clickCellEdit: true, //单元格编辑
	 * 
	 * @param rowIndex
	 *            单击的行索引
	 * @param field
	 *            单击的列字段
	 * @param value
	 *            单击的列数据
	 */
	clickCellEdit = function(rowIndex, field, value) {
		var dg = $(this);
		var dgId = dg.attr("id");
		if (endEditing(dg)) {
			dg.datagrid('selectRow', rowIndex).datagrid('editCell', {
				index : rowIndex,
				field : field
			});
			dgLastEditIndex[dgId] = rowIndex;
			dgLastEditType[dgId] = "editcell";
			dgLastEditData[dgId] = value;
			dgLastCellField[dgId] = field;
		}
	},
	// 初始化datagrid右键菜单参数
	initDgContextMenu = function(params, p) {
		// 右键菜单显示，showContextMenu配合menuSelector
		if (params.showContextMenu) {
			// 如果存在用户自定义的操作行事件，则保留自定义操作
			if (params.onRowContextMenu) {
				var userEvent = params.onRowContextMenu;
				p.onRowContextMenu = function(e, index, row) {
					userEvent(e, index, row);
					showDatagridContextMenu(e, index, row);
				}
			} else {
				p.onRowContextMenu = showDatagridContextMenu;
			}
		}
	},
	// 初始化teeegrid右键菜单参数
	initTgContextMenu = function(params, p) {
		// 右键菜单显示，showContextMenu配合menuSelector
		if (params.showContextMenu) {
			// 如果存在用户自定义的操作行事件，则保留自定义操作
			if (params.onContextMenu) {
				var userEvent = params.onContextMenu;
				p.onContextMenu = function(e, row) {
					userEvent(e, row);
					showTreegridContextMenu(e, row);
				}
			} else {
				p.onContextMenu = showTreegridContextMenu;
			}
		}
	},
	// 初始化datagrid表头右键菜单参数，可选择显示的列
	initDgHeaderContextMenu = function(params, p) {
		if (params.showHeaderContextMenu) {
			// 如果存在用户自定义的操作行事件，则保留自定义操作
			if (params.onHeaderContextMenu) {
				var userEvent = params.onHeaderContextMenu;
				p.onHeaderContextMenu = function(e, field) {
					userEvent(e, field);
					showHeaderMenu(e, field);
				}
			} else {
				p.onHeaderContextMenu = showHeaderMenu;
			}
		}
	},
	// 2.2.4+ 销毁 Tab 内元素
	destoryTab=function(title){
		
		// 移除多余的window-shadow
		$('.window-shadow:gt(0)').remove();
		
		if (uiEx.globalDialogMap[title]) {

			var globalDialogs = "#"
					+ uiEx.globalDialogMap[title].join("#") + "#";
			$(".easyui-dialog").each(
					function() {
						// 检测是否是新创建的dialog
						// 不包含在uiEx.globalDialogMap[title]中，则代表tab新创建的dialog，则销毁
						if (globalDialogs.indexOf("#" + this.id
								+ "#") == -1) {
							
							var dialogTemp=$('#' + this.id);
							dialogTemp.dialog({
								closed : true
							})
							dialogTemp.dialog('destroy', true);
						}
					})
			// 清空globalDialogMap
			uiEx.globalDialogMap[title] = new Array();
		}
		
		if (uiEx.globalContextMenuMap[title]) {

			var globalContextMenuss = "#"
					+ uiEx.globalContextMenuMap[title].join("#") + "#";
			// 排除 tabsMenu 公用的菜单
			$(".easyui-menu").not("#tabsMenu").each(
					function() {
							// 检测是否是新创建的menu
							// 不包含在uiEx.globalContextMenuMap[title]中，则代表tab新创建的menu，则销毁
							if (globalContextMenuss.indexOf("#" + this.id
									+ "#") == -1) {
								var contextMenuTemp=$('#' + this.id);
								contextMenuTemp.menu('destroy');
							}
					})
			// 清空globalContextMenuMap
			uiEx.globalContextMenuMap[title] = new Array();
		}
		
	}
	;

	/*
	 * ################# EasyUIEx扩展函数
	 */
	/*
	 * 列编辑扩展
	 */
	$.extend($.fn.datagrid.methods, {
		editCell : function(jq, param) {
			return jq.each(function() {
				var fields = $(this).datagrid('getColumnFields', true).concat(
						$(this).datagrid('getColumnFields'));
				for (var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field) {
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
				for (var i = 0; i < fields.length; i++) {
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		}
	});

	/*
	 * ################# EasyUIEx函数实现和注册
	 */
	/*
	 * ################# 窗口对话框部分
	 */
	/**
	 * 操作提示
	 * 
	 * @param msg
	 *            消息内容
	 * @param icon
	 *            消息图标类型：error、info、question、warning
	 * @param callback
	 *            回调函数
	 * 
	 */
	uiEx.alert = function(msg, icon, callback) {
		$.messager.alert(this.alertTitle, msg, icon, callback);
	}

	/**
	 * 确认提示
	 * 
	 * @param msg
	 *            消息内容
	 * @param callback
	 *            回调函数
	 */
	uiEx.confirm = function(msg, callback) {
		$.messager.confirm(this.confirmTitle, msg, callback);
	}
	/**
	 * 输入提示
	 * 
	 * @param msg
	 *            消息内容
	 * @param callback
	 *            回调函数
	 */
	uiEx.prompt = function(msg, callback) {
		$.messager.prompt(this.promptTitle, msg, callback);
	}
	/**
	 * 消息提示
	 * 
	 * @param msg
	 *            消息内容
	 * @param position
	 *            消息位置：topLeft, topCenter, topRight, centerLeft, center,
	 *            centerRight, bottomLeft, bottomCenter, bottomRight;
	 *            默认为：bottomRight（右下角）
	 * @param params
	 *            msg消息框参数
	 */
	uiEx.msg = function(msg, position, params) {
		var style; // bottomright or others
		if (position) {
			position = position.toLowerCase();
			if (position == "topleft") {
				style = {
					right : '',
					left : 0,
					top : document.body.scrollTop
							+ document.documentElement.scrollTop,
					bottom : ''
				};
			} else if (position == "topcenter") {
				style = {
					right : '',
					top : document.body.scrollTop
							+ document.documentElement.scrollTop,
					bottom : ''
				};
			} else if (position == "topright") {
				style = {
					left : '',
					right : 0,
					top : document.body.scrollTop
							+ document.documentElement.scrollTop,
					bottom : ''
				};
			} else if (position == "centerleft") {
				style = {
					left : 0,
					right : '',
					bottom : ''
				}
			} else if (position == "center") {
				style = {
					right : '',
					bottom : ''
				}
			} else if (position == "centerright") {
				style = {
					left : '',
					right : 0,
					bottom : ''
				}
			} else if (position == "bottomleft") {
				style = {
					left : 0,
					right : '',
					top : '',
					bottom : -document.body.scrollTop
							- document.documentElement.scrollTop
				}
			} else if (position == "bottomcenter") {
				style = {
					right : '',
					top : '',
					bottom : -document.body.scrollTop
							- document.documentElement.scrollTop
				}
			}
		}

		var p = {};

		if (!params) {
			params = {};
		}

		params.style = style;
		params.msg = msg;
		params.title = this.msgTitle, $.extend(p, uiEx.msgDefaults, params);
		$.messager.show(p);
	}

	/**
	 * 显示指定对话框
	 * 
	 * @param dialogSelector
	 *            dialog选择器
	 * @param title
	 *            对话框标题
	 * @return dialog对象
	 */
	uiEx.openDialog = function(dialogSelector, title) {
		if (!title) {
			title = $(dialogSelector).panel('options').title;
		}
		return $(dialogSelector).dialog({
			cache : false,
			closed : false,
			title : title
		});
	}

	/**
	 * 显示指定对话框
	 * 
	 * @param dialogSelector
	 *            dialog选择器
	 * @param href
	 *            请求内容的href
	 * @param title
	 *            对话框标题
	 * @return dialog对象
	 */
	uiEx.openHrefDialog = function(dialogSelector, href, title) {
		if (!title) {
			title = $(dialogSelector).panel('options').title;
		}
		return $(dialogSelector).dialog({
			href : href,
			cache : false,
			closed : false,
			title : title
		});
	}

	/**
	 * 关闭指定对话框
	 * 
	 * @param dialogSelector
	 *            dialog选择器
	 * @return dialog对象
	 */
	uiEx.closeDialog = function(dialogSelector) {
		return $(dialogSelector).dialog({
			closed : true
		});
	}

	/*
	 * ################# 表单处理部分
	 */
	/**
	 * 清除指定id的form表单信息
	 * 
	 * @param selector
	 *            表单选择器
	 * @return form对象
	 */
	uiEx.clearForm = function(selector) {
		return this.disableValidate(selector).form('clear');
	}
	/**
	 * 重置reset指定id的form表单信息
	 * 
	 * @param selector
	 *            表单选择器
	 * @return form对象
	 */
	uiEx.resetForm = function(selector) {
		return this.disableValidate(selector).form('reset');
	}
	/**
	 * 代替form("load", data);为表单填充数据 可以指定表单名前缀，将数据填充到指定前缀的属性中去
	 * 如将data中的name属性值填充到user.name中
	 * 
	 * @param selector
	 *            表单选择器
	 * @param data
	 *            表单数据
	 * @param prefix
	 *            表单名称前缀
	 * @return form对象
	 */
	uiEx.loadForm = function(selector, data, prefix) {
		if(!prefix){
			prefix="";
		}
		var data2 = {};
		$.each(data, function(i, v) {
			data2[prefix + i] = v;
		});
		$(selector).form("load", data2);
	}
	/**
	 * 开启表单验证
	 * 
	 * @param selector
	 *            表单选择器
	 * @return form对象
	 */
	uiEx.enableValidate = function(selector) {
		return $(selector).form('enableValidation');
	}
	/**
	 * 禁用表单验证
	 * 
	 * @param selector
	 *            表单选择器
	 * @return form对象
	 */
	uiEx.disableValidate = function(selector) {
		return $(selector).form('disableValidation');
	}

	/**
	 * 对表单进行验证
	 * 
	 * @param selector
	 *            表单选择器
	 * @return form对象
	 */
	uiEx.validate = function(selector) {
		return this.enableValidate(selector).form('validate');
	}
	/**
	 * 将form表单信息格式化为JSON返回
	 * 
	 * @parama formSelector form选择器
	 * @return form表单序列化后的JSON对象
	 */
	uiEx.serializeJSON = function(formSelector) {
		var o = {};
		var a = $(formSelector).serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});

		return o;
	};
	/**
	 * 普通表单提交
	 * 
	 * @param formSelector
	 *            表单选择器
	 * @param params
	 *            可选; form表单额外提交的参数
	 * @param noValidate
	 *            可选; 是否验证; boolean; 默认为true
	 */
	uiEx.submitForm = function(formSelector, params, noValidate) {
		// 动态添加参数
		var addDoms = [];
		if (params) {
			$.each(params, function(name, value) {
				var newDom = $('<input type="text" name="' + name + '" value="'
						+ value + '"/>');
				$(formSelector).append(newDom);
				addDoms.push(newDom);
			});
		}

		// false不验证表单，直接提交
		if (noValidate == false) {
			$(formSelector)[0].submit();
		} else {
			if (this.validate(formSelector)) {
				$(formSelector)[0].submit();
			}
		}
		// 移除动态添加的参数
		$.each(addDoms, function(i, dom) {
			$(dom).remove();
		})
	}
	/**
	 * 以Ajax进行带表单验证的表单提交，内部封装了一个`xRequestedWith=XMLHttpRequest`参数；
	 * 会随表单一同提交到服务器，帮助开发者在服务器端通过`xRequestedWith参数`来判断是否是Ajax请求。
	 * 
	 * @param formSelector
	 *            表单选择器
	 * @param callback
	 *            AJAX请求后的回调处理函数
	 * @param params
	 *            可选; form表单额外提交的参数
	 * @param noValidate
	 *            可选; 是否验证; boolean; 默认为true
	 */
	uiEx.submitAjax = function(formSelector, callback, params, noValidate) {
		uiEx.submitURLAjax(formSelector, null, callback, params, noValidate);
	}

	/**
	 * 将表单以Ajax提交到指定url，内部封装了一个`xRequestedWith=XMLHttpRequest`参数；
	 * 会随表单一同提交到服务器，帮助开发者在服务器端通过`xRequestedWith参数`来判断是否是Ajax请求。
	 * 
	 * @param formSelector
	 *            表单选择器
	 * @param url
	 *            提交到的URL地址
	 * @param callback
	 *            AJAX请求后的回调处理函数
	 * @param params
	 *            可选; form表单额外提交的参数
	 * @param noValidate
	 *            可选; 是否验证; boolean; 默认为true
	 */
	uiEx.submitURLAjax = function(formSelector, url, callback, params,
			noValidate) {

		// 能够为form表单提交成功的succes事件注册一个系统全局的必须执行函数
		// 表单提交成功success事件处理函数，包含了一个系统全局的必须执行函数
		// uiEx.formSubmitSuccess类似于jQuery的全局ajaxSuccess函数
		var successCallback = function(data) {
			if (callback) {
				callback(data)
			}
			// 包含默认事件处理函数执行
			if (uiEx.formSubmitSuccess) {
				uiEx.formSubmitSuccess(data);
			}
		}

		if (url) {
			$(formSelector).form('submit', {
				url : url,
				onSubmit : function(param) {
					if (!param) {
						param = {};
					}
					if (params) {
						$.extend(param, params);
					}
					param.xRequestedWith = "XMLHttpRequest";
					if (noValidate == false) {
						return true;
					} else {
						return uiEx.validate(formSelector);
					}
				},
				success : successCallback,
				error : function() {
					uiEx.msg(uiEx.ajaxError, "bottomRight");
				}
			});
		} else {
			$(formSelector).form('submit', {
				onSubmit : function(param) {
					if (!param) {
						param = {};
					}
					if (params) {
						$.extend(param, params);
					}
					param.xRequestedWith = "XMLHttpRequest";
					if (noValidate == false) {
						return true;
					} else {
						return uiEx.validate(formSelector);
					}
				},
				success : successCallback,
				error : function() {
					uiEx.msg(uiEx.ajaxError, "bottomRight");
				}
			});
		}
	}

	/*
	 * ################# Tab 选项卡部分
	 */
	/**
	 * 为指定Tab添加选项卡，支持双击关闭
	 * 
	 * @parma tabSelector 选项卡
	 * @parma title 标题
	 * @parma url 地址
	 * @parma icon 可选；图标
	 * @parma isIframe 可选；boolean值；是否使用iframe方式引入，true为使用iframe方式引入，默认为href方式 #
	 *        EasyUI Tabs两种动态动态加载方式之间的区别： - 使用content(iframe框架)引入页面: content : '<iframe
	 *        scrolling="auto" frameborder="0" src="'+ url + '"
	 *        style="width:100%;height:100%;"></iframe>'; -
	 *        作为独立窗口存在，页面内容独立，与当前页面互不干扰 - 需要独立引入需要的JS和CSS资源 - 弹出的内容是在内部窗口内 -
	 *        使用href方法： href : url, - 内容片段加载，引入的内容和当前页面合并在一起 -
	 *        不需要引入页面已经引入的JS和CSS资源 - 引用的页面不能有body，否则加载的内容内部的JS文件文法执行 -
	 *        会显示html渲染解析的提示
	 * 
	 */
	uiEx.openTab = function(tabSelector, title, url, icon, isIframe) {
		var tab = $(tabSelector);
		if (tab.tabs('exists', title)) {
			tab.tabs('select', title);
		} else {
			// 当关闭tab时，检测globalDialogMap，自动destory销毁多出来的dialog，防止重复加载
			tab.tabs({
				onBeforeClose : function() {
					
					destoryTab(title);
					return true;
				}
			});

			var addParams = {
				title : title,
				closable : true,
				selected : true,
				iconCls : icon,
				onBeforeLoad : function() {
					$(".easyui-dialog").each(function() {
						// 记录打开当前tab时，已经存在的dialog
						// 当关闭tab时，自动destory销毁多出来的dialog，防止重复加载
						if (!uiEx.globalDialogMap[title]) {
							uiEx.globalDialogMap[title] = new Array();
						}
						uiEx.globalDialogMap[title].push(this.id);
					});
					
					$(".easyui-menu").each(function() {
						// 记录打开当前tab时，已经存在的menu
						// 当关闭tab时，自动destory销毁多出来的menu，防止重复加载
						if (!uiEx.globalContextMenuMap[title]) {
							uiEx.globalContextMenuMap[title] = new Array();
						}
						uiEx.globalContextMenuMap[title].push(this.id);
					});
					return true;
				}

			// ,tools:[{
			// iconCls:'icon-mini-refresh',
			// handler:function(){
			// alert('refresh');
			// }
			// }]
			};

			if (isIframe) {
				// content : content,
				var content = '<iframe scrolling="auto" frameborder="0" src="'
						+ url + '" style="width:100%;height:100%;"></iframe>';
				addParams["content"] = content;
			} else {
				// href : url,
				// href 不能有body，否则加载的内容内部的JS文件文法执行
				addParams["href"] = url;
			}

			tab.tabs('add', addParams);
			//		
		}
		/* 双击关闭TAB选项卡 */
		$(".tabs-inner").on("dblclick", function() {
			var subtitle = $(this).children(".tabs-closable").text();
			$('#tabs').tabs('close', subtitle);
		})
	}
	/**
	 * 刷新当前选项卡选中的Panel
	 * 
	 * @param tabSelector
	 *            选项卡选择器
	 */
	uiEx.reloadSelTab = function(tabSelector) {
		var tab=$(tabSelector).tabs("getSelected");
		
		destoryTab(tab.panel('options').title);
		
		tab.panel("refresh");
	}

	/**
	 * 根据菜单Text自动在指定Tab打开某个菜单
	 * 
	 * @param menuSelector
	 *            menu选择器
	 * @param tabSeelctor
	 *            选项卡选择器
	 * @param menuText
	 *            要打开的菜单文本，可以使用数组定义多个菜单文本
	 */
	uiEx.openMenuByText = function(menuSelector, tabSelector, menuText) {
		var menu = $(menuSelector);

		var menuOpen = function(txt) {
			var node = menu.tree("getNode", $(".tree-node:contains('" + txt
					+ "')")[0]);
			if (node) {
				uiEx.openTab(tabSelector, node.text, node.url, node.iconCls);
				menu.tree('select', node.target);
			}
		}

		if ($.isArray(menuText)) {
			for (var i = 0; i < menuText.length; i++) {
				menuOpen(menuText[i]);
			}
		} else {
			menuOpen(menuText);
		}

	}
	/**
	 * 根据菜单Id自动在指定Tab打开某个菜单
	 * 
	 * @param menuSelector
	 *            menu选择器
	 * @param tabSeelctor
	 *            选项卡选择器
	 * @param menuId
	 *            要打开的菜单id，可以使用数组定义多个菜单id
	 */
	uiEx.openMenuById = function(menuSelector, tabSelector, menuId) {
		var menu = $(menuSelector);

		var menuOpen = function(id) {
			var node = menu.tree('find', id);
			if (node) {
				uiEx.openTab(tabSelector, node.text, node.url, node.iconCls);
				menu.tree('select', node.target);
			}
		}

		if ($.isArray(menuId)) {
			for (var i = 0; i < menuId.length; i++) {
				menuOpen(menuId[i]);
			}
		} else {
			menuOpen(menuId);
		}
	}

	var contextMenuTab; // 当前显示右键菜单的Tab对象
	/**
	 * 绑定tabs的右键菜单，实现：关闭，关闭其他，关闭所有；关闭左侧标签页、关闭右侧标签页；刷新等菜单功能
	 * 
	 * @param tabSelector
	 *            tab选择器
	 * @param menuSelector
	 *            menu选择器；可选，默认之为#tabsMenu
	 * 
	 * 必须在页面定义右键菜单标签（菜单id、菜单项的name不能修改）： <%--
	 * ##################Tab选项卡的右键菜单，不能删除################## --%> <div
	 * id="tabsMenu" class="easyui-menu" style="width:120px;"> <div name="close"
	 * data-options="iconCls:'icon-close'">关闭标签</div> <div name="other"
	 * data-options="">关闭其他标签</div> <div name="all" data-options="">关闭所有标签</div>
	 * <div class="menu-sep"></div> <div name="closeRight">关闭右侧标签</div> <div
	 * name="closeLeft">关闭左侧标签</div> <div class="menu-sep"></div> <div
	 * name="refresh" data-options="iconCls:'icon-reload'">刷新标签</div> </div>
	 */
	uiEx.addTabsContextMenu = function(tabSelector, menuSelector) {
		// 添加右键菜单
		$(tabSelector).tabs({
			onContextMenu : function(e, title, index) {
				e.preventDefault();
				if (!menuSelector) {
					menuSelector = '#tabsMenu';
				}

				var tabMenu = $(menuSelector);
				// 当前右键的标签页
				contextMenuTab = $(this).tabs("getTab", index);
				// 当前选中的标签页
				// contextMenuTab=$(this).tabs("getSelected");

				if (contextMenuTab.panel("options").closable) {
					tabMenu.menu('enableItem', tabMenu.find("[name=close]"));
				} else {
					tabMenu.menu('disableItem', tabMenu.find("[name=close]"));
				}
				tabMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				}).data("tabTitle", title);
			}
		});

		// 处理tabs右键菜单的单击事件
		$("#tabsMenu").menu({
			onClick : function(item) {

				// 当前Tab
				// var curTab=tabs.tabs("getSelected");
				var curTab = contextMenuTab;

				// 所有tabs
				var tabs = $(tabSelector);

				// 当前打开右键菜单的tab的索引
				var curIndex = tabs.tabs('getTabIndex', curTab);

				if (curTab) {
					// 要关闭的tabs
					var closeTabs = [];

					if (item.name == "close") {
						closeTabs.push(curTab);
					} else if (item.name == "all") {
						$.each(tabs.tabs("tabs"), function(i, tab) {
							closeTabs.push(tab);
						});
					} else if (item.name == "other") {
						$.each(tabs.tabs("tabs"), function(i, tab) {
							if (tab != curTab) {
								closeTabs.push(tab);
							}
						});
					} else if (item.name == "closeRight") {
						$.each(tabs.tabs("tabs"), function(i, tab) {
							var tabIndex = tabs.tabs('getTabIndex', tab);
							if (tabIndex > curIndex) {
								closeTabs.push(tab);
							}
						});
					} else if (item.name == "closeLeft") {
						$.each(tabs.tabs("tabs"), function(i, tab) {
							var tabIndex = tabs.tabs('getTabIndex', tab);
							if (tabIndex < curIndex) {
								closeTabs.push(tab);
							}
						});
					} else if (item.name == "refresh") { // 刷新
						
						var title=curTab.panel("options").title;
						destoryTab(title);
						curTab.panel("refresh");
					}
					// 关闭需要关闭的选项卡
					for (var i = 0; i < closeTabs.length; i++) {
						var opt = closeTabs[i].panel("options");
						if (opt.closable) {
							tabs.tabs("close", opt.title);
						}
					}
				}

			}
		});
	}

	/*
	 * ################# DataGrid 数据表格部分
	 */

	/*
	 * ################# 数据网格初始化部分，包括datagrid，edatagrid，detaildatagrid
	 */
	/**
	 * DataGrid: datagrid初始化，包含了uiEx.dataGridDefaults默认参数
	 * 
	 * @param datagridSelector
	 *            datagrid选择器
	 * @param params
	 *            可选；datagrid初始化参数
	 */
	uiEx.initDatagrid = function(datagridSelector, params) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		clearLast(dgId);
		if (!params) {
			params = {};
		}

		var p = {

		};
		// 右键菜单显示，showContextMenu配合menuSelector
		initDgContextMenu(params, p);
		// 初始化datagrid表头右键菜单参数，可选择显示的列
		initDgHeaderContextMenu(params, p);
		// 在DataGrid单击时实现行编辑，可以代替edatagrid实现带行编辑的datagrid
		// clickRowEdit:true
		if (params.clickRowEdit) {
			// 如果存在用户自定义的单击行事件，则保留自定义操作
			if (params.onClickRow) {
				var userClickRow = params.onClickRow;
				p.onClickRow = function(rowIndex, rowData) {
					userClickRow(rowIndex, rowData);
					clickRowEdit(rowIndex, rowData);
				}
			} else {
				p.onClickRow = clickRowEdit;
			}
		}
		// 开启列编辑
		if (params.clickCellEdit) {
			// 如果存在用户自定义的单击行事件，则保留自定义操作
			if (params.onClickCell) {
				var userCellEdit = params.onCellEdit;
				p.onClickCell = function(rowIndex, field, value) {
					userCellEdit(rowIndex, field, value);
					clickCellEdit(rowIndex, field, value);
				}
			} else {
				p.onClickCell = clickCellEdit;
			}
		}
		$.extend(p, uiEx.dataGridDefaults, params);
		dg.datagrid(p);
		dgType[dgId] = "datagrid";
	};
	/**
	 * EditDataGrid: edatagrid初始化，包含了uiEx.dataGridDefaults默认参数
	 * 
	 * @param datagridSelector
	 *            datagrid选择器
	 * @param params
	 *            可选；datagrid初始化参数
	 */
	uiEx.initEdatagrid = function(datagridSelector, params) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		clearLast(dgId);
		if (!params) {
			params = {};
		}
		var p = {
			destroyMsg : {
				norecord : { // when no record is selected
					title : uiEx.alertTitle,
					msg : uiEx.deleteSelectedMsg
				},
				confirm : { // when select a row
					title : uiEx.confirmTitle,
					msg : uiEx.deleteConfirmMsg
				}
			}
		};

		// 右键菜单显示，showContextMenu配合menuSelector
		initDgContextMenu(params, p);
		// 表头添加右键菜单，可选择显示的列
		initDgHeaderContextMenu(params, p);
		// 在DataGrid单击时实现行编辑，可以代替edatagrid实现带行编辑的datagrid

		$.extend(p, uiEx.dataGridDefaults, params);
		dg.edatagrid(p);
		dgType[dgId] = "edatagrid";
	};
	/**
	 * DetailDataGrid: DetailDataGrid初始化，包含了detailDataGridDefaults参数的默认值
	 * 
	 * @param datagridSelector
	 *            datagrid选择器
	 * @param detailUrl
	 *            加载详细视图的url
	 * @param params
	 *            可选；其他参数，主要包括数据CRUD的url地址
	 */
	uiEx.initDetailDatagrid = function(datagridSelector, detailUrl, params) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		clearLast(dgId);
		if (!params) {
			params = {};
		}

		var p = {
			view : detailview,
			// 当展开一行时触发
			onExpandRow : function(index, row) {
				var ddv = $(this).datagrid("getRowDetail", index).find(
						"div.ddv");
				ddv.panel({
					border : false,
					cache : true,
					href : detailUrl + "?index=" + index,
					// 加载完url的内容后渲染视图
					onLoad : function() {
						dg.datagrid("fixDetailRowHeight", index);
						dg.datagrid("selectRow", index);
						dg.datagrid("getRowDetail", index).find("form").form(
								"load", row);
					}
				});
				// 固定明细行的高度
				$("#userDataGrid5").datagrid("fixDetailRowHeight", index);
			}
		};
		// 右键菜单显示，showContextMenu配合menuSelector
		initDgContextMenu(params, p);
		// 表头添加右键菜单，可选择显示的列
		initDgHeaderContextMenu(params, p);
		// 在DataGrid单击时实现行编辑，可以代替edatagrid实现带行编辑的datagrid
		$.extend(p, uiEx.detailDataGridDefaults, params);
		dg.datagrid(p);
		dgType[dgId] = "detaildatagrid";
	};

	/*
	 * ################# 数据网格增删改操作
	 */
	/**
	 * DetailDataGrid: DetailDataGrid添加行
	 * 
	 * @param datagridSelector
	 *            datagrid选择器
	 */
	uiEx.detailRowAdd = function(datagridSelector) {
		var dg = $(datagridSelector);
		dg.datagrid('appendRow', {
			isNewRecord : true
		});
		var index = dg.datagrid('getRows').length - 1;
		dg.datagrid('expandRow', index);
		dg.datagrid('selectRow', index);
	}

	/**
	 * DetailDataGrid: DetailDataGrid编辑保存
	 * 
	 * @param datagridSelector
	 *            datagrid选择器
	 * @param index
	 *            编辑行索引，加载详细数据时，index值会提交到服务器，直接从服务器端请求参数获得传入该函数
	 *            例如，JSP写法：uiEx.detailRowSave('#userDataGrid',${param.index})
	 * @param showMsg
	 *            可选；是否显示提示信息
	 */
	uiEx.detailRowSave = function(datagridSelector, index, showMsg) {
		var dg = $(datagridSelector);
		var row = dg.datagrid('getRows')[index];
		var saveUrl = dg.datagrid("options").saveUrl;
		var updateUrl = dg.datagrid("options").updateUrl;

		var url = row.isNewRecord ? saveUrl : updateUrl;
		url = url.replace(uiEx.expReg, function($0, $1) {
			return row[$1];
		});

		var successMsg = uiEx.rowEditSuccessMsg;
		var failureMsg = uiEx.rowEditFailureMsg;
		if (row.isNewRecord) {
			if (showMsg == undefined) {
				showMsg = uiEx.showRowAddMsg;
			}
			successMsg = uiEx.rowAddSuccessMsg;
			failureMsg = uiEx.rowAddFailureMsg;
		} else {
			if (showMsg == undefined) {
				showMsg = uiEx.showRowEditMsg;
			}
		}

		dg.datagrid("getRowDetail", index).find("form").form(
				"submit",
				{
					url : url,
					onSubmit : function() {
						return $(this).form('validate');
					},
					success : function(data) {
						data = eval('(' + data + ')');
						data.isNewRecord = false;
						dg.datagrid("collapseRow", index);
						dg.datagrid("updateRow", {
							index : index,
							row : dg.datagrid("getRowDetail", index).find(
									"form").serializeJSON()
						});
						if (showMsg) {
							uiEx.msg(successMsg);
						}
					}
				});
	}

	/**
	 * DetailDataGrid: DetailDataGrid取消编辑或添加
	 * 
	 * @param datagridSelector
	 *            datagrid选择器
	 * @param index
	 *            编辑行索引，加载详细数据时，index值会提交到服务器，直接从服务器端请求参数获得传入该函数
	 *            例如，JSP写法：uiEx.detailRowCancel('#userDataGrid',${param.index})
	 */
	uiEx.detailRowCancel = function(datagridSelector, index) {
		var dg = $(datagridSelector);
		var row = dg.datagrid("getRows")[index];
		if (row.isNewRecord) {
			dg.datagrid("deleteRow", index);
		} else {
			dg.datagrid("collapseRow", index);
		}
	}

	/**
	 * datagrid：为行编辑DataGrid追加新行
	 * 
	 * @param datagridSelector
	 *            DataGrid选择器
	 * @param rowParam
	 *            可选；新数据行的数据参数
	 */
	uiEx.rowAdd = function(datagridSelector, rowParam) {
		if (rowParam == undefined) {
			rowParam = {};
		}
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		if (endEditing(datagridSelector)) {
			dg.datagrid("appendRow", rowParam);
			var editIndex = dg.datagrid("getRows").length - 1;
			dg.datagrid("selectRow", editIndex)
					.datagrid("beginEdit", editIndex);
			dgLastEditIndex[dgId] = editIndex;
			dgLastEditType[dgId] = "add";
		}
	}
	/**
	 * datagrid：为指定DataGrid启用行编辑，会引起一次新加载
	 * 
	 * @param datagridSelector
	 *            datagrid选择器或对象
	 */
	uiEx.rowEdit = function(datagridSelector) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		dg.datagrid({
			onClickRow : function(rowIndex, rowData) {
				var lastEditIndex = dgLastEditIndex[dgId];
				if (lastEditIndex != rowIndex) {
					toEdit(dgId, dg, rowIndex, rowData);
				}
			}
		});
	}

	/**
	 * datagird、edatagrid、detaildatagrid、treegrid:删除选中行
	 * 
	 * @param datagridSelector
	 *            DataGrid选择器
	 * @param showMsg
	 *            可选；boolean值，是否显示提示消息，会覆盖默认的全局uiEx.showRowDeleteMsg参数值
	 * @param reloadDataGrid
	 *            可选；是否reload重新加载 DataGrid，默认为false
	 * @param successKey
	 *            可选；字符串值，执行成功返回的标记key，值必须和successValue相同才代表删除成功
	 * @param successValue
	 *            可选；字符串值，执行成功返回的标记value
	 * @param callback
	 *            可选；执行删除成功后的回调函数，参数为服务器端返回的数据
	 *  
	 *  - demo：
	 *  dg.rowDelete(true, false, "statusCode", "200");
	 */
	uiEx.rowDelete = function(datagridSelector, showMsg, reloadDataGrid,
			successKey, successValue, callback) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		var delRows = [];
		var selRows = dg.datagrid("getSelections");
		if (selRows.length == 0) {
			return;
		} else {
			$.each(selRows, function(i, row) {
				delRows.push(dg.datagrid("getRowIndex", row));
			});

		}

		delRows.sort(function(i, j) {
			return j - i;
		});

		if (showMsg == undefined) {
			showMsg = uiEx.showRowDeleteMsg;
		}

		uiEx
				.confirm(
						uiEx.deleteConfirmMsg,
						function(res) {
							if (res) {

								// 执行删除请求
								var url = dg.datagrid("options").destroyUrl;
								var method = dg.datagrid("options").method;

								// 开始多行提交删除
								if (dg.datagrid("options").mutipleDelete) {
									var ps = dg.datagrid("options").mutipleDeleteProperty;
									var postdata = {};
									if ($.isArray(ps)) {
										$.each(ps, function(i, v) {
											postdata[v] = new Array();
											$.each(selRows, function(j, row) {
												postdata[v].push(row[v]);
											});
										});
									} else {
										postdata[ps] = new Array();
										$.each(selRows, function(j, row) {
											postdata[ps].push(row[ps]);
										});
									}
									// 同时提交分页参数
									if (dg.datagrid("options")) {
										if (dg.datagrid("options").pageSize) {
											postdata.rows = dg
													.datagrid("options").pageSize;
										}
										if (dg.datagrid("options").pageNumber) {
											postdata.page = dg
													.datagrid("options").pageNumber;
										}
									}
									postdata = $.param(postdata, true);

									$
											.ajax({
												url : url, // 请求的URL
												type : method, // 请求方式(POST、GET），默认GET
												// 发送到服务器端的数据，也可以{key1:
												// 'value1', key2: 'value2'}
												data : postdata,
												error : function() {
													if (showMsg) {
														uiEx
																.msg(uiEx.rowDeleteFailureMsg);
													}
												},
												success : function(data) {
													if (showMsg) {

														if (successKey) {
															if (data[successKey]
																	&& data[successKey] == successValue) {
																$
																		.each(
																				delRows,
																				function(
																						i,
																						rowIndex) {
																					dg
																							.datagrid(
																									'deleteRow',
																									rowIndex);
																				});
																clearLast(dgId);
																uiEx
																		.msg(uiEx.rowDeleteSuccessMsg);
															} else {
																uiEx
																		.msg(uiEx.rowDeleteFailureMsg);
															}
														} else {
															// dg.datagrid('cancelEdit',
															// lastEditIndex).datagrid(
															// 'deleteRow',
															// lastEditIndex);
															$
																	.each(
																			delRows,
																			function(
																					i,
																					rowIndex) {
																				dg
																						.datagrid(
																								'deleteRow',
																								rowIndex);
																			});
															clearLast(dgId);
															uiEx
																	.msg(uiEx.rowDeleteSuccessMsg);
														}

													}
													if(callback){
														callback(data);
													}
													// 判断是否当前页最后一条数据
													if(dg.datagrid("getRows").length==0){
														// 分页，且大于第一页
														if(dg.datagrid("options").pageSize && dg.datagrid("options").pageNumber>1){
															dg.datagrid({"pageNumber":dg.datagrid("options").pageNumber-1});//刷新表格
														}
													}
												}
											});
								}else{
								// 单行提交，多次
								$.each(
												delRows,
												function(i, rowIndex) {
													var lastEditIndex = rowIndex;
													dg.datagrid("endEdit",
															lastEditIndex);
													var opts = dg
															.datagrid("options");
													var row = dg
															.datagrid('getRows')[lastEditIndex];
													var row2 = {};
													if (opts.sendRowDataPrefix) {
														$
																.each(
																		row,
																		function(
																				i,
																				v) {
																			row2[opts.sendRowDataPrefix
																					+ i] = row[i];
																		});
													} else {
														row2 = row;
													}

													dg.datagrid('cancelEdit',lastEditIndex).datagrid('deleteRow',lastEditIndex);
													// 同时提交分页参数
													if (dg.datagrid("options")) {
														if (dg
																.datagrid("options").pageSize) {
															row2.rows = dg
																	.datagrid("options").pageSize;
														}
														if (dg
																.datagrid("options").pageNumber) {
															row2.page = dg
																	.datagrid("options").pageNumber;
														}
													}
													$
															.ajax({
																url : url, // 请求的URL
																type : method, // 请求方式(POST、GET），默认GET
																// 发送到服务器端的数据，也可以{key1:
																// 'value1',
																// key2:
																// 'value2'}
																data : row2,
																error : function() {
																	if (showMsg) {
																		uiEx
																				.msg(uiEx.rowDeleteFailureMsg);
																	}
																},
																success : function(
																		data) {
																	if (showMsg) {

																		if (successKey) {
																			if (data[successKey]
																					&& data[successKey] == successValue) {
								
																				clearLast(dgId);
																				uiEx
																						.msg(uiEx.rowDeleteSuccessMsg);
																			} else {
																				dg
																						.datagrid(
																								'insertRow',
																								{
																									index : lastEditIndex, // index
																									// start
																									// with
																									// 0
																									row : row
																								});
																				uiEx
																						.msg(uiEx.rowDeleteFailureMsg);
																			}
																		} else {
																			clearLast(dgId);
																			uiEx.msg(uiEx.rowDeleteSuccessMsg);
																		}

																	}
																	if(callback){
																		callback(data);
																	}
																	// 判断是否当前页最后一条数据
																	if(dg.datagrid("getRows").length==0){
																		// 分页，且大于第一页
																		if(dg.datagrid("options").pageSize && dg.datagrid("options").pageNumber>1){
																			dg.datagrid({"pageNumber":dg.datagrid("options").pageNumber-1});//刷新表格
																		}
																	}
																}
															});

												});
								}
								// 刷新
								if (reloadDataGrid) {
									dg.datagrid('reload');
								}
							}
						});

	}

	/**
	 * 取消选中行的编辑
	 * 
	 * @param datagridSelector
	 *            DataGrid选择器
	 */
	uiEx.rowCancelEdit = function(datagridSelector) {
		var dg = $(datagridSelector);
		var dgId = dg.attr("id");
		// 撤销编辑
		if (dgType[dgId] == "edatagrid") { // edatagrid
			dg.edatagrid('cancelRow');
			// dg.edatagrid('disableEditing');
			// dg.edatagrid('enableEditing');
		} else { // datagrid
			dg.datagrid('cancelEdit', dgLastEditIndex[dgId]);
		}

		// 撤销新添加的行
		if (dgLastEditType[dgId] == "add") {
			// if(dgType[dgId]=="edatagrid"){ //edatagrid
			dg.edatagrid('deleteRow', dgLastEditIndex[dgId]);
			// }else{ //datagrid
			// dg.datagrid('deleteRow',dgLastEditIndex[dgId]);
			// }
		}

		clearLast(dgId);
	}

	/**
	 * edatagrid, 开始编辑，直接单击编辑，无需双击开启编辑
	 * 
	 * @param datagridSelector
	 *            EDataGrid选择器
	 */
	uiEx.beginEditGrid = function(datagridSelector) {
		var dg = $(datagridSelector);

		// 如果没有选中行，则编辑第一行
		var rowIndex = 0;
		if (dg.edatagrid("getSelected")) {
			rowIndex = dg.edatagrid("getRowIndex", dg.edatagrid("getSelected"));
		} else {
			dg.edatagrid("selectRow", rowIndex);
		}
		dg.edatagrid("editRow", rowIndex);

	}
	/**
	 * edatagrid, 结束编辑，进入可编辑状态 不同于disableEditing禁用编辑，禁用编辑则无法编辑
	 * 
	 * @param datagridSelector
	 *            EDataGrid选择器
	 */
	uiEx.endEditGrid = function(datagridSelector) {
		var dg = $(datagridSelector);
		dg.edatagrid('cancelRow');
		dg.edatagrid('options').editIndex = -1;
	}

	/**
	 * @deprecated 使用 showHeaderContextMenu:true,代替 // 表头添加右键菜单，可选择显示的列
	 * 
	 * 为指定DataGrid设置列头右键菜单，可选择和隐藏列，会引起一次新的datagrid加载
	 * 
	 * @param datagridSelector
	 *            数据表格选择器
	 */
	/*
	 * uiEx.headerMenu = function(datagridSelector) {
	 * $(datagridSelector).datagrid({ onHeaderContextMenu : function(e, field) {
	 * e.preventDefault(); var dgId = $(this).attr("id"); var cmenu =
	 * dgHeaderMenu[dgId]; if (cmenu == undefined) { cmenu =
	 * createColumnMenu(this); dgHeaderMenu[dgId] = cmenu; } cmenu.menu("show", {
	 * left : e.pageX, top : e.pageY }); } }); }
	 * 
	 * ################# treegrid增强部分
	 */
	/**
	 * Treegird: Treegird初始化
	 * 
	 * @param treegridSelector
	 *            treegrid选择器
	 * @param params
	 *            可选；treegrid其他参数
	 */
	uiEx.initTreegrid = function(treegridSelector, params) {
		var p = {};

		if (params) {
			// 右键菜单显示，showContextMenu配合menuSelector
			initTgContextMenu(params, p);
			// 初始化treegrid表头右键菜单参数
			initDgHeaderContextMenu(params, p);
		}

		$.extend(p, params);

		$(treegridSelector).treegrid(p);
	}
	/**
	 * Treegird: parentId 方式的 Treegird 初始化
	 * 
	 * @param treegridSelector
	 *            treegrid选择器
	 * @param params
	 *            可选；treegrid其他参数
	 */
	uiEx.initParentIdTreegrid = function(treegridSelector, params) {
		if(params){
			params.loadFilter=function(rows){
				return uiEx.convert(rows);
			}
		}else{
			params={
					loadFilter:function(rows){
						return uiEx.convert(rows);
					}
			}
		}
	
		uiEx.initTreegrid(treegridSelector, params);
	}

	/**
	 * 折叠目录
	 * 
	 * @param treeGridSelector
	 *            treeGrid选择器
	 * 
	 */
	uiEx.collapse = function(treeGridSelector) {
		var node = $(treeGridSelector).treegrid('getSelected');
		if (node) {
			$(treeGridSelector).treegrid('collapse', node.id);
		}
	}
	/**
	 * 展开目录
	 * 
	 * @param treeGridSelector
	 *            treeGrid选择器
	 * 
	 */
	uiEx.expand = function(treeGridSelector) {
		var node = $(treeGridSelector).treegrid('getSelected');
		if (node) {
			$(treeGridSelector).treegrid('expand', node.id);
		}
	}

	/*
	 * ################# tree树菜单增强部分
	 */
	/**
	 * 复选框树初始化
	 * 
	 * @param treeSelector
	 *            树选择器或对象
	 * @param param
	 *            树加载参数
	 * @param values
	 *            默认选中值数组
	 */
	uiEx.initTreeChk = function(treeSelector, param, values) {

		if (!param) {
			param = {};
		}
		if (!values) {
			values = [];
		}
		var tree = $(treeSelector);
		var tid = tree.attr("id");

		// 原始格式化操作
		var formatter = param.formatter;
		// 是否显示title
		if (param.showTitle) {
			// 格式化显示title
			param.formatter = function(node) {
				var title = node[param.showTitle];
				// 如果指定属性值为空，则显示text
				if (!title) {
					title = node["text"];
				}
				if (formatter) {
					return "<span hidetitle='" + title + "'>" + formatter(node)
							+ "</span>";
				} else {
					return "<span hidetitle='" + title + "'>" + node.text
							+ "</span>";
				}
			}
		}
		// 加载成功默认通用处理函数
		var commonLoadSuccess = function(node, data) {
			// 缓存加载到的数据
			treeChkData[tid] = data;
			uiEx.treeChkSetValues(treeSelector, values);

			if (param.showTitle) {
				$("[id^='_easyui_tree_']").each(function(i, treeDiv) {
					var title = $("[hidetitle]", this).attr("hidetitle");
					$(this).attr("title", title);
				});
			}

		}

		var loadSuccess = commonLoadSuccess;

		// 单击节点选中
		var click = function(node) {
			if (!node.checked) {
				tree.tree("check", node.target);
			} else {
				tree.tree("uncheck", node.target);
			}
		}

		if (param["onLoadSuccess"]) {
			var success = param["onLoadSuccess"];
			loadSuccess = function(node, data) {
				success(node, data);
				commonLoadSuccess(node, data);
			}
		}

		if (param["onClick"]) {
			var c = param["onClick"];
			click = function(node, data) {
				c(node, data);
				tree.tree("check", node.target);
			}
		}
		param["onLoadSuccess"] = loadSuccess;
		param["onClick"] = click;
		// 自动选中父节点参数。cascadeCheck:true会导致父节点选中时子节点自动全选
		if (param["noChildCascadeCheck"]) {
			param["cascadeCheck"] = false; // 取消级联选择

			var unCheckFn = undefined;
			if (param["onCheck"]) {
				unCheckFn = param["onCheck"];
			}
			param["onCheck"] = function(node, checked) {
				if (unCheckFn) {
					param["cascadeCheck"] = false;
					unCheckFn(node, checked);
				}
				var nowTree = $(this);
				if (checked) {
					if (nowTree.tree('getParent', node.target)) {
						nowTree.tree("check", nowTree.tree('getParent',
								node.target).target)
					}
				} else {
					var childNodes = nowTree.tree('getChildren', node.target);
					$.each(childNodes, function(i, v) {
						nowTree.tree("uncheck", v.target)
					});
				}
			}
		}

		tree.tree(param);
	}
	
	/**
	 * 使用 ParentId 方式的复选框树初始化
	 * 
	 * @param treeSelector
	 *            树选择器或对象
	 * @param param
	 *            树加载参数
	 * @param values
	 *            默认选中值数组
	 */
	uiEx.initParentIdTreeChk = function(treeSelector, param, values) {
		if(param){
			param.loadFilter=function(rows){
				return uiEx.convert(rows);
			}
		}else{
			param={
				loadFilter:function(rows){
					return uiEx.convert(rows);
				}	
			};
		}
		uiEx.initTreeChk(treeSelector, param, values);
	}
	
	/**
	 * 带复选框的树重置，配合uiEx.treeChk使用
	 * 
	 * @param treeSelector
	 *            树选择器或对象
	 */
	uiEx.treeChkRest = function(treeSelector) {
		var tree = $(treeSelector);
		var tid = tree.attr("id");
		tree.tree("loadData", treeChkData[tid]);
	}

	/**
	 * 设置选中的树复选框，注意：此方法必须在树渲染完后调用
	 * 
	 * @param treeSelector
	 *            树选择器或对象
	 * @param values
	 *            选中的树节点ID数组
	 */
	uiEx.treeChkSetValues = function(treeSelector, values) {
		var tree = $(treeSelector);
		$.each(values, function(i, v) {
			var n = tree.tree('find', v);
			if (n) {
				tree.tree('check', n.target);
			}
		});
	}

	/**
	 * 获得带复选框树选中的节点id数组
	 * 
	 * @param treeSelector
	 *            树选择器或对象
	 * @return 带复选框树选中的节点id数组
	 */
	uiEx.getCheckedIds = function(treeSelector) {
		var nodes = $(treeSelector).tree("getChecked",
				[ 'checked', 'indeterminate' ]);
		var ids = $.map(nodes, function(n) {
			return n.id;
		});

		return ids;
	}

	/**
	 * 获得复选框树选中的节点中attributeArray指定的属性值，返回获得值列表的数组
	 * 
	 * @param treeSelector
	 *            树选择器或对象
	 * @param propertyArray
	 *            树属性数组
	 * @return 获得值列表的数组
	 */
	uiEx.getCheckedInfos = function(treeSelector, propertyArray) {
		var nodes = $(treeSelector).tree("getChecked",
				[ 'checked', 'indeterminate' ]);
		var os = $.map(nodes, function(n) {
			var o = {};
			$.each(propertyArray, function(i, v) {
				o[v] = n[v];
			})
			return o;
		});
		return os;
	}

	/**
	 * Tree: tree初始化，包含两大默认功能： 1. 点击菜单父节点打开子节点功能 2. 点击菜单在tabSelector指定的tab打开
	 * 
	 * @param treeSelector
	 *            datagrid选择器
	 * @param tabSelector
	 *            打开树菜单url的tab选择器
	 * @param params
	 *            可选；tree初始化参数
	 */
	uiEx.initTree = function(treeSelector, tabSelector, params) {
		var p = {
			onClick : function(node) {
				if (node.url) {
					uiEx.openTab(tabSelector, node.text, node.url,
									node.iconCls);
				}
			}
		}

		if (params) {
			if (params.expandChilds) {
				var os;
				if (params.onSelect) {
					os = params.onSelect;
				}

				params.onSelect = function(node) {
					if (os) {
						os(node);
					}
					$(this).tree('toggle', node.target);
				};
			}
		}

		$.extend(p, params);
		$(treeSelector).tree(p);
	}
	
	/**
	 * Tree: parentId类型tree初始化，包含两大默认功能： 
	 * 1. 点击菜单父节点打开子节点功能 2. 点击菜单在tabSelector指定的tab打开
	 * 
	 * @param treeSelector
	 *            datagrid选择器
	 * @param tabSelector
	 *            打开树菜单url的tab选择器
	 * @param params
	 *            可选；tree初始化参数
	 */
	uiEx.initParentIdTree = function(treeSelector, tabSelector, params) {
		
		if(params){
			params.loadFilter=function(rows){
				return uiEx.convert(rows);
			}
		}else{
			params={
				loadFilter:function(rows){
					return uiEx.convert(rows);
				}	
			};
		}
		uiEx.initTree(treeSelector, tabSelector, params);
	}

	/**
	 * onSelect事件处理：Tree的onSelect事件的实现，能实现点击菜单父节点打开子节点功能 在树初始化时通过注册onSelect事件传入
	 * onSelect : uiEx.expandChilds, //点击菜单父节点打开子节点功能
	 * 
	 * 通过initTree的expandChilds属性可以实现相同效果： expandChilds: true;
	 * 
	 * @param node
	 *            事件调用时传入点击的节点对象
	 */
	uiEx.expandChilds = function(node) {
		$(this).tree('toggle', node.target);
	}
	
	
	/*
	 * ################# 其他函数
	 */
	/**
	 * parentId tree 数据转换
	 * loadFilter:function(rows){
	 * 		return uiEx.convert(rows);
	 * 	}
	 * 
	 */
	uiEx.convert =function(rows){
		function exists(rows, parentId){
	
			for(var i=0; i<rows.length; i++){
				if (rows[i].id == parentId) return true;
			}
			return false;
		}
		
		var nodes = [];
		// get the top level nodes
		for(var i=0; i<rows.length; i++){
			var row = rows[i];
			if (!exists(rows, row.parentId)){
				nodes.push(row);
			}
		}
		
		var toDo = [];
		for(var i=0; i<nodes.length; i++){
			toDo.push(nodes[i]);
		}
		while(toDo.length){
			var node = toDo.shift();	// the parent node
			// get the children nodes
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				if (row.parentId == node.id){
					var child = row;
					if (node.children){
						node.children.push(child);
					} else {
						node.children = [child];
					}
					toDo.push(child);
				}
			}
		}
		return nodes;
	}
	/**
	 * 将变量uiEx的控制权让渡给第一个实现它的那个库
	 * 
	 * @return uiEx对象的引用
	 */
	uiEx.noConflict = function() {
		if (window.uiEx === uiEx) {
			window.uiEx = _uiEx;
		}
		return uiEx;
	}

	/*
	 * jQuery对象插件注册
	 */

	jQuery.fn
			.extend({
				/**
				 * tabs:绑定tabs的右键菜单
				 * 
				 * @param menuSelector
				 *            menu选择器
				 * @param menuSelector
				 *            menu选择器；可选，默认之为#tabsMenu
				 */
				addTabsContextMenu : function(menuSelector) {
					return this.each(function(i, v) {
						uiEx.addTabsContextMenu(this, menuSelector);
					});
				},
				/**
				 * form:清除指定id的form表单信息
				 * 
				 * @return form对象
				 */
				clearForm : function() {
					var f;
					this.each(function(i, v) {
						f = uiEx.clearForm(this);
					});
					return f;
				},
				/**
				 * form：重置reset指定id的form表单信息
				 * 
				 * @return form对象
				 */
				resetForm : function() {
					var f;
					this.each(function(i, v) {
						f = uiEx.resetForm(this);
					});
					return f;
				},
				/**
				 * 代替form("load", data);为表单填充数据 可以指定表单名前缀，将数据填充到指定前缀的属性中去
				 * 如将data中的name属性值填充到user.name中
				 * 
				 * @param data
				 *            表单数据
				 * @param prefix
				 *            表单名称前缀
				 * @return form对象
				 */
				loadForm : function(data, prefix) {
					var f;
					this.each(function(i, v) {
						f = uiEx.loadForm(this, data, prefix);
					});
					return f;
				},
				/**
				 * 显示指定对话框
				 * 
				 * @return dialog对象
				 */
				openDialog : function(title) {
					var dialog;
					this.each(function(i, v) {
						dialog = uiEx.openDialog(this, title);
					});
					return dialog;
				},
				/**
				 * 显示指定对话框
				 * 
				 * @param href
				 *            请求内容的href
				 * @param title
				 *            对话框标题
				 * @return dialog对象
				 */
				openHrefDialog : function(href, title) {
					var dialog;
					this.each(function(i, v) {
						dialog = uiEx.openHrefDialog(this, href, title);
					});
					return dialog;
				},
				/**
				 * form:关闭指定对话框
				 * 
				 * @return dialog对象
				 */
				closeDialog : function() {
					var dialog;
					this.each(function(i, v) {
						dialog = uiEx.closeDialog(this);
					});
					return dialog;
				},
				/**
				 * form:开启表单验证
				 * 
				 * @return form对象
				 */
				enableValidate : function() {
					var f;
					this.each(function(i, v) {
						f = uiEx.enableValidate(this);
					});
					return f;
				},
				/**
				 * form:禁用表单验证
				 * 
				 * @return form对象
				 */
				disableValidate : function() {
					var f;
					this.each(function(i, v) {
						f = uiEx.disableValidate(this);
					});
					return f;
				},
				/**
				 * @deprecated 使用 showHeaderContextMenu:true,代替 //
				 *             表头添加右键菜单，可选择显示的列
				 *             datagrid:为指定DataGrid设置列头右键菜单，可选择和隐藏列，会引起一次新的datagrid加载
				 */
				/*
				 * headerMenu : function() { return this.each(function(i, v) {
				 * uiEx.headerMenu(this); }); },
				 */
				/**
				 * menu:根据菜单Id自动在指定Tab打开某个菜单
				 * 
				 * @param tabSelctor
				 *            tab选择器或对象
				 * @param menuId
				 *            菜单Id
				 */
				openMenuById : function(tabSelctor, menuId) {
					return this.each(function(i, v) {
						uiEx.openMenuById(this, tabSelctor, menuId);
					});
				},
				/**
				 * menu:根据菜单Text文本自动在指定Tab打开某个菜单
				 * 
				 * @param tabSelctor
				 *            tab选择器或对象
				 * @param menuText
				 *            菜单Text文本
				 */
				openMenuByText : function(tabSelctor, menuText) {
					return this.each(function(i, v) {
						uiEx.openMenuByText(this, tabSelctor, menuText);
					});
				},
				/**
				 * tab:为指定Tab添加选项卡
				 * 
				 * @param title
				 *            标题
				 * @param url
				 *            打开的url
				 * @param icon
				 *            显示的图标
				 */
				openTab : function(title, url, icon) {
					return this.each(function(i, v) {
						uiEx.openTab(this, title, url, icon);
					});
				},
				/**
				 * DataGrid: datagrid初始化，包含了uiEx.dataGridDefaults参数的以下默认参数：
				 * rownumbers:true, fitColumns:true, singleSelect:true,
				 * pagination:true, method:"post"
				 * 
				 * @param params
				 *            datagrid初始化参数
				 */
				initDatagrid : function(params) {
					return this.each(function(i, v) {
						uiEx.initDatagrid(this, params);
					});
				},
				/**
				 * DataGrid: datagrid初始化，包含了uiEx.dataGridDefaults参数的以下默认参数：
				 * rownumbers:true, fitColumns:true, singleSelect:true,
				 * pagination:true, method:"post"
				 * 
				 * @param params
				 *            datagrid初始化参数
				 */
				initEdatagrid : function(params) {
					return this.each(function(i, v) {
						uiEx.initEdatagrid(this, params);
					});
				},
				/**
				 * DetailDataGrid:
				 * DetailDataGrid初始化，包含了detailDataGridDefaults参数的默认值
				 * 
				 * @param detailUrl
				 *            加载详细视图的url
				 * @param params
				 *            其他参数，主要包括数据CRUD的url地址
				 */
				initDetailDatagrid : function(detailUrl, params) {
					return this.each(function(i, v) {
						uiEx.initDetailDatagrid(this, detailUrl, params);
					});
				},
				/**
				 * Treegrid:
				 * Treegrid初始化
				 * 
				 * @param params
				 *            其他参数，主要包括数据CRUD的url地址
				 */
				initTreegrid : function(params) {
					return this.each(function(i, v) {
						uiEx.initTreegrid(this, params);
					});
				},
				/**
				 * Treegrid:
				 * parentId 方式 Treegrid 初始化
				 * 
				 * @param params
				 *            其他参数，主要包括数据CRUD的url地址
				 */
				initParentIdTreegrid : function(params) {
					return this.each(function(i, v) {
						uiEx.initParentIdTreegrid(this, params);
					});
				},
				/**
				 * tab:刷新当前选项卡选中的Panel
				 */
				reloadSelTab : function() {
					return this.each(function(i, v) {
						uiEx.reloadSelTab(this);
					});
				},
				/**
				 * datagird:为行编辑DataGrid追加新行
				 * 
				 * @param rowParam
				 *            可选;新行显示的数据参数
				 */
				rowAdd : function(rowParam) {
					return this.each(function(i, v) {
						uiEx.rowAdd(this, rowParam);
					});
				},
				/**
				 * datagird、edatagrid、detaildatagrid、treegrid:删除选中行
				 * 
				 * @param showMsg
				 *            boolean值，可选;是否显示提示消息，会覆盖默认的全局uiEx.showRowDeleteMsg参数值
				 * @param reloadDataGrid
				 *            是否reload重新加载 DataGrid，默认为false
				 * @param successKey
				 *            可选；字符串值，执行成功返回的标记key，值必须和successValue相同才代表删除成功
				 * @param successValue
				 *            可选；字符串值，执行成功返回的标记value
				 * @param callback
				 *  		  执行删除成功后的回调函数，参数为服务器端返回的数据
				 */
				rowDelete : function(showMsg, reloadDataGrid, successKey,
						successValue, callback) {
					return this.each(function(i, v) {
						uiEx.rowDelete(this, showMsg, reloadDataGrid,
								successKey, successValue, callback);
					});
				},
				/**
				 * datagird:为指定DataGrid启用行编辑，会引起一次新加载
				 */
				rowEdit : function() {
					return this.each(function(i, v) {
						uiEx.rowEdit(this);
					});
				},
				/**
				 * DetailDataGrid: DetailDataGrid添加行
				 */
				detailRowAdd : function() {
					return this.each(function(i, v) {
						uiEx.detailRowAdd(this);
					});
				},
				/**
				 * DetailDataGrid: DetailDataGrid取消编辑或添加
				 * 
				 * @param index
				 *            编辑行索引，一般从服务器端请求参数传入
				 */
				detailRowCancel : function(index) {
					return this.each(function(i, v) {
						uiEx.detailRowCancel(this, index);
					});
				},
				/**
				 * DetailDataGrid: DetailDataGrid取消编辑或添加
				 * 
				 * @param index
				 *            编辑行索引，一般从服务器端请求参数传入
				 */
				detailRowSave : function(index) {
					return this.each(function(i, v) {
						uiEx.detailRowSave(this, index);
					});
				},
				/**
				 * 取消选中行的编辑
				 * 
				 */
				rowCancelEdit : function() {
					return this.each(function(i, v) {
						uiEx.rowCancelEdit(this);
					});
				},
				/**
				 * edatagrid, 结束编辑，进入可编辑状态 不同于disableEditing禁用编辑，禁用编辑则无法编辑
				 * 
				 */
				endEditGrid : function() {
					return this.each(function(i, v) {
						uiEx.endEditGrid(this);
					});
				},
				/**
				 * edatagrid, 开始编辑，直接单击编辑，无需双击开启编辑
				 * 
				 */
				beginEditGrid : function() {
					return this.each(function(i, v) {
						uiEx.beginEditGrid(this);
					});
				},
				/**
				 * 折叠目录
				 * 
				 * @param treeGridSelector
				 *            treeGrid选择器
				 * 
				 */
				collapse : function() {
					return this.each(function(i, v) {
						uiEx.collapse(this);
					});
				},
				/**
				 * 展开目录
				 * 
				 * @param treeGridSelector
				 *            treeGrid选择器
				 * 
				 */
				expand : function() {
					return this.each(function(i, v) {
						uiEx.expand(this);
					});
				},

				/**
				 * form:将form表单信息格式化为JSON返回
				 */
				serializeJSON : function() {
					var res = {};
					this.each(function(i, v) {
						res = uiEx.serializeJSON(this);
					});
					return res;
				},
				/**
				 * form:普通表单提交
				 * 
				 * @param params
				 *            可选; form表单额外提交的参数
				 * @param noValidate
				 *            可选; boolean, 是否验证; 默认为true
				 */
				submitForm : function(params, noValidate) {
					return this.each(function(i, v) {
						uiEx.submitForm(this, params, noValidate);
					});
				},
				/**
				 * form:以Ajax进行带表单验证的表单提交
				 * 
				 * @param callback
				 *            AJAX请求后的回调处理函数
				 * @param params
				 *            可选; form表单额外提交的参数
				 * @param noValidate
				 *            可选; 是否验证; boolean; 默认为true
				 */
				submitAjax : function(callback, params, noValidate) {
					return this.each(function(i, v) {
						uiEx.submitAjax(this, callback, params, noValidate);
					});
				},
				/**
				 * form:将表单以Ajax提交到指定url
				 * 
				 * @param url
				 *            提交到的URL地址
				 * @param callback
				 *            AJAX请求后的回调处理函数
				 * @param params
				 *            可选; form表单额外提交的参数
				 * @param noValidate
				 *            可选; 是否验证; boolean; 默认为true
				 */
				submitURLAjax : function(url, callback, params, noValidate) {
					return this.each(function(i, v) {
						uiEx.submitURLAjax(this, url, callback, params,
								noValidate);
					});
				},
				/**
				 * tree: parentId 方式的复选框树初始化
				 * 
				 * @param param
				 *            树加载参数
				 * @param values
				 *            默认选中值
				 */
				initTreeChk : function(param, values) {
					return this.each(function(i, v) {
						uiEx.initTreeChk(this, param, values);
					});
				},
				/**
				 * tree:复选框树初始化
				 * 
				 * @param param
				 *            树加载参数
				 * @param values
				 *            默认选中值
				 */
				initParentIdTreeChk : function(param, values) {
					return this.each(function(i, v) {
						uiEx.initParentIdTreeChk(this, param, values);
					});
				},
				/**
				 * Tree: tree初始化，包含两大默认功能： 1. 点击菜单父节点打开子节点功能 2.
				 * 点击菜单在tabSelector指定的tab打开
				 * 
				 * @param tabSelector
				 *            打开树菜单url的tab选择器
				 * @param params
				 *            可选；tree初始化参数
				 */
				initTree : function(tabSelector, params) {
					return this.each(function(i, v) {
						uiEx.initTree(this, tabSelector, params)
					});
				},
				/**
				 * Tree: tree初始化，包含两大默认功能： 1. 点击菜单父节点打开子节点功能 2.
				 * 点击菜单在tabSelector指定的tab打开
				 * 
				 * @param tabSelector
				 *            打开树菜单url的tab选择器
				 * @param params
				 *            可选；tree初始化参数
				 */
				initParentIdTree : function(tabSelector, params) {
					return this.each(function(i, v) {
						uiEx.initParentIdTree(this, tabSelector, params)
					});
				},
				/**
				 * tree:带复选框的树重置，配合uiEx.treeChk使用
				 */
				treeChkRest : function() {
					return this.each(function(i, v) {
						uiEx.treeChkRest(this);
					});
				},
				/**
				 * tree:设置选中的树复选框，注意：此方法必须在树渲染完后调用
				 * 
				 * @param values
				 *            选中的树节点ID数组
				 */
				treeChkSetValues : function(values) {
					return this.each(function(i, v) {
						uiEx.treeChkSetValues(this, values);
					});
				},
				/**
				 * tree:设置选中的树复选框，注意：此方法必须在树渲染完后调用
				 * 
				 * @return 带复选框树选中的节点id数组
				 */
				getCheckedIds : function() {
					var ids;
					this.each(function(i, v) {
						ids = uiEx.getCheckedIds(this);
					});
					return ids;
				},
				/**
				 * form:对表单进行验证
				 * 
				 * @return form对象
				 */
				validate : function() {
					var f;
					this.each(function(i, v) {
						f = uiEx.validate(this);
					});
					return f;
				}

			});

	// 注册对外发布的命名空间
	window.uiEx = uiEx;
})();
