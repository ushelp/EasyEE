package cn.easyproject.easyee.sm.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 日期格式化工具类
 * @author  easyproject.cn
 *
 */
public class DateUtil {
	public static final String FULL = "yyyy-MM-dd HH:mm:ss";
	public static final String FULL_SPRIT = "yyyy/MM/dd HH:mm:ss";
	public static final String FULL_ZH_CN = "yyyy年MM月dd日 HH:mm:ss";
	public static final String FULL_ZH_CN2 = "yyyy年MM月dd日 HH时mm分ss秒";

	public static final String FULL_NO_SECOND = "yyyy-MM-dd HH:mm";
	public static final String FULL_NO_SECOND_SPRIT = "yyyy/MM/dd HH:mm";
	public static final String FULL_NO_SECOND_ZH_CN = "yyyy年MM月dd日 HH:mm";
	public static final String FULL_NO_SECOND_ZH_CN2 = "yyyy年MM月dd日 HH时mm分";

	public static final String YEAR = "yyyy-MM-dd";
	public static final String YEAR_SPRIT = "yyyy/MM/dd";
	public static final String YEAR_ZH_CN = "yyyy年MM月dd日";

	public static final String TIME = "HH:mm:ss";
	public static final String TIME_ZH_CN = "HH时mm分ss秒";
	public static final String NO_SECOND_C = "yyyy年MM月dd日 HH:mm";

	/**
	 * 日期对象转字符串
	 * @param date
	 * @param formartStr
	 * @return
	 */
	public static String dateToString(Date date, String formartStr) {
		String strDate = null;

		if ((formartStr != null) && (!"".equals(formartStr))) {
			SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
			strDate = sdf.format(date);
		}
		return strDate;
	}

	/**
	 * 字符串转日期对象
	 * @param strDate
	 * @param formartStr
	 * @return
	 */
	public static Date stringToDate(String strDate, String formartStr) {
		Date date = null;
		if ((formartStr != null) && (!"".equals(formartStr))) {
			SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
			try {
				date = sdf.parse(strDate);
			} catch (ParseException e) {
				date = null;
				e.printStackTrace();
			}
		}
		return date;
	}
	/**
	 * 当前日期字符串
	 * @param formartStr
	 * @return
	 */
	public static String nowTime(String formartStr) {
		String strDate = null;
		if ((formartStr != null) && (!"".equals(formartStr))) {
			SimpleDateFormat sdf = new SimpleDateFormat(formartStr);
			strDate = sdf.format(new Date());
		}
		return strDate;
	}
	/**
	 * 检测日期是否在今天
	 * @param date
	 * @return
	 */
	public static boolean checkDateInToday(Date date) {
		if (date == null) {
			return true;
		}
		boolean flag = false;
		Date now = new Date();

		String nowStr = dateToString(now, "yyyy-MM-dd");
		String dateStr = dateToString(date, "yyyy-MM-dd");

		if (!nowStr.equals(dateStr)) {
			flag = true;
		}

		return flag;
	}

//	public static void main(String[] args) {
//		String mydate = dateToString(new Date(), "yyyy-MM-dd HH:mm:ss");
//
//		Date date = stringToDate("2001-01-01 12:12:12", "yyyy-MM-dd HH:mm:ss");
//
//		System.out.println(mydate);
//		System.out.println(date);
//		System.out.println(nowTime("yyyy年MM月dd日 HH时mm分ss秒"));
//	}
}