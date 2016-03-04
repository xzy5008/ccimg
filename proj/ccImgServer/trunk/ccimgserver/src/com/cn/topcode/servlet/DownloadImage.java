package com.cn.topcode.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cn.topcode.shell.MakeService;
import com.cn.topcode.util.Config;
import com.cn.topcode.util.DesUtils;
import com.cn.topcode.util.DateUtil;
import com.cn.topcode.util.MD5Util;
import com.cn.topcode.util.StringUtil;

/**
 * 
 * @Title: 图片地址查询
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:2014-8-26
 * @author:xiezhongyong
 * @version 2.0
 */
public class DownloadImage extends HttpServlet {

	private static final long serialVersionUID = 5958203286894296017L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String ccid = req.getParameter("ccid");

		if(StringUtil.isNull(ccid) || (ccid.length() != 11 && ccid.length() != 18)) {
			resp.getWriter().write("image not exist.");
			resp.getWriter().flush();
			resp.getWriter().close();
			return;
		}
		
		
		
 		try {
					
 			
 					String rs = MakeService.makeImg(ccid);
 					if("-1".equals(rs)) {
 						resp.getWriter().write("image not exist.");
						resp.getWriter().flush();
						resp.getWriter().close();
 					}
 					else{
 						//查询图片是否存在
 						File file = new File(rs.toString());
 						if (!file.exists()) {
 							resp.getWriter().write("image not exist.");
 							resp.getWriter().flush();
 							resp.getWriter().close();
 						} else {
 							outImg(resp, rs);
 						}
 					}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		

	}

	@SuppressWarnings("unused")
	private void outImg(HttpServletResponse resp, String imgPath) {
		try {
			/* 读取文件 */
			File file = new File(imgPath);
			/* 如果文件存在 */
			if (file.exists()) {
				String filename = URLEncoder.encode(file.getName(), "UTF-8");
				resp.reset();
				resp.setContentType("multipart/form-data");
				resp.addHeader("Content-Disposition", "attachment; filename=\""
						+ filename + "\"");
				int fileLength = (int) file.length();
				resp.setContentLength(fileLength);
				/* 如果文件长度大于0 */
				if (fileLength != 0) {
					/* 创建输入流 */
					InputStream inStream = new FileInputStream(file);
					byte[] buf = new byte[4096];
					/* 创建输出流 */
					ServletOutputStream servletOS = resp.getOutputStream();
					int readLength;
					while (((readLength = inStream.read(buf)) != -1)) {
						servletOS.write(buf, 0, readLength);
					}
					inStream.close();
					servletOS.flush();
					servletOS.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
	
}
