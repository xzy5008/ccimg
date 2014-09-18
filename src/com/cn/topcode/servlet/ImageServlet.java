package com.cn.topcode.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.topcode.util.Config;
import com.cn.topcode.util.DateUtil;
import com.cn.topcode.util.DesUtils;
import com.cn.topcode.util.StringUtil;

/**
 * 
 * @Title: 彩码图片输出
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:2014-8-26
 * @author:xiezhongyong
 * @version 2.0
 */
public class ImageServlet extends HttpServlet{
	
	private static final long serialVersionUID = 5958203286894296017L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String uri = req.getRequestURI();
		String p = uri.substring(uri.lastIndexOf("/")+1,uri.length());
//		String imgPath = req.getSession().getServletContext().getRealPath("/upload")+ "/ccImg/";//图片路径
		String imgPath = Config.FILE_PATH;
		try {
			if(!StringUtil.isNull(p)){
				p = DesUtils.decrypt(p); //解密
				String[] arr = p.split(",");
				if("DISPLE".equals(arr[0])){
					File file = new File(imgPath + arr[1] + ".png");
					if (file.exists()) {
						outImg(resp, imgPath + arr[1] + ".png");
					} 
				}else{
					if(DateUtil.addMINUTE(DateUtil.string2Date(arr[0],"yyyyMMddHHmmssSSSS"), 
							Integer.parseInt(Config.VALID_TIME)).getTime()> (new Date()).getTime()){
						File file = new File(imgPath + arr[1] + ".png");
						if (file.exists()) {
							outImg(resp, imgPath + arr[1] + ".png");
						} 
					}
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	@SuppressWarnings("unused")
	private void outImg(HttpServletResponse resp, String imgPath){
		try {
			resp.setHeader("Pragma", "No-cache");
			resp.setDateHeader("Expires", 0);
			resp.setHeader("Cache-Control", "no-cache");
			OutputStream out = resp.getOutputStream();
			InputStream is = new FileInputStream(imgPath);
			
			byte[] bs = new byte[1024];
			int len = -1;
			while((len=is.read(bs))!=-1){
				out.write(bs);
			}
			out.flush();
			is.close();
			out.close();
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
}
