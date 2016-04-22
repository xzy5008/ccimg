/** 
 * @author xie
 * @version 2.0
 * @date 2014-1-9-上午9:57:03
 */

package com.cn.topcode.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.topcode.util.MD5Util;



/**
 * 功能说明：HTTP Get/Post
 */
public class HttpRequestUtil {
	
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
    	
    	
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            
            String time = System.currentTimeMillis()+"";
            String userid = "93f9371d-65a4-49df-b80b-9a861a6fcf58";
            
            String param = "ccid=00000004003";
            String sign = MD5Util.getMD5SecretKey(MD5Util.KEY_PREFIX+userid+time+param+MD5Util.KEY_SUFFIX);
            // 设置通用的请求属性
//            connection.setRequestProperty("content-length", param.getBytes().length+"");
            connection.setRequestProperty("sign", sign);
            connection.setRequestProperty("time", time);
            connection.setRequestProperty("userid", userid);
            
            
            // 设置通用的请求属性
//            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            
//            String time = "11111111111sdfsdf";
//            String userid = "13629f80-a8a9-49bb-966b-62076e46290a";
            
            
//            String sign = MD5Util.getMD5SecretKey(MD5Util.KEY_PREFIX+userid+time+param+MD5Util.KEY_SUFFIX);
            // 设置通用的请求属性
            conn.setRequestProperty("content-length", param.getBytes().length+"");
//            conn.setRequestProperty("sign", sign);
//            conn.setRequestProperty("time", time);
//            conn.setRequestProperty("userid", userid);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Content-Type", "multipart/form-data");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
//    public static void main(String[] args) {
//    	 String time = "11111111111sdfsdf";
//         String userid = "13629f80-a8a9-49bb-966b-62076e46290a";
//    	String ccid = "00000004003";
//    	ccid = SecretUtils.encryptModeString(ccid, userid+time);
//    	
//    	System.out.println(ccid);
//    	
//		String rs = HttpRequestUtil.sendPost("http://127.0.0.1:8080/rest/cc/qs","{\"ccid\":\"999\",\"rows\":\"5\",\"cols\":\"5\"}");
//		System.out.println("rs==="+rs);
//	}

    
    public static void main(String[] args) {
    	SimpleDateFormat SDF=new SimpleDateFormat("yyyyMMddHHmmss");
    	String time = SDF.format(new Date());
    	String ccid = "040000000000000000";
    	String pwd = MD5Util.getMD5SecretKey(MD5Util.KEY_PREFIX+ccid+time+MD5Util.KEY_SUFFIX);
    	
		String rs = sendGet("http://127.0.0.1:8080/query?ccid="+ccid+"&time="+time+"&pwd="+pwd);
		System.out.println(rs);
		
	}
}
	
