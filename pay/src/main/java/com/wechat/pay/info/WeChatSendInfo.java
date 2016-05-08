package com.wechat.pay.info;

import java.io.Serializable;
@SuppressWarnings("serial")
public class WeChatSendInfo implements Serializable{

	private  String osType;   // APP 类型  Android  IOS
	
	private String mch_id; //微信分配的商户号 必须
	
	private String device_info ;// 终端设备号 非必须 默认web；
	
    private String nonce_str; //随机字符串 必须
  
//    private String sign; // 签名 必须
	
    private String body; // 商品描叙 必须
   
    private  String detail; //商品详情 非必须
  
    private String attach;// 附加数据，非必须
	
	private String out_trade_no;//商户订单号 商户系统内部订单的唯一标示 必须
	
	private String fee_type; //货币类型 非必须 默认是CNY
		
	private String  total_fee;// 总金额  必须  注意单位是分
	
    private  String spbill_create_ip;//终端IP 必须
	
	private String  time_start;//交易起始时间 非必须 格式 yyyyMMddHHmmss
		
	private String  time_expire;// 订单失效时间 非必须 格式 yyyyMMddHHmmss 

	private String  goods_tag; // 商品标记 非必须
	
	private String notify_url; //通知地址   必须
	
	private String trade_type; //支付类型 必须  我们的项目中是APP支付类型
	
   private String  limit_pay; //指定支付方式  非必须

  private String appid ;
	

	public String getMch_id() {
		return mch_id;
	}
	
	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
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

	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getOut_trade_no() {
		return out_trade_no;
	}
	
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
	public String getFee_type() {
		return fee_type;
	}
	
	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}
	
	public String getTotal_fee() {
		return total_fee;
	}
	
	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}
	
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	
	public String getTime_start() {
		return time_start;
	}
	
	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}
	
	public String getTime_expire() {
		return time_expire;
	}
	
	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}
	
	public String getGoods_tag() {
		return goods_tag;
	}
	
	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}
	
	public String getNotify_url() {
		return notify_url;
	}
	
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	
	public String getTrade_type() {
		return trade_type;
	}
	
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
	public String getLimit_pay() {
		return limit_pay;
	}
	
	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}
	
}

