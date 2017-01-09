/**
 * EasyEE全局处理函数
 */
$(function() {

	// ###### 工具函数
	
	/**
	 * 判断数组中是否包含指定值
	 */
	EasyEE.has = function(array, value) {
		var exists = false;
		$.each(array, function(i, v) {
			if (v == value) {
				exists = true;
				return false;
			}

		});
		return exists;
	}

	
	/**
	 * 判断是否是JSON对象
	 */
	EasyEE.isJson=function (obj) {
		var isjson = typeof (obj) == "object"
				&& Object.prototype.toString.call(obj).toLowerCase() == "[object object]"
				&& !obj.length;
		return isjson;
	}

	/**
	 * JSON返回状态列表
	 * 参考：cn.easyproject.easyee.sh.base.util.StatusCode
	 */
	EasyEE.StatusCode = {
		OK : 200, // 操作正常
		ERROR : 500, // 操作失败
		TIMEOUT : 301, // 用户超时
		NO_PERMISSSION : 401 // 权限不足
	
	}

	/**
	 * 全局Ajax处理 - 对请求返回的数据进行状态判断 - 全局统一消息提示
	 */
	$(document).ajaxSuccess(function(event, xhr, settings, plainData) {
		var data = plainData;
		if (!EasyEE.isJson(data)) {
			try {
				data = eval("(" + plainData + ")");
			} catch (e) {
			}
		}
		
		if (data.statusCode) {
			if (data.statusCode == EasyEE.StatusCode.NO_PERMISSSION) {
				// 权限不足
				if (data.locationUrl) {
					// 未登录，跳转到指定页面
					window.location.href = data.locationUrl;
				}
				uiEx.alert(data.msg, "warning");
			} else if (data.statusCode == EasyEE.StatusCode.ERROR) {
				// 操作失败
				if (data.msg) {
					uiEx.msg(data.msg);
				} else {
					uiEx.msg(EasyEE.msg.failure);
				}
			} else if (data.statusCode == EasyEE.StatusCode.TIMEOUT) {
				// 用户超时
				if (data.msg) {
					uiEx.alert(data.msg, "error");
				} else {
					uiEx.alert(EasyEE.msg.timeout);
				}
				if (data.locationUrl) {
					// 未登录，跳转到指定页面
					window.location.href = data.locationUrl;
				}
			} else {
				// 200 正常
				if (data.msg) {
					uiEx.msg(data.msg);
				}
			}
			if(data.callback){
				data.callback();
			}
		}

	}).ajaxError(function(event, jqXHR, options, errorMsg) {
//		console.info(event);
//		console.info(jqXHR);
//		console.info(options);
//		console.info(errorMsg);
		if (jqXHR.status == 404) {
			// 404
			uiEx.msg(EasyEE.msg.ajaxNotFound);
		} else if (jqXHR.status == 500) {
			// 服务器错误
			uiEx.msg(EasyEE.msg.ajaxServerError);
		} else {
			uiEx.msg(EasyEE.msg.ajaxOtherError);
		}
 
	});

	/**
	 * 由于easyui的form表单提交采用的是iframe，而非ajax，所以需要针对form再进行一次全局消息处理
	 * uiEx.formSubmitSuccess类似于jQuery的全局ajaxSuccess函数
	 * 能够为form表单提交成功的succes事件注册一个系统全局的必须执行函数
	 */
	uiEx.formSubmitSuccess = function(plainData) {
		var data = plainData;
		if (!EasyEE.isJson(data)) {
			try {
				data = eval("(" + plainData + ")");
			} catch (e) {

			}
		}

		if (data.statusCode) {
			// cn.easyproject.easyee.sh.base.util.StatusCode.OK...
			if (data.statusCode == EasyEE.StatusCode.NO_PERMISSSION) {
				// 权限不足
				if (data.locationUrl) {
					// 未登录，跳转到指定页面
					window.location.href = data.locationUrl;
				}
				uiEx.alert(data.msg, "warning");
			} else if (data.statusCode == EasyEE.StatusCode.ERROR) {
				// 操作失败
				if (data.msg) {
					uiEx.msg(data.msg);
				} else {
					uiEx.msg(EasyEE.msg.serverError);
				}
			} else if (data.statusCode == EasyEE.StatusCode.TIMEOUT) {
				// 用户超时
				if (data.msg) {
					uiEx.alert(data.msg, "error");
				} else {
					uiEx.alert(EasyEE.msg.timeout);
				}
				if (data.locationUrl) {
					// 未登录，跳转到指定页面
					window.location.href = data.locationUrl;
				}
			} else {
				// 200 正常
				if (data.msg) {
					uiEx.msg(data.msg);
				}
			}
		}
		if(data.callback){
			data.callback();
		}
	}

	
	

});