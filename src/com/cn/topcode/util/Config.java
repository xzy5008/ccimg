package com.cn.topcode.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * @version 1.0
 */
public class Config implements Serializable{
	private static final long serialVersionUID = 130345515953775313L;
	/**
	 * 配置文件名称
	 */
	static final String propfilename = "config";
	static{
		 new Config();
	}
	
	public static String IMAGE_URL;
	public static String VALID_TIME;
	public static String FILE_PATH;
	/**
	 *加载配置文件
	 */
	private Config() {
		try {
			this.load();
			System.out.println("##############config 配置文件 加载了");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 配置文件加载
	 * @throws Exception
	 */
	private void load() throws Exception {
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle(propfilename);
		
		IMAGE_URL=resourceBundle.getString("image_url");
		VALID_TIME=resourceBundle.getString("valid_time");
		FILE_PATH=resourceBundle.getString("file_path");
		
		
		
	}
	
	/**
	 * 状态 拆分
	 * @param str
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	private HashMap splitStatus(String str)throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();
		String[] temp = token(str,",");
		for(int i=0;i<temp.length;i++){
			String[] val = token(temp[i],"=");
			resultMap.put(val[0], val[1]);
		}
		return resultMap;
	}	

	private String[] token(String str,String ceparator)throws Exception {
		StringTokenizer st = new StringTokenizer(str, ceparator);
		String return_arrayStr[] = new String[st.countTokens()];
		int count=0;
		while(st.hasMoreTokens()) {     
			return_arrayStr[count]=st.nextToken();
		      count++;
	    }
		return return_arrayStr;
	}
	
	
}
