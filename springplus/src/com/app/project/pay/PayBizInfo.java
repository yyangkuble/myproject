package com.app.project.pay;

import www.springmvcplus.com.util.IdManage;

public class PayBizInfo {
	String body;//商品信息的描述
	String subjec;//商品信息的标题
	String out_trade_no=IdManage.getTimeUUid();//订单编号，系统生成
	String total_amount;//订单金额
	String product_code="QUICK_MSECURITY_PAY";
	String goods_type="0";
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubjec() {
		return subjec;
	}
	public void setSubjec(String subjec) {
		this.subjec = subjec;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	
	
}
