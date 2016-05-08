package com.wechat.pay.info;

import java.io.Serializable;

@SuppressWarnings("serial")
public class WeChatDrawCashInfo implements Serializable {
	private String osType	; //系统类别
	private String mchid; //商户号 必填
	private String device_info; // 设备号 非必须
	private String nonce_str;// 随机字符串 必填
	private String partner_trade_no;//商户订单号  必填
	private String openid;// 用户openid 必填
	private String check_name;// 校验用户姓名选项 必填   只能是 NO_CHECK FORCE_CHECK OPTION_CHECK
	private String re_user_name;	//收款用户姓名  非必须 收款用户真实姓名。 如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
	private String amount;// 金额 必填 单位是分
	private String desc;// 企业付款描述信息 必填
	private String spbill_create_ip;// Ip地址 必填 
	private String mch_appid;  //授权获取的appId 必填 并不是公众号appid 
	
	public String getMch_appid() {
		return mch_appid;
	}
	public void setMch_appid(String mch_appid) {
		this.mch_appid = mch_appid;
	}
	public WeChatDrawCashInfo(){
		
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getMchid() {
		return mchid;
	}
	public void setMchid(String mchid) {
		this.mchid = mchid;
	}
	public String getDevice_info() {
		return device_info;
	}
	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getPartner_trade_no() {
		return partner_trade_no;
	}
	public void setPartner_trade_no(String partner_trade_no) {
		this.partner_trade_no = partner_trade_no;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getCheck_name() {
		return check_name;
	}
	public void setCheck_name(String check_name) {
		this.check_name = check_name;
	}
	public String getRe_user_name() {
		return re_user_name;
	}
	public void setRe_user_name(String re_user_name) {
		this.re_user_name = re_user_name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	
	
}
