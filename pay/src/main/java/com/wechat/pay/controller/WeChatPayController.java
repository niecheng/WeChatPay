package com.wechat.pay.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wechat.pay.info.WeChatPayInfo;
import com.wechat.pay.info.WeChatSendInfo;
import com.wechat.pay.service.WechatBizApi;

@Controller
@RequestMapping(value = "/wechatPay")
public class WeChatPayController {
	@Autowired
	private WechatBizApi weChatBizApi;
	@RequestMapping(value="/getPayInfo",method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public Map<String,Object> getPayInfo(String osType,String body,String out_trade_no,String total_fee,String trade_type) throws Exception{
		   WeChatSendInfo  weChatSendInfo = new WeChatSendInfo();
		  weChatSendInfo.setOsType(osType);
		weChatSendInfo.setBody(body);
		weChatSendInfo.setOut_trade_no(out_trade_no);
		weChatSendInfo.setTotal_fee(total_fee);
		weChatSendInfo.setTrade_type(trade_type);
		WeChatPayInfo wechatPayInfo = weChatBizApi.getResWechatPay(weChatSendInfo);//第三步组装支付信息，返回给APP客户端，调起支付
		 HashMap<String, Object> map = new HashMap<String,Object>();
		 map.put("wechatPayInfo", wechatPayInfo);
		return map;
	} 
}
