package com.wechat.pay.service.impl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wechat.pay.info.WeChatDrawCashInfo;
import com.wechat.pay.info.WeChatDrawCashResultInfo;
import com.wechat.pay.info.WeChatPayInfo;
import com.wechat.pay.info.WeChatSendInfo;
import com.wechat.pay.service.WechatBizApi;
import com.wechat.pay.util.ConfigUtil;
import com.wechat.pay.util.WXCommonUtil;
import com.wechat.pay.util.WXPayCommonUtil;
import com.wxap.util.XMLUtil;
@Service
public class WechatBizApiImpl  implements WechatBizApi{
	
	protected static final Logger logger = LoggerFactory.getLogger(WechatBizApiImpl.class);
	/**
	 * 微信支付统一下单接口
	 * **/
	 @Override
	public Map<String,String> getPrepayMap(WeChatSendInfo weChatSendInfo,String notify_url)throws Exception{
		SortedMap<String,String> parameters = new TreeMap<String,String>();
		weChatSendInfo.setNotify_url(notify_url);
		String appid = WXCommonUtil.getAppidByOSType(weChatSendInfo.getOsType());
		String much_id = WXCommonUtil.getMuchIdByAppId(appid);
		weChatSendInfo.setMch_id(much_id);
		weChatSendInfo.setNonce_str(WXPayCommonUtil.CreateNoncestr());
	    String 	spbill_create_ip= InetAddress.getLocalHost().getHostAddress();
		weChatSendInfo.setSpbill_create_ip(spbill_create_ip);
		Field[] fields= 	weChatSendInfo.getClass().getDeclaredFields();
		for(Field field : fields){
			String name = field.getName();
			if(!name.equals("osType")){
				field.setAccessible(true);
				if(null != field.get(weChatSendInfo)){
				 parameters.put(field.getName(),field.get(weChatSendInfo).toString());
				}
			}
		}
		parameters.put("appid", appid);
		String apikey = WXCommonUtil.getApiKeyByAppId(appid);
		String sign = WXPayCommonUtil.createSign("UTF-8", parameters,apikey);
		parameters.put("sign", sign);
		String requestXML = WXPayCommonUtil.getRequestXml(parameters);
		String result =WXCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
		logger.info("result:  " + result);
		result = new String(result.toString().getBytes(), "ISO-8859-1");//加上这步解决中文乱码
		Map<String, String> map = XMLUtil.doXMLParse(result);
		return map;
	}
	
		/**
		 * 调起微信支付 对回话标示的再次签名
		 * */
		@Override
		public Map<String,String> signAgain(Map<String,String> map,String osType){
			  SortedMap<String,String> appMap = new TreeMap<String,String>();
			if(map.get("return_code").equals("SUCCESS")){
				String appid = map.get("appid");
				String nonce_str = map.get("nonce_str");
				String apikey = WXCommonUtil.getApiKeyByAppId(appid);
				String much_id = WXCommonUtil.getMuchIdByAppId(appid);
				appMap.put("appid", appid);
				appMap.put("partnerid",  much_id);
				appMap.put("prepayid", map.get("prepay_id"));
				appMap.put("noncestr", nonce_str);
				appMap.put("timestamp", String.valueOf(System.currentTimeMillis()/1000));
				appMap.put("package", "Sign=WXPay"); //这里一定是 Sign=WXpay 不然IOS端在支付的时候会出现解析失败的问题
				String signnew = WXPayCommonUtil.createSign("UTF-8", appMap,apikey);
				appMap.put("sign", signnew);
				appMap.put("result", "true");
			}else{
				appMap.put("result", "false");
			}
			return appMap;
		}
		
		/**
		 * 返回微信支付 (用户向企业账户付款)  信息,在客户端调起微信支付
		 * */
		@Override
		public WeChatPayInfo  getResWechatPay(WeChatSendInfo weChatSendInfo) throws Exception{
			Map<String,String> map = getPrepayMap(weChatSendInfo,ConfigUtil.NOTIFY_URL); // 第一步调用统一下单接口 获得预支付交易回话标识
			Map<String,String> payInfoMap = signAgain(map,weChatSendInfo.getOsType());// 第二步 使用第一步中的prepay_id值再次签名
			WeChatPayInfo wechatPayInfo = getWeChatPayInfo(payInfoMap);   // 第三步组装支付信息
			return wechatPayInfo;
		}
		private  WeChatPayInfo getWeChatPayInfo(Map<String,String> payInfoMap ) throws Exception{
			WeChatPayInfo wechatPayInfo = new  WeChatPayInfo();
			 for(String key: payInfoMap.keySet()){
				  if(!key.equals("package")){// 由于package是Java保留字，所以做特殊处理
					  PropertyDescriptor pd = new PropertyDescriptor(key, wechatPayInfo.getClass());
			          Method method = pd.getWriteMethod();
			          method.invoke(wechatPayInfo, payInfoMap.get(key));
		          }else{
		        	  wechatPayInfo.setPackagevalue(payInfoMap.get("package"));
		          }
	       }
				return wechatPayInfo;
		}

		/**
		 * 用户微信提现接口
		 * */
		public WeChatDrawCashResultInfo weChatDrawCash(WeChatDrawCashInfo weChatDrawCashInfo,	Double balance,String weChat_OpenId) throws Exception {
			String appid = WXCommonUtil.getAppidByOSType(weChatDrawCashInfo.getOsType());
			String much_id = WXCommonUtil.getMuchIdByAppId(appid);
			weChatDrawCashInfo.setMchid(much_id);
			weChatDrawCashInfo.setNonce_str(WXPayCommonUtil.CreateNoncestr());
			weChatDrawCashInfo.setPartner_trade_no(UUID.randomUUID().toString().replace("-", ""));
			weChatDrawCashInfo.setAmount("100"); // 测试的时候只能一次提现 1元
		    Double   amount = Double.valueOf(weChatDrawCashInfo.getAmount());
			String ip = InetAddress.getLocalHost().getHostAddress();
			weChatDrawCashInfo.setSpbill_create_ip(ip);
		    WeChatDrawCashResultInfo weChatDrawCashResultInfo = new   WeChatDrawCashResultInfo();
		    logger.info( "amount: " + amount + "weChat_Openid: " + weChat_OpenId );
		    if(null == weChat_OpenId){
	        	weChatDrawCashResultInfo.setReturn_code("FAIL");
	        	weChatDrawCashResultInfo.setResult_code("FAIL");
	        	weChatDrawCashResultInfo.setErr_code_des("请先绑定微信再来提现");
		    }else if(balance >= amount){ // 由于今天测试需要改成 <=  正式的是 >= 要注意
	        	weChatDrawCashResultInfo =  getResWeChatDrawCash(weChatDrawCashInfo);
	            String error_code =  weChatDrawCashResultInfo.getErr_code();
	           logger.info("ERRORCODEDESC: " +  weChatDrawCashResultInfo.getErr_code_des());
	        }else{
	        	weChatDrawCashResultInfo.setReturn_code("FAIL");
	        	weChatDrawCashResultInfo.setResult_code("FAIL");
	        	weChatDrawCashResultInfo.setErr_code_des("余额不足");
	        }
			return weChatDrawCashResultInfo;
		}

		/**
		 * 返回提现信息给前端
		 * */
		@Override 
		public WeChatDrawCashResultInfo getResWeChatDrawCash(WeChatDrawCashInfo  weChatDrawCashInfo) throws Exception{	
			SortedMap<String,String> parameters = new TreeMap<String,String>();
			Field[] fields= 	weChatDrawCashInfo.getClass().getDeclaredFields();
			for(Field field : fields){
				String name = field.getName();
				if(!name.equals("osType")){
					field.setAccessible(true);
					if(null != field.get(weChatDrawCashInfo)){
					 parameters.put(field.getName(),field.get(weChatDrawCashInfo).toString());
					}
				}
			}
			String osType = weChatDrawCashInfo.getOsType();
			String much_appid = WXCommonUtil.getAppidByOSType(osType);
			String apikey = WXCommonUtil.getApiKeyByAppId(much_appid);
			String much_id = WXCommonUtil.getMuchIdByAppId(much_appid);
			parameters.put("mch_appid",much_appid );
			String sign = WXPayCommonUtil.createSign("UTF-8", parameters,apikey);
			parameters.put("sign", sign);
			String requestXML = WXPayCommonUtil.getRequestXml(parameters);
			String caName  = WXCommonUtil.getCaNameByAppId(much_appid);
	        logger.info("osType:  " +osType);
	        logger.info("much_appid:   "  + much_appid);
	        logger.info("apikey:  " +apikey );
	        logger.info("caName: " +caName);
	        logger.info("much_id: " +much_id);
			String result =WXCommonUtil.httpsRequestCA(ConfigUtil.ENTRPRISE_PAY_URL, "POST", requestXML,caName,much_id);
	        logger.info("resulttixian:" +result);
			Map<String, String> drawInfoMap = XMLUtil.doXMLParse(result);
			WeChatDrawCashResultInfo weChatDrawCashResultInfo = new  WeChatDrawCashResultInfo();
			 for(String key: drawInfoMap.keySet()){
				  PropertyDescriptor pd = new PropertyDescriptor(key, weChatDrawCashResultInfo.getClass());
		          Method method = pd.getWriteMethod();
		          method.invoke(weChatDrawCashResultInfo, drawInfoMap.get(key)); 
	       }
				return weChatDrawCashResultInfo;
		}

		
		/**微信付款成功后 回调验证签名*/

		@Override
		public Boolean isValidSign(Map<String, String> map) throws Exception {
			SortedMap<String,String> appmap=new TreeMap<String,String>(map);  
			String sign  = appmap.get("sign");
			String appid  = appmap.get("appid");
			String return_code = appmap.get("return_code");
			String apiKey  = WXCommonUtil.getApiKeyByAppId(appid);
			String validSign = WXPayCommonUtil.createSign("UTF-8", appmap, apiKey);
	         if(validSign.equals(sign) && return_code.equals("SUCCESS")){
	        	 SortedMap< String,String>  parameters = new TreeMap<String, String>();
	        	 parameters.put("return_code", "SUCCESS");
	        	 parameters.put("return_msg", "OK");
	        	String requestXML =  WXPayCommonUtil.getRequestXml(parameters);
	     		String result =WXCommonUtil.httpsRequest(ConfigUtil.UNIFIED_ORDER_URL, "POST", requestXML);
	     		logger.info("wechatresult :"  + result);
	        	  return true;
	         }else{
	        	 return false;
	         }
		}
}
