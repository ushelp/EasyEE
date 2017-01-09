package cn.easyproject.easyee.ssh.sys.action;

import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.easyproject.easyee.ssh.base.action.BaseAction;
import cn.easyproject.easyee.ssh.base.util.StatusCode;

/**
 * 验证码检测
 * @author easyproject.cn
 * @version 1.0
 *
 */
@ParentPackage("easyssh-default")
public class CaptchaAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger logger=LoggerFactory.getLogger(CaptchaAction.class);
	
	
	private String captcha;

	

	
	/**
	 * 验证码检测
	 * 
	 * @return
	 */
	@Action(value="/checkCaptcha")
	public String checkCaptcha() {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("rand") != null && session
				.getAttribute("rand").toString().equalsIgnoreCase(captcha))) {
			statusCode=StatusCode.ERROR;
			msg=getText("sys.LoginAction.captchatError");
		}

		super.setJsonMsgMap();
		return JSON;
	}
	
	


	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}



}
