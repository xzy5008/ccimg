package com.cn.topcode.shell;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.cn.topcode.util.Config;
import com.cn.topcode.util.MongoDBUtil;

/**
 * 
 * @Title:
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:2016-1-13
 * @author:xiezhongyong
 * @version 1.0
 */
public class MakeService {

	/**
	 * @param shellPaht
	 * @param staticArea -q 此次彩码生成时是否不需要静止区，0表示需要静止区，1表示不需要
	 * @param border     -e 此次彩码生成时是否不需要边框，0表示需要，1表示不需要 
	 * @param width		  宽度
	 * @param sdk		  验证码 
	 * @param id		  彩码ID
	 * @param type       -t 此次彩码生成类型，必填 1=5x5,2=5x8
	 * @return
	 */
	public static boolean ExeShell(String shellPath,String imgPath,String sdk,String staticArea,String border,int width,String id,String type,String count) {
		StringBuffer sb = new StringBuffer(shellPath);
		sb.append(" -q ").append(staticArea);	//静止区
		sb.append(" -e ").append(border);		//边框
		sb.append(" -o xxxxx");					//订单号
		sb.append(" -p ").append(sdk);			//生产验证码
		sb.append(" -s ").append(id);			//	彩码ID
		sb.append(" -n ").append(count);		//	彩码数量
		sb.append(" -t ").append(type);			//	彩码类型
		sb.append(" -w ").append(width);		//	彩码宽度
		sb.append(" -d ").append(imgPath);		//	彩码宽度
		String cmd = sb.toString();
		Runtime run = Runtime.getRuntime();
		String result = "";
		BufferedReader br = null;
		BufferedInputStream in = null;
		try {
			Process p = run.exec(cmd);
			if (p.waitFor() != 0) {
				result += "没有进程号";
				return false;
			}
			in = new BufferedInputStream(p.getInputStream());
			br = new BufferedReader(new InputStreamReader(in));
			String lineStr;
			while ((lineStr = br.readLine()) != null) {
				result += lineStr;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (br != null) {
				try {
					br.close();
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	
	
	public static String makeImg(String id,String staticArea,String border,String lmv,String dpi) {
		
		
		try {
			String type = "58";
			String mType = "2";
			if(id.length() == 11) {
				type = "55";
				mType = "1";
			}
			
			
			int rs = MongoDBUtil.query(type, Long.parseLong(id));
			
			int width = countPx(lmv, dpi, type);			//宽度
			String count = "1";
			if(0 == rs) {
				ExeShell(Config.MAKE_SHELL, Config.MAKE_IMGPATH, Config.MAKE_SDK, staticArea, border, width, id, mType, count);
			}else{
				return "-1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return  Config.MAKE_IMGPATH +"/"+id+".png";
		// " -q 1 -e 0 -o xxxxx -p 3c42e219631e26da65309fe44ce15f63 -s 004039238146692861 -n 1 -t 2 -w 160 -d /home/imgserver/data";
//		 * @param shellPaht
//		 * @param staticArea -q 此次彩码生成时是否不需要静止区，0表示需要静止区，1表示不需要
//		 * @param border     -e 此次彩码生成时是否不需要边框，0表示需要，1表示不需要 
//		 * @param width		  宽度
//		 * @param sdk		  验证码
//		 * @param id		  彩码ID
//		 * @param type       -t 此次彩码生成类型，必填 1=5x5,2=5x8
//		 * @return
		
		
	}
	
	//
	 public static int countPx(String lmv, String dpiv,String typev){
		 int rs;
		 try {
			 	Map<String, String> tp = new HashMap<String, String>();
			 	tp.put("55", "5");
			 	tp.put("58", "8");
				double lm = Double.parseDouble(lmv);
				double dpi = Double.parseDouble(dpiv);
				int type = Integer.parseInt(tp.get(typev));
				
				rs = (int) ((lm*0.3937)*dpi);
				int v = 1;
				while(rs % type != 0) {
					
					if((rs+v)%type ==0 ){
						rs = rs + v;
						break;
					}
					
					if((rs-v)%type == 0) {
						rs = rs-v;
						
						break;
					}
					
					v++;
				}
				
		} catch (Exception e) {
			throw new RuntimeException("像素转换异常:"+e);
		}
		
		 
		 return rs;
		 
	 }
	
	

}
