package com.wechat.pay.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.security.KeyStore;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用工具类
 * @author niecheng
 */
public class WXCommonUtil {
	private static Logger log = LoggerFactory.getLogger(WXCommonUtil.class);
	private static final HashMap<OSType, String> appId2OSType = new HashMap<OSType, String>();
	private static final HashMap<String,String> appId2apiKey  = new HashMap<String,String>();
	private static final HashMap<String,String> appId2muchID  = new HashMap<String,String>();
	private static final HashMap<String,String>appId2CA= new HashMap<String,String>();
	public enum OSType {
           android, ios,iosTest,androidTest

	}

	static{
		appId2OSType.put(OSType.android, ConfigUtil.APPID_ANDROID);
		appId2OSType.put(OSType.androidTest,ConfigUtil.APPID_ANDROIDTEST);
		appId2OSType.put(OSType.ios, ConfigUtil.APPID_IOS);
		appId2OSType.put(OSType.iosTest,ConfigUtil.APPID_IOSTEST);
		
		appId2apiKey.put(ConfigUtil.APPID_ANDROID,ConfigUtil.API_KEY);
		appId2apiKey.put(ConfigUtil.APPID_ANDROIDTEST,ConfigUtil.ANDROIDTEST_APIKEY);
		appId2apiKey.put(ConfigUtil.APPID_IOS,ConfigUtil.IOS_API_KEY);
		appId2apiKey.put(ConfigUtil.APPID_IOSTEST, ConfigUtil.IOSTEST_API_KEY);
		
		
		appId2muchID.put(ConfigUtil.APPID_ANDROID,ConfigUtil. MCH_ID);
		appId2muchID.put(ConfigUtil.APPID_ANDROIDTEST, ConfigUtil.MCH_ID_ANDROIDTEST);
		appId2muchID.put(ConfigUtil.APPID_IOS,ConfigUtil.MCH_ID_IOS);
		appId2muchID.put(ConfigUtil.APPID_IOSTEST,ConfigUtil.MCH_ID_IOSTEST);
		
		appId2CA.put(ConfigUtil.APPID_ANDROID,ConfigUtil. ANDROID_CANAME);
		appId2CA.put(ConfigUtil.APPID_ANDROIDTEST, ConfigUtil.ANDROIDTEST_CANAME);
		appId2CA.put(ConfigUtil.APPID_IOS,ConfigUtil.IOS_CANAME);
		appId2CA.put(ConfigUtil.APPID_IOSTEST,ConfigUtil.IOSTEST_CANAME);
		
		
	}
	/**
	 * 发送https请求
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequest(String requestUrl, String requestMethod, String outputStr) {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext;
			try {
				sslContext = SSLContext.getInstance("SSL", "SunJSSE");
				sslContext.init(null, tm, new java.security.SecureRandom());
				// 从上述SSLContext对象中得到SSLSocketFactory对象
				SSLSocketFactory ssf = sslContext.getSocketFactory();
		         return getResponseStr(requestUrl, requestMethod, outputStr, ssf);
			} catch (Exception e) {
		          return null;
			}

		
	}
	/**
	 * 带证书的请求方式
	 * **/
	public static String httpsRequestCA(String requestUrl, String requestMethod, String outputStr,String caName,String much_id) {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		  try{
			  KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//		        FileInputStream instream = new FileInputStream(new File("C:/cert/apiclient_cert.p12"));
			  InputStream  instream =  WXCommonUtil.class.getClassLoader().getResourceAsStream(caName);
//		        FileInputStream instream = new FileInputStream(new File(in));
		        try {
		            keyStore.load(instream,much_id.toCharArray());
		        } finally {
		            instream.close();
		        }
		        SSLContext sslContext = SSLContexts.custom() .loadKeyMaterial(keyStore, much_id.toCharArray()).build();
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			 SSLSocketFactory ssf = sslContext.getSocketFactory();
				return  getResponseStr( requestUrl, requestMethod,  outputStr, ssf);
			}catch(Exception e){
				e.printStackTrace();
			}
            return null;
			
	}
	
	public  static  String getResponseStr(String requestUrl, String requestMethod, String outputStr,SSLSocketFactory ssf){
		try{
		URL url = new URL(requestUrl);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(ssf);
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setUseCaches(false);
		// 设置请求方式（GET/POST）
		conn.setRequestMethod(requestMethod);
		conn.setRequestProperty("content-type", "application/x-www-form-urlencoded"); 
		// 当outputStr不为null时向输出流写数据
		if (null != outputStr) {
			OutputStream outputStream = conn.getOutputStream();
			// 注意编码格式
			outputStream.write(outputStr.getBytes("UTF-8"));
			outputStream.close();
		}
		// 从输入流读取返回内容
		InputStream inputStream = conn.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		// 释放资源
		bufferedReader.close();
		inputStreamReader.close();
		inputStream.close();
		inputStream = null;
		conn.disconnect();
		return buffer.toString();
	} catch (ConnectException ce) {
		log.error("连接超时：{}", ce);
	} catch (Exception e) {
		log.error("https请求异常：{}", e);
	}
	return null;
}
	
	

	public static String urlEncodeUTF8(String source){
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getAppidByOSType(String osType){
		String appid = null ;
		if(osType.equals("android")){
			appid = appId2OSType.get(OSType.android);
		}else if(osType.equals("ios")){
			appid =appId2OSType.get(OSType.ios);
		}else if(osType.equals("iostest")){
			appid = appId2OSType.get(OSType.iosTest);
		}else{
			appid = appId2OSType.get(OSType.androidTest); //现在要是找不到就返回iosTest
		}
		return appid;
	}
	/**
	 * 根据appid 获取微信api的密匙
	 * @param appid  app的Id
	 * **/
	public static String getApiKeyByAppId(String appId){
		return appId2apiKey.get(appId);
	}
	
	/**
	 * 根据appid获取商户Id
	 * @param appid  app的Id
	 * */
	public static String getMuchIdByAppId(String appId){
		return appId2muchID.get(appId);
	}
	
	public static String getCaNameByAppId(String appId){
		return appId2CA.get(appId);
	}

}