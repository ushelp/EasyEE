package cn.easyproject.easyee.sm.base.configuration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@ControllerAdvice(basePackages = { "cn.easyproject.easyee.sm" })
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	// VIEW result

	@ExceptionHandler(Exception.class)
	ModelAndView handleControllerException(HttpServletRequest request, Throwable ex) {
		if(logger.isErrorEnabled()){
			logger.error("Controller exception",ex);
		}
		ModelAndView mav = new ModelAndView();
		mav.setViewName("error/controllerError");
		return mav;
	}

	// JSON result

//	@ExceptionHandler(ArithmeticException.class)
//	@ResponseBody
//	String handleControllerException2(HttpServletRequest request, Throwable ex) {
//		return "JSON-ERROR";
//	}

}
