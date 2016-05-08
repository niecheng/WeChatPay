package com.wechat.pay.info;

import java.io.Serializable;
public class WeChatPayInfo implements Serializable{
	private static final long serialVersionUID = 1L;
	private String appid;
	private String partnerid;
	private String prepayid;
	private String noncestr;
	private String timestamp;
	private String packagevalue;
	private String sign;
	private String result;
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getPackagevalue() {
		return packagevalue;
	}
	public void setPackagevalue(String packagevalue) {
		this.packagevalue = packagevalue;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

    
}
