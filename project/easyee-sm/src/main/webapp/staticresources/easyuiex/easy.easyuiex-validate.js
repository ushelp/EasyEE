/**
 * EasyUIEx validate
 * 
 * Version 2.0.0
 * 
 * http://easyproject.cn 
 * https://github.com/ushelp
 * 
 * Copyright 2014 Ray [ inthinkcolor@gmail.com ]
 * 
 * Dependencies: jQuery EasyUI
 * 
 */
/*
 * ################ EasyUiEx Validator function
 */
$(function() {
	/*
	 * ################# Custom validator
	 */

	/**
	 * Comparing equals whit other input
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		equals : {
			validator : function(value, param) {
				return value == $(param[0]).val();
			},
			message : '{1}'
		}
	});

	/**
	 * Minimum length 
	 */
	$.extend($.fn.validatebox.defaults.rules, {
		minLength : {
			validator : function(value, param) {
				return value.length >= param[0];
			},
			message : uiEx.msg.minLength
		}
	});

})