package cn.easyproject.easyee.sm.base.configuration;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author easyproject.cn
 * @version 1.0
 * 
 */
@Component
public class MyErrorViewResolver implements ErrorViewResolver {
	private static Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
	
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		// Use the request or status to optionally return a ModelAndView
		// System.out.println(status.value());
		// System.out.println(status.is5xxServerError());
		// System.out.println(model);
		if(logger.isErrorEnabled()){
			logger.error("View error! ["+status.value()+", "+status.getReasonPhrase()+"]");
		}
		
		ModelAndView mav = new ModelAndView();
		if(status.is4xxClientError()){
			mav.setViewName("error/notFound");
		}else{
			mav.setViewName("error/serverError");
		}
		
		return mav;
	}

}
