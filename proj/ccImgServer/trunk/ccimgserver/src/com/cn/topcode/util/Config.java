package com.cn.topcode.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import com.mongodb.ServerAddress;

/**
 * @version 1.0
 */
public class Config implements Serializable{
	private static final long serialVersionUID = 130345515953775313L;
	/**
	 * 配置文件名称
	 */
	static final String propfilename = "config";

	
	public static String IMAGE_URL;
	public static String VALID_TIME;
	public static String FILE_PATH;
	public static Integer ID_LEN;
	
	public static Map<String, String> TYPE_LENS;
	
	
	public static  List<ServerAddress> MONGODB_HOSTS = new ArrayList<ServerAddress>();
	public static  String MONGODB_DBNAME;
	public static  String MONGODB_USER;
	public static  String MONGODB_PASSWORD;
	
	
	//图片服务器 信息
	public static String MAKE_SHELL;
	public static String MAKE_IMGPATH;
	public static String MAKE_SDK;
	
	static{
		 new Config();
	}
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
		ID_LEN=Integer.parseInt(resourceBundle.getString("id_len"));
		
		
		TYPE_LENS=splitStatus(resourceBundle.getString("type_lens"));
		
		replSeeds(resourceBundle.getString("mongodb_hosts"));
		MONGODB_DBNAME = resourceBundle.getString("mongodb_dbname");
		MONGODB_USER = resourceBundle.getString("mongodb_user");
		MONGODB_PASSWORD = resourceBundle.getString("mongodb_password");
	
		//图片服务器 信息
		MAKE_SHELL=resourceBundle.getString("MAKE_SHELL");
		MAKE_IMGPATH=resourceBundle.getString("MAKE_IMGPATH");
		MAKE_SDK=resourceBundle.getString("MAKE_SDK");
	}
	
	private void  replSeeds(String hosts) {
		String[] serverAddresses = hosts.split(",");
		ServerAddress address = null;
		try {
			for (String addr : serverAddresses) {
				String[] a = addr.split(":");
				String host = a[0];
				if (a.length > 2) {
					throw new IllegalArgumentException(
							"Invalid Server Address : " + addr);
				} else if (a.length == 2) {
					address = new ServerAddress(host, Integer
							.parseInt(a[1]));
				} else {
					address = new ServerAddress(host);
				}
				MONGODB_HOSTS.add(address);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
