package com.cn.topcode.util;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * 
 * @Title: 加密解密 
 * @Description: 实现TODO
 * @Copyright:Copyright (c) 2014
 * @Company:上海亨码信息科技有限公司
 * @Date:2014-8-26
 * @author:xiezhongyong
 * @version 2.0
 */
public class DesUtils{  
    Key key;  
  
    public DesUtils() {  
  
    }  
  
    public DesUtils(String str) {  
        setKey(str); // 生成密匙  
    }  
  
    public Key getKey() {  
        return key;  
    }  
  
    public void setKey(Key key) {  
        this.key = key;  
    }  


    /** 
     * 根据参数生成 KEY      
     */  
    public void setKey(String strKey) { 
        try { 
        	//防止linux下 随机生成key
    		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
    		secureRandom.setSeed(strKey.getBytes());

            KeyGenerator _generator = KeyGenerator.getInstance("DES"); 
            _generator.init(secureRandom); 
            this.key = _generator.generateKey(); 
            _generator = null; 
        } catch (Exception e) { 
            throw new RuntimeException( 
                    "Error initializing SqlMap class. Cause: " + e); 
        } 
    }
  
    /** 
     * 加密 String 明文输入 ,String 密文输出 
     */  
    public String encryptStr(String strMing) {  
        byte[] byteMi = null;  
        byte[] byteMing = null;  
        String strMi = "";  
        BASE64Encoder base64en = new BASE64Encoder();  
        try {  
            byteMing = strMing.getBytes("UTF8");  
            byteMi = this.encryptByte(byteMing);  
            strMi = base64en.encode(byteMi);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            base64en = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMi;  
    }  
  
    /** 
     * 解密 以 String 密文输入 ,String 明文输出 
     * 
     * @param strMi 
     * @return 
     */  
    public String decryptStr(String strMi) {  
        BASE64Decoder base64De = new BASE64Decoder();  
        byte[] byteMing = null;  
        byte[] byteMi = null;  
        String strMing = "";  
        try {  
            byteMi = base64De.decodeBuffer(strMi);  
            byteMing = this.decryptByte(byteMi);  
            strMing = new String(byteMing, "UTF8");  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            base64De = null;  
            byteMing = null;  
            byteMi = null;  
        }  
        return strMing;  
    }  
  
    /** 
     * 加密以 byte[] 明文输入 ,byte[] 密文输出 
     * 
     * @param byteS 
     * @return 
     */  
    private byte[] encryptByte(byte[] byteS) {  
        byte[] byteFina = null;  
        Cipher cipher;  
        try {  
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            cipher.init(Cipher.ENCRYPT_MODE, key);  
            byteFina = cipher.doFinal(byteS);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }  
  
    /** 
     * 解密以 byte[] 密文输入 , 以 byte[] 明文输出 
     * 
     * @param byteD 
     * @return 
     */  
    private byte[] decryptByte(byte[] byteD) {  
        Cipher cipher;  
        byte[] byteFina = null;  
        try {  
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            cipher.init(Cipher.DECRYPT_MODE, key);  
            byteFina = cipher.doFinal(byteD);  
        } catch (Exception e) {  
            throw new RuntimeException(  
                    "Error initializing SqlMap class. Cause: " + e);  
        } finally {  
            cipher = null;  
        }  
        return byteFina;  
    }  
   
    /**
     * 转16进制
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {  
        String hs = "";  
        String stmp = "";  
        for (int n = 0; n < b.length; n++) {  
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));  
            if (stmp.length() == 1) {  
                hs = hs + "0" + stmp;  
            } else {  
                hs = hs + stmp;  
            }  
        }  
        return hs.toUpperCase();  
    }  
    
    /**
     * 16进制转字符串
     * @param s
     * @return
     */
	public static String toStringHex(String s){
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage(),e);
			}
		}

		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 字符串加密 
	 * @param str
	 * @return 16进制字符串
	 */
	public static String encrypt(String str){
		DesUtils des = new DesUtils("SDFJSLDFJLSDJKF"); 
		String val = des.encryptStr(str);
		return byte2hex(val.getBytes());
	}
	/**
	 * 16进制字符串解密
	 * @param str
	 * @return 16进制字符串
	 */
	public static String decrypt(String str){
		try {
			DesUtils des = new DesUtils("SDFJSLDFJLSDJKF"); 
			String val = toStringHex(str);
			return des.decryptStr(val);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	
    public static void main(String[] args) throws Exception {  
//    	DesUtils des = new DesUtils("123456");  
//        String str1 = "201";  
//        // DES 加密字符串  
//        String str2 = des.encryptStr(str1);  
//        // DES 解密字符串  
//        String deStr = des.decryptStr(str2);  
//        System.out.println(" 加密前： " + str1);  
//        System.out.println("s2="+str2);
//        String s1 = byte2hex(str2.getBytes());
//        String s2 = 
//        s2 = toStringHex(s1);
//        System.out.println("16:"+s1);
//        System.out.println("2:"+s2);
//        System.out.println(" 加密后： " + str2);  
//        System.out.println(" 加密后长度： " + str2.length());  
//        System.out.println(" 解密后： " + deStr);  
    	
    	String s = "123123快乐士大夫士大夫士大夫";
    	String v1 = DesUtils.encrypt(s);
    	System.out.println(v1);
    	String v2 =DesUtils.decrypt("3166424B79636A59383433464243783533494A4D4968315531726F503233355662536D42355969672F4D6F3D");
    	System.out.println(v2);
    }  
 }

