package com.cn.topcode.util;

import java.text.SimpleDateFormat;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @Title: TODO String 处理
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:Apr 15, 2014
 * @author:xiezhongyong
 * @version 1.0
 */
public class StringUtil {

	/**
	 * 转换空值为一个字符串对象.
	 * 
	 * @param str
	 * @return
	 */
	public static String converNullTostr(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str)) {
			str = "";
		}
		return str;
	}

	/**
	 * 判断字符串是否为空.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		boolean isNull = true;
		if (str != null && !"".equals(str.trim()) && !"null".equals(str)) {
			isNull = false;
		}
		return isNull;
	}
	/**
	 * 判断字符串是否为空.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(String str) {
		boolean isNull = false;
		if (str != null && !"".equals(str.trim()) && !"null".equals(str)) {
			isNull = true;
		}
		return isNull;
	}

	/**
	 * *随机生成通用唯一标识符.
	 * 
	 * @return
	 */
	public static String randomUUID() {

		return UUID.randomUUID().toString();
	}

	/**
	 * 判断是否为合法的日期时间字符串
	 * 
	 * @param str_input
	 * @param rDateFormat
	 * @return
	 */
	public static boolean isDate(String str_input, String rDateFormat) {
		if (!isNull(str_input)) {
			SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
			formatter.setLenient(false);
			try {
				String s = formatter.format(formatter.parse(str_input));
				return str_input.equals(s);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	/**
	 * 断是否为合法的日期时间字符串
	 * 
	 * @param str_input
	 * @return
	 */
	public static boolean isDate(String str_input) {
		boolean isOk = false;
		if (isNull(str_input)) {
			return isOk;
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy-MM-dd");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyyMMdd");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyy-MM-dd HH:mm:ss");
		}
		if (!isOk) {
			isOk = isDate(str_input, "yyyyMMddHHmmss");
		}
		return isOk;
	}

	/**
	 * 正则表达式字符串匹配
	 * 
	 * @param regex
	 * @param str_input
	 * @return
	 */
	private static boolean match(String regex, String str_input) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str_input);
		return matcher.matches();
	}

	/**
	 * 判断是否为合法的email地址字符串
	 * 
	 * @param str_input
	 * @return
	 */
	public static boolean isEmail(String str_input) {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return match(regex, str_input);
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str_input
	 * @return
	 */
	public static boolean isNumeric(String str_input) {
		if (isNull(str_input)) {
			return false;
		}
		if (str_input.matches("\\d*")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否合法url地址
	 * 
	 * @param str_input
	 * @return
	 */
	public static boolean isURL(String str_input) {
		String regex = "^(http|ftp|file)://.*";
		return match(regex, str_input);
	}

	/**
	 * 判断是否为合法IP
	 * 
	 * @param str_input
	 * @return
	 */
	public static boolean isIP(String str_input) {
		try {
			String number = str_input.substring(0, str_input.indexOf('.'));
			if (Integer.parseInt(number) > 255)
				return false;
			str_input = str_input.substring(str_input.indexOf('.') + 1);
			number = str_input.substring(0, str_input.indexOf('.'));
			if (Integer.parseInt(number) > 255)
				return false;
			str_input = str_input.substring(str_input.indexOf('.') + 1);
			number = str_input.substring(0, str_input.indexOf('.'));
			if (Integer.parseInt(number) > 255)
				return false;
			number = str_input.substring(str_input.indexOf('.') + 1);
			if (Integer.parseInt(number) > 255)
				return false;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
