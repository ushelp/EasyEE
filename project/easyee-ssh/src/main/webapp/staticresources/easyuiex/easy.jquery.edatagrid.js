/**
 * EasyUIEx edatagrid
 * 
 * Version 1.5.1
 * 
 * http://easyproject.cn https://github.com/ushelp
 * 
 * Copyright 2014 Ray [ inthinkcolor@gmail.com ]
 * 
 * Dependencies: jQuery EasyUI
 * 
 * 
 * edatagrid - jQuery EasyUI
 * 
 * Licensed under the GPL:
 *   http://www.gnu.org/licenses/gpl.txt
 *
 * Copyright 2011 stworthy [ stworthy@gmail.com ] 
 * 
 * Dependencies:
 *   datagrid
 *   messager
 */

(function($){
	var currTarget;
	var validate=false;
	$(function(){
		$(document).unbind('.edatagrid').bind('mousedown.edatagrid', function(e){
				var p = $(e.target).closest('div.datagrid-view,div.combo-panel');
				if (p.length){
					if (p.hasClass('datagrid-view')){
						var dg = p.children('table');
						if (dg.length && currTarget != dg[0]){
							_save();
						}
					}
					return;
				}
				_save();
			function _save(){
				try{
					var dg = $(currTarget);
					if (dg.length){
						var opts = dg.edatagrid("options");
						var editIndex = opts.editIndex;
						if(dg.datagrid('validateRow', editIndex)){
							dg.edatagrid('saveRow');
							currTarget = undefined;
						}
					}
				}catch(e){
					currTarget = undefined;
				}
			
			}
		});
	});
	/**
	 *  EasyUIEx: + datagrid编辑时是否改变标识列表
	 */
	var change={};
	function buildGrid(target){
		var dgId=$(target).attr("id");
		var opts = $.data(target, 'edatagrid').options;
		$(target).datagrid($.extend({}, opts, {
			onDblClickCell:function(index,field){
				if (opts.editing){
					$(this).edatagrid('editRow', index);
					focusEditor(field);
				}
			},
			onClickCell:function(index,field){
				if(opts.clickEdit){ //单击编辑参数
					if (opts.editing){
						$(this).edatagrid('editRow', index);
						focusEditor(field);
					}
				}else{
					if (opts.editing && opts.editIndex >= 0){
						$(this).edatagrid('editRow', index);
						focusEditor(field);
					}
				}
			},
			/**
			 *  EasyUIEx: + 在事件中监控是否改变
			 */
			onEndEdit : function(index, row, changes) {
				for(var i in changes){
					change[dgId]=true;
					return;
				}
				change[dgId]=false;
			},
			onAfterEdit: function(index, row){
				
				/**
				 *  EasyUIEx: + Cancel后，不用再次双击，直接单击任意行（包括当前选中行）即可编辑。
				 *  将编辑索引设为一个超出行范围的值。
				 */
//				opts.editIndex = -1;
				opts.editIndex = 999999;
				/**
				 *  EasyUIEx: + 增加是否改变判断
				 */
				if(!change[dgId]){ 
					return;
				}

				var url = row.isNewRecord ? opts.saveUrl : opts.updateUrl;
				
				var row2={};
				
				if(opts.sendRowDataPrefix==undefined){
					opts.sendRowDataPrefix="";
				}
				$.each(row,function(i,v){
					row2[opts.sendRowDataPrefix+i]=row[i];
				});
				
				if (url){
					
					var method="post";
					if(opts.method){
						method=opts.method;
					}
					var success = uiEx.rowEditSuccessMsg;
					var failure = uiEx.rowEditFailureMsg;
					var showMsg=opts.showMsg;
					if (row.isNewRecord) {
						if (showMsg == undefined) {
							showMsg = uiEx.showRowAddMsg;
						}
						success = uiEx.rowAddSuccessMsg;
						failure = uiEx.rowAddFailureMsg;
					} else {
						if (showMsg == undefined) {
							showMsg = uiEx.showRowEditMsg;
						}
					}
					// 同时提交分页参数
					if(opts){
						if(opts.pageSize){
							row2.rows=opts.pageSize;
						}
						if(opts.pageNumber){
							row2.page=opts.pageNumber;
						}
					}

					$.ajax({
						url : url, // 请求的URL
						type : method, // 请求方式(POST、GET），默认GET
						// 发送到服务器端的数据，也可以{key1: 'value1', key2: 'value2'}
						data :row2,
						dataType:'json',
						error : function() {
							if (showMsg) {
								uiEx.msg(failure);
							}
						},
						success : function(data) {
							if (data.isError){
								$(target).edatagrid('cancelRow',index);
								$(target).edatagrid('selectRow',index);
								$(target).edatagrid('editRow',index);
								opts.onError.call(target, index, data);
								return;
							}
							var newData={};
							if(data.rowData){
								newData=data.rowData;
							}
							newData.isNewRecord=null;
							$(target).datagrid('updateRow', {
								index: index,
								row: newData
							});
							if (opts.tree){
								var idValue = row[opts.idField||'id'];
								var t = $(opts.tree);
								var node = t.tree('find', idValue);
								if (node){
									node.text = row[opts.treeTextField];
									t.tree('update', node);
								} else {
									var pnode = t.tree('find', row[opts.treeParentField]);
									t.tree('append', {
										parent: (pnode ? pnode.target : null),
										data: [{id:idValue,text:row[opts.treeTextField]}]
									});
								}
							}
							opts.onSave.call(target, index, row);
							
							
							var successKey=opts.successKey;
							var successValue=opts.successValue;
							if (showMsg) {
								if(successKey){
									if(data[successKey]&&data[successKey]==successValue){
										uiEx.msg(success);
									}else{
										uiEx.msg(failure);
									}
								}else{
									uiEx.msg(success);
								}
							}
						}
					});
					
				} else {
					opts.onSave.call(target, index, row);
				}
				if (opts.onAfterEdit) opts.onAfterEdit.call(target, index, row);
			},
			onCancelEdit: function(index, row){
				opts.editIndex = -1;
				if (row.isNewRecord) {
					$(this).datagrid('deleteRow', index);
				}
				if (opts.onCancelEdit) opts.onCancelEdit.call(target, index, row);
			},
			onBeforeLoad: function(param){
				if (opts.onBeforeLoad.call(target, param) == false){return false}
//				$(this).datagrid('rejectChanges');
				$(this).edatagrid('cancelRow');
				if (opts.tree){
					var node = $(opts.tree).tree('getSelected');
					param[opts.treeParentField] = node ? node.id : undefined;
				}
			}
		}));
		
		
		function focusEditor(field){
			var editor = $(target).datagrid('getEditor', {index:opts.editIndex,field:field});
			if (editor){
				editor.target.focus();
			} else {
				var editors = $(target).datagrid('getEditors', opts.editIndex);
				if (editors.length){
					editors[0].target.focus();
				}
			}
		}
		
		if (opts.tree){
			$(opts.tree).tree({
				url: opts.treeUrl,
				onClick: function(node){
					$(target).datagrid('load');
				},
				onDrop: function(dest,source,point){
					var targetId = $(this).tree('getNode', dest).id;
					$.ajax({
						url: opts.treeDndUrl,
						type:'post',
						data:{
							id:source.id,
							targetId:targetId,
							point:point
						},
						dataType:'json',
						success:function(){
							$(target).datagrid('load');
						}
					});
				}
			});
		}
	}
	
	$.fn.edatagrid = function(options, param){
		if (typeof options == 'string'){
			var method = $.fn.edatagrid.methods[options];
			if (method){
				return method(this, param);
			} else {
				return this.datagrid(options, param);
			}
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'edatagrid');
			if (state){
				$.extend(state.options, options);
			} else {
				$.data(this, 'edatagrid', {
					options: $.extend({}, $.fn.edatagrid.defaults, $.fn.edatagrid.parseOptions(this), options)
				});
			}
			buildGrid(this);
		});
	};
	
	$.fn.edatagrid.parseOptions = function(target){
		return $.extend({}, $.fn.datagrid.parseOptions(target), {
		});
	};
	
	$.fn.edatagrid.methods = {
		options: function(jq){
			var opts={};
			if($.data(jq[0], 'edatagrid')){
				opts = $.data(jq[0], 'edatagrid').options;
			}
			return opts;
		},
		enableEditing: function(jq){
			return jq.each(function(){
				var opts = $.data(this, 'edatagrid').options;
				opts.editing = true;
			});
		},
		disableEditing: function(jq){
			return jq.each(function(){
				var opts = $.data(this, 'edatagrid').options;
				opts.editing = false;
			});
		},
		editRow: function(jq, index){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				var editIndex = opts.editIndex;
				if (editIndex != index){
					if (dg.datagrid('validateRow', editIndex)){
						if (editIndex>=0){
							if (opts.onBeforeSave.call(this, editIndex) == false) {
								setTimeout(function(){
									dg.datagrid('selectRow', editIndex);
								},0);
								return;
							}
						}
					
						dg.datagrid('endEdit', editIndex);
						dg.datagrid('beginEdit', index);
						opts.editIndex = index;
						
						if (currTarget != this && $(currTarget).length){
							$(currTarget).edatagrid('saveRow');
							currTarget = undefined;
						}
						if (opts.autoSave){
							currTarget = this;
						}
						
						var rows = dg.datagrid('getRows');
						opts.onEdit.call(this, index, rows[index]);
					
					} else {
						setTimeout(function(){
							dg.datagrid('selectRow', editIndex);
						}, 0);
						
					}
				}
			});
		},
		addRow: function(jq, index){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				if (opts.editIndex >= 0){
					if (!dg.datagrid('validateRow', opts.editIndex)){
						dg.datagrid('selectRow', opts.editIndex);
						return;
					}
					validate=true;
					if (opts.onBeforeSave.call(this, opts.editIndex) == false){
						setTimeout(function(){
							dg.datagrid('selectRow', opts.editIndex);
						},0);
						return;
					}
					dg.datagrid('endEdit', opts.editIndex);
				}
				var rows = dg.datagrid('getRows');
//				if (currTarget != this && $(currTarget).length){
//					$(currTarget).edatagrid('saveRow');
//					currTarget = this;
//				}

				if (opts.autoSave){
					currTarget = this;
				}
				function _add(index, row){
					if (index == undefined){
						dg.datagrid('appendRow', row);
						opts.editIndex = rows.length - 1;
					} else {
						dg.datagrid('insertRow', {index:index,row:row});
						opts.editIndex = index;
					}
				}
			
				if (typeof index == 'object'){
					_add(index.index, $.extend(index.row, {isNewRecord:true}))
				} else {
					_add(index, {isNewRecord:true});
				}
				
//				if (index == undefined){
//					dg.datagrid('appendRow', {isNewRecord:true});
//					opts.editIndex = rows.length - 1;
//				} else {
//					dg.datagrid('insertRow', {
//						index: index,
//						row: {isNewRecord:true}
//					});
//					opts.editIndex = index;
//				}
				
				dg.datagrid('beginEdit', opts.editIndex);
				dg.datagrid('selectRow', opts.editIndex);
				
				if (opts.tree){
					var node = $(opts.tree).tree('getSelected');
					rows[opts.editIndex][opts.treeParentField] = (node ? node.id : 0);
				}
				
				opts.onAdd.call(this, opts.editIndex, rows[opts.editIndex]);
			});
		},
		saveRow: function(jq){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				if (opts.editIndex >= 0){
					if (opts.onBeforeSave.call(this, opts.editIndex) == false) {
						setTimeout(function(){
							dg.datagrid('selectRow', opts.editIndex);
						},0);
						return ;
					}
						$(this).datagrid('endEdit', opts.editIndex);
				}
			});
		},
		cancelRow: function(jq){
			return jq.each(function(){
				var opts = $.data(this, 'edatagrid').options;
				if (opts.editIndex >= 0){
					$(this).datagrid('cancelEdit', opts.editIndex);
					/**
					 *  EasyUIEx: + Cancel后，不用再次双击，直接单击任意行（包括当前选中行）即可编辑。
					 *  将编辑索引设为一个超出行范围的值。
					 */
					opts.editIndex=999999;   
				}
			});
		},
		destroyRow: function(jq, index){
			return jq.each(function(){
				var dg = $(this);
				var opts = $.data(this, 'edatagrid').options;
				
				var rows = [];
				if (index == undefined){
					rows = dg.datagrid('getSelections');
					rows.sort(function(i, j) {
						return dg.datagrid("getRowIndex", j) - dg.datagrid("getRowIndex", i);
					});
				} else {
					var rowIndexes = $.isArray(index) ? index : [index];
					for(var i=0; i<rowIndexes.length; i++){
						var row = opts.finder.getRow(this, rowIndexes[i]);
						if (row){
							rows.push(row);
						}
					}
				}
				
				if (!rows.length){
					$.messager.show({
						title: opts.destroyMsg.norecord.title,
						msg: opts.destroyMsg.norecord.msg
					});
					return;
				}
				
				$.messager.confirm(opts.destroyMsg.confirm.title,opts.destroyMsg.confirm.msg,function(r){
					if (r){
						for(var i=0; i<rows.length; i++){
							_del(rows[i]);
						}
						dg.datagrid('clearSelections');
					}
				});
				function _del(row){
					var index = dg.datagrid('getRowIndex', row);
					if (index == -1){return}
					if (row.isNewRecord){
						dg.datagrid('cancelEdit', index);
					} else {
						if (opts.destroyUrl){
							var idValue = row[opts.idField||'id'];
							var row2={};
							
							$.each(row,function(i,v){
								row2[opts.sendRowDataPrefix+i]=row[i];
							});
							
							var method="post";
							var showMsg=opts.showMsg;
							if(opts.method){
								method=opts.method;
							}
							if (showMsg == undefined) {
								showMsg = uiEx.showRowDeleteMsg;
							}
							// 同时提交分页参数
							if(dg.datagrid("options")){
								if(dg.datagrid("options").pageSize){
									row2.rows=dg.datagrid("options").pageSize;
								}
								if(dg.datagrid("options").pageNumber){
									row2.page=dg.datagrid("options").pageNumber;
								}
							}
							$.ajax({
								url : opts.destroyUrl, // 请求的URL
								type : method, // 请求方式(POST、GET），默认GET
								// 发送到服务器端的数据，也可以{key1: 'value1', key2: 'value2'}
								data :row2,
								dataType:'json',
								error : function() {
									if (showMsg) {
										uiEx.msg(uiEx.rowDeleteFailureMsg);
									}
								},
								success :function(data){
									var index = dg.datagrid('getRowIndex', idValue);
									if (data.isError){
										dg.datagrid('selectRow', index);
										opts.onError.call(dg[0], index, data);
										return;
									}
									
									opts.onDestroy.call(dg[0], index, row);
									var successKey=opts.successKey;
									var successValue=opts.successValue;
									if (showMsg) {
										if(successKey){
											if(data[successKey]&&data[successKey]==successValue){
												uiEx.msg(uiEx.rowDeleteSuccessMsg);
												if (opts.tree){
													dg.datagrid('reload');
													var t = $(opts.tree);
													var node = t.tree('find', idValue);
													if (node){
														t.tree('remove', node.target);
													}
												} else {
													dg.datagrid('cancelEdit', index);
													dg.datagrid('deleteRow', index);
												}
											}else{
												uiEx.msg(uiEx.rowDeleteFailureMsg);
											}
										}else{
											uiEx.msg(uiEx.rowDeleteSuccessMsg);
											if (opts.tree){
												dg.datagrid('reload');
												var t = $(opts.tree);
												var node = t.tree('find', idValue);
												if (node){
													t.tree('remove', node.target);
												}
											} else {
												dg.datagrid('cancelEdit', index);
												dg.datagrid('deleteRow', index);
											}
										}
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
							
						} else {
							dg.datagrid('cancelEdit', index);
							dg.datagrid('deleteRow', index);
							opts.onDestroy.call(dg[0], index, row);
							// 判断是否当前页最后一条数据
							if(dg.datagrid("getRows").length==0){
								// 分页，且大于第一页
								if(dg.datagrid("options").pageSize && dg.datagrid("options").pageNumber>1){
									dg.datagrid({"pageNumber":dg.datagrid("options").pageNumber-1});//刷新表格
								}
							}
						}
						
						
					}
					
					
					
				}
				
				
			});
		}
		
		
		
	};
	
	$.fn.edatagrid.defaults = $.extend({}, $.fn.datagrid.defaults, {
		editing: true,
		editIndex: -1,
		destroyMsg:{
			norecord:{
				title:'Warning',
				msg:'No record is selected.'
			},
			confirm:{
				title:'Confirm',
				msg:'Are you sure you want to delete?'
			}
		},
//		destroyConfirmTitle: 'Confirm',
//		destroyConfirmMsg: 'Are you sure you want to delete?',
		
		autoSave: false,	// auto save the editing row when click out of datagrid
		url: null,	// return the datagrid data
		saveUrl: null,	// return the added row
		updateUrl: null,	// return the updated row
		destroyUrl: null,	// return {success:true}
		
		tree: null,		// the tree selector
		treeUrl: null,	// return tree data
		treeDndUrl: null,	// to process the drag and drop operation, return {success:true}
		treeTextField: 'name',
		treeParentField: 'parentId',
		
		onAdd: function(index, row){},
		onEdit: function(index, row){},
		onBeforeSave: function(index){},
		onSave: function(index, row){},
		onDestroy: function(index, row){},
		onError: function(index, row){}
	});
})(jQuery);