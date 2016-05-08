所有的配置信息 放在

ConfigUtil 这个类中

测试的时候需要填写好你的  appid ，商户号 mch_id  商户号   api_key  api调用密匙  notify url  回调地址


部署好你的项目之后 在浏览器地址栏输入 作为测试

http://localhost:8080/pay/wechatPay/getPayInfo?osType=android&body=adasdsadsa&out_trade_no=1231aaead1131&total_fee=100&trade_type=APP

会给你返回调起微信支付的信息参数