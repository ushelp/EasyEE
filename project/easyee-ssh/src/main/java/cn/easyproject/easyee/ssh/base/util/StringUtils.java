package cn.easyproject.easyee.ssh.base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringUtils {
	//SQL防注入过滤字符，多个过滤字符用#分隔
	public static final String FILTER_SQL_INJECT = "'#and#exec#insert#select#delete# #update#count#*#%#chr#mid#master#truncate#char#declare#;#or#-#+#,";
	public static final String[] INJECT_STRING_ARRAY=FILTER_SQL_INJECT.split("#"); //SQL防注入过滤数组
	 
	/**
	 * 过滤文本中的特殊字符，只保留字符串和数字	
	 * @param str 要过滤的字符串
	 * @return 过滤后的结果
	 */
	public static String onlyLetterAndDigital(String str){
		  String regEx="[`~!@#$%^&*()+=|{}':;',//[//].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";   
	      Pattern   p   =   Pattern.compile(regEx);      
	      Matcher   m   =   p.matcher(str);      
	      return     m.replaceAll("").trim();      
	}

	/**
	 * 过滤SQL条件中的非法字符，防注入
	 * @param condition SQL拼接条件和参数
	 * @return 过滤后的结果
	 */
	public static String filterSQLCondition(String condition){
		if(condition==null||condition.equals("")){
			return "";
		}
		 for(String s:INJECT_STRING_ARRAY){
			 condition=condition.replace(s, ""); 
		 }
		return condition;
	}
	
	/**
	 * 安全的从字符串中截取指定长度的开始内容
	 * @param content 内容
	 * @param length 截取的长度
	 * @return 截取并追加的结果
	 */
	public static String subStart(String str,int length){
		if(str.length()>length){
			return str.substring(0,length);
		}
		return str;
	}
	/**
	 * 安全的从字符串中按照索引截取指定长度内容
	 * @param str
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static String substring(String str,int startIndex,int endIndex){
		startIndex=startIndex<0?0:startIndex;
		endIndex=endIndex>str.length()?str.length():endIndex;
		if(startIndex<endIndex){
			return "";
		}
		return str.substring(startIndex, endIndex);
	}
	/**
	 * 安全的从字符串中按照索引截取指定长度内容
	 * @param str
	 * @param startIndex
	 * @param endIndex
	 * @return
	 */
	public static String substr(String str,int startIndex,int length){
		return substring(str,startIndex, startIndex+length);
	}
	/**
	 * 判断字符串是否不为空或null
	 * @param str 字符串对象
	 * @return 是否不为空
	 */
	public static boolean isNotNullAndEmpty(Object str){
		return str!=null&&(!str.toString().trim().equals(""));
	}
	
}
