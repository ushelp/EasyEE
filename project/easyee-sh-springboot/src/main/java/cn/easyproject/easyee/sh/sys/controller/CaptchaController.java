package cn.easyproject.easyee.sh.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.easyproject.easyee.sh.base.controller.BaseController;
import cn.easyproject.easyee.sh.base.util.StatusCode;

/**
 * 验证码检测
 * 
 * @author easyproject.cn
 * @version 1.0
 *
 */
@RestController
public class CaptchaController extends BaseController {
	public static Logger logger = LoggerFactory.getLogger(CaptchaController.class);

	/**
	 * 验证码检测
	 * 
	 * @return
	 */
	@RequestMapping("checkCaptcha")
	public Map<Object, Object> checkCaptcha(String captcha) {
		HttpSession session = request.getSession();
		if (!(session.getAttribute("rand") != null
				&& session.getAttribute("rand").toString().equalsIgnoreCase(captcha))) {
			super.setStatusCode(StatusCode.ERROR); // 默认为OK
			super.setMsgKey("sys.LoginController.captchatError");
		}
		return super.setJsonMsgMap();
	}

}
