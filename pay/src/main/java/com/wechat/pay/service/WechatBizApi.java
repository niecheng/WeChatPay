package com.wechat.pay.service;

import java.util.Map;

import com.wechat.pay.info.WeChatDrawCashInfo;
import com.wechat.pay.info.WeChatDrawCashResultInfo;
import com.wechat.pay.info.WeChatPayInfo;
import com.wechat.pay.info.WeChatSendInfo;


public interface WechatBizApi {
	
	/**
	 * 微信支付统一下单接口
	 * **/
	public Map<String,String> getPrepayMap(WeChatSendInfo weChatSendInfo,String notify_url)throws Exception;
	
	/**
	 * 调起微信支付 对回话标示的再次签名
	 * */
	public Map<String,String> signAgain(Map<String,String> map,String osType) throws Exception;	
	
	/**
	 * 返回微信支付 (用户向企业账户付款)  信息,在客户端调起微信支付
	 * */
	public  WeChatPayInfo  getResWechatPay(WeChatSendInfo weChatSendInfo) throws Exception;
	
	/**
	 * 用户微信提现接口
	 * */
	public WeChatDrawCashResultInfo weChatDrawCash(WeChatDrawCashInfo weChatDrawCashInfo,	Double balance,String weChat_OpenId) throws Exception;
	/**
	 * 返回提现信息给前端
	 * */
	public WeChatDrawCashResultInfo getResWeChatDrawCash(WeChatDrawCashInfo  weChatDrawCashInfo) throws Exception;
	
	/**微信付款成功验证签名*/
	public Boolean  isValidSign(Map<String,String> map)  throws Exception;
}
