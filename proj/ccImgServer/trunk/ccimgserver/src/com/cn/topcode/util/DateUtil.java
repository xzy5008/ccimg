package com.cn.topcode.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @Title: TODO
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:2014-8-1
 * @author:xiezhongyong
 * @version 2.0
 */
public class DateUtil {
	
	
	 static final String DateForm_001= "yyyy-MM-dd";
	 static final String DateForm_002= "yyyy-MM-dd HH:mm:ss";
	 static final String DateForm_003= "yyyyMMddHHmmss";
	
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String date2String(Date date){
     if(date==null) date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat(DateForm_002);
     return sdf.format(date).toString();
	}
	
	/**
	 * 格式化日期
	 * @param date
	 * @return
	 */
	public static String date2String(Date date,String form){
		if(date==null) date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat(form);
		return sdf.format(date).toString();
	}
	
	
	/**
	 * 格式化日期字符串
	 * @param str
	 * @return
	 * @throws ParseException
	 */
	public static Date string2Date(String str, String form) throws ParseException{
	     if(str==null) return null;
	        SimpleDateFormat sdf=new SimpleDateFormat(form);
	     return sdf.parse(str);
		}
	
	/** date +月
	 * @param num
	 * @return
	 */
	public static Date addMonth(Date date , int num) {
		Calendar cal= Calendar.getInstance();
	    if(null == date){
	    	cal.setTime(new Date());
	    }else{
	    	cal.setTime(date);
	    }
	    cal.add(Calendar.MONTH, num);
	    return  cal.getTime();
	}
	/** date +小时
	 * @param num
	 * @return
	 */
	public static Date addHOUR(Date date , int num) {
		Calendar cal= Calendar.getInstance();
		if(null == date){
			cal.setTime(new Date());
		}else{
			cal.setTime(date);
		}
		cal.add(Calendar.HOUR, num);
		return  cal.getTime();
	}
	/** date +分钟
	 * @param num
	 * @return
	 */
	public static Date addMINUTE(Date date , int num) {
		Calendar cal= Calendar.getInstance();
		if(null == date){
			cal.setTime(new Date());
		}else{
			cal.setTime(date);
		}
		cal.add(Calendar.MINUTE, num);
		return  cal.getTime();
	}
}
