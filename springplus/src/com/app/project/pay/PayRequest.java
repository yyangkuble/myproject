package com.app.project.pay;

import javax.servlet.http.HttpServletRequest;

import www.springmvcplus.com.util.DateUtil;

public class PayRequest {
	
	public PayRequest(HttpServletRequest request) {
		// TODO Auto-generated constructor stub
		String path = request.getContextPath();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
		this.notify_url=basePath+this.notify_url;
	}
	
	String app_id;
	String method="alipay.trade.app.pay";
	String charset="UTF-8";
	String sign_type="RSA2";
	String sign="";
	String timestamp=DateUtil.getDate();
	String version="1.0";
	String notify_url="pay/alipay";
	PayBizInfo biz_content;
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public PayBizInfo getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(PayBizInfo biz_content) {
		this.biz_content = biz_content;
	}
	
	
	
}
