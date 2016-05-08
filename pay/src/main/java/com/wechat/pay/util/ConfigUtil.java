package com.wechat.pay.util;

public class ConfigUtil {
	/**
	 * 服务号相关信息
	 */
	//修改配置文件中的 AppId
	
	 public final static String APPID_ANDROID= "your androidapp id";
	 public final static String APPID_ANDROIDTEST ="your androidtestapp id";
	 public final static String APPID_IOS = "your iosapp id"; // ios 现在只有测试账号所以一样
	 public final static String APPID_IOSTEST="your iostestapp id";
	 
	 public final static String MCH_ID = "your android  mch_id ";//商户号
	 public final static String MCH_ID_ANDROIDTEST="your android test mch_id"; // Android 测试支付号
	 public final static String MCH_ID_IOS="your ios  mch_id";//IOS测试支付号
	 public final static String MCH_ID_IOSTEST="your iostest  mch_id";//IOS测试支付号

	 
	 public final static String API_KEY = "your api_key";//ANDROID正式API密钥
	 public final static String ANDROIDTEST_APIKEY="your androidtest api_key" ;// ANDROID测试API密匙
	 public final static String IOS_API_KEY="your ios api_key"; // IOS 微信测试密钥 
	 public final static String IOSTEST_API_KEY="your iostest api_key"; // IOS 微信测试密钥 
	 
	 public final static String ANDROID_CANAME="android_cert.p12"; // 你的证书名字
	 public final static String ANDROIDTEST_CANAME="androidtest_cert.p12";
	 public final static String IOS_CANAME="iostest_cert.p12"; //测试阶段使用同一个
	 public final static String IOSTEST_CANAME="iostest_cert.p12";
	 
	 
	 public final static String OS_ANDROID="android";
	 public final static String OS_IOS = "ios";
	 public final static String OS_ANDROIDTEST="androidtest";
	 public final static String OS_IOSTEST = "iostest";
//	 public final static String SIGN_TYPE = "MD5";//签名加密方式
//	 public final static String CERT_PATH = "D:/apiclient_cert.p12";//微信支付证书存放路径地址
	//微信支付统一接口的回调action

	 public final static String NOTIFY_URL ="your  notify url 你应用的 回调地址";
	 
	 public final static String NOTIFY_RECHARGEURL ="http://ideadev.mobisoft.com.cn:8080/wallet/wechat/recharge";
	/**
	 * 微信基础接口地址
	 */
	 //获取token接口(GET)
	 public final static String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	 //oauth2授权接口(GET)
	 public final static String OAUTH2_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	 //刷新access_token接口（GET）
	 public final static String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// 菜单创建接口（POST）
	 public final static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	// 菜单查询（GET）
	 public final static String MENU_GET_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	// 菜单删除（GET）
	public final static String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	/**
	 * 微信支付接口地址
	 */
	//微信支付统一接口(POST)
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//微信退款接口(POST)
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	//订单查询接口(POST)
	public final static String CHECK_ORDER_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	//关闭订单接口(POST)
	public final static String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	//退款查询接口(POST)
	public final static String CHECK_REFUND_URL = "https://api.mch.weixin.qq.com/pay/refundquery";
	//对账单接口(POST)
	public final static String DOWNLOAD_BILL_URL = "https://api.mch.weixin.qq.com/pay/downloadbill";
	//短链接转换接口(POST)
	public final static String SHORT_URL = "https://api.mch.weixin.qq.com/tools/shorturl";
	//接口调用上报接口(POST)
	public final static String REPORT_URL = "https://api.mch.weixin.qq.com/payitil/report";
	//企业付款接口
	public final static String ENTRPRISE_PAY_URL="https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
}
