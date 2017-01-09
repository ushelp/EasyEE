package cn.easyproject.easyee.sm.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 页面跳转
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@Controller
public class ForwardController {

	@RequestMapping("toLogin")
	public String toLogin() {
		return "login";
	}
	
	@RequestMapping("toMain")
	public String toMain() {
		return "main/main";
	}

	@RequestMapping("toReports")
	public String toReports() {
		return "main/reports/doChart";
	}

	

}
