package com.cn.topcode.util;

/**
 * 
 * @Title: TODO
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:Mar 27, 2014
 * @author:xiezhongyong
 * @version 1.0
 */
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 采用MD5加密解密
 * 
 * @author tfq
 * @datetime 2011-10-13
 */
public class MD5Util {


    //  加密前缀
    public final static String KEY_PREFIX = "TKIHDEKD6FOKKKMTFd68";

    // 加密后缀
    public final static String KEY_SUFFIX = "QCTD458JDYKTKIHDECTD";
    
    //  加密前缀
    public final static String PASS_PREFIX = "JSDFKDF798J9JKJDSKF";
    
    // 加密后缀
    public final static String PASS_SUFFIX = "JKSJDF678JKDF67DSFJ";
	/**
	 * MD5 加密
	 * 
	 * @param inStr
	 *            明文
	 * @return
	 */
	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}

	/**
	 * 解密算法
	 * 
	 * @param inStr
	 *            密文
	 * @return
	 */
	public static String convertMD5(String inStr) {

		char[] a = inStr.toCharArray();
		for (int i = 0; i < a.length; i++) {
			a[i] = (char) (a[i] ^ 't');
		}
		String s = new String(a);
		return s;

	}
	
	/**
	 * 登录密码加密
	 * @param password
	 * @return
	 */
	public static String getPassword(String password) {
		return getMD5SecretKey(PASS_PREFIX+password+PASS_SUFFIX);
		
	}
	
	/**
	 * MD5 32位加密
	 * 
	 * @param str
	 *            加密串
	 * @return
	 */
	public static String getMD5SecretKey(String str) {
		MessageDigest messageDigest = null;

		try {
			messageDigest = MessageDigest.getInstance("MD5");

			messageDigest.reset();

			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException caught!");
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}

		return md5StrBuff.toString().toLowerCase();
	}

	// 测试主函数
	public static void main(String args[]) {
		
		String s = "adfsdf";
		System.out.println(s);
		System.out.println("原始：" + s);
		System.out.println("MD5后：" + string2MD5(s));
		System.out.println("加密的：" + convertMD5(s));
		System.out.println("解密的：" + convertMD5(s));

	}
}