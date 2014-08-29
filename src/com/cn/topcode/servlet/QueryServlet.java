package com.cn.topcode.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class QueryServlet extends HttpServlet {

	private static final long serialVersionUID = 5958203286894296017L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String ccid = req.getParameter("ccid");
		String time = req.getParameter("time");
		String pwd = req.getParameter("pwd");

		String rs = "0001";// 请求参数错误

		try {
			if (!StringUtil.isNull(ccid) && !StringUtil.isNull(time)
					&& !StringUtil.isNull(pwd)) {
				String configPwd = MD5Util.getMD5SecretKey(MD5Util.KEY_PREFIX
						+ ccid + time + MD5Util.KEY_SUFFIX);
				if (pwd.equals(configPwd)) {
					String imgPath = req.getSession().getServletContext()
							.getRealPath("/upload")
							+ "/ccImg/";
					//查询图片是否存在
					File file = new File(imgPath + ccid + ".png");
					if (!file.exists()) {
						rs = "0003";// 图片不存在
					} else {

						String sign = null;// 无时间限制
						if ("sign".equals(req.getParameter("sign"))) {
							sign = "DISPLE";
						} else {
							sign = DateUtil.date2String(new Date(),
									"yyyyMMddHHmmssSSSS");
						}
						rs = Config.IMAGE_URL + DesUtils.encrypt(sign + "," + ccid);

					}
				} else {
					rs = "0002";// 验证失败
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
		resp.setContentType("application/json;charset=utf-8");
		resp.setHeader("Pragma", "No-cache");
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Cache-Control", "no-cache");
		PrintWriter pw = resp.getWriter();
		pw.write(rs.toString());
		pw.flush();
		pw.close();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}
}
