package cn.easyproject.easyee.sm.base.util;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidatorUtils {
	static Validator validator;
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	/**
	 * 根据实体上的注解校验实体
	 * 
	 * @param t
	 * @return
	 */
	public static <T> String validate(T t) {

		Set<ConstraintViolation<T>> constraintViolations = validator
				.validate(t);
		String validateError = "";
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				validateError = constraintViolation.getMessage();
				break;
			}
		}
		return validateError;
	}

	/**
	 * 判断是否电话号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		boolean flag = false;
		try {
			Pattern p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
			Matcher m = p.matcher(mobiles);
			flag = m.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否纯数字
	 * 
	 * @param s
	 * @return
	 */
	public final static boolean isNumeric(String s) {
		if (s != null && !"".equals(s.trim()))
			return s.matches("^[0-9]*$");
		else
			return false;
	}
}
