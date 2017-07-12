package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("用户支付记录")
@Entity
@TableCreate
public class UserPayLog {
	@Id
	@ApiDesc("订单id")
	String id;
	String itPayId;//第三方的交易id
	@ApiDesc("下单时间")
	String PayTime;//下单时间
	@ApiDesc("用户id")
	String userId;
	String title;//购买的标题
	String context;//购买的商品详细信息
	String biz_type;//支付的业务类型，1:vip  296元，2:发表大咖问题 公开 5元 3：发表大咖问题 不公开 20元， 4：聆听大咖回答 1元
	String biz_id;//如果是1,2,3类型不填写，如果是4 填写回答问题的id
	@ApiDesc("支付状态 0： 下单    1：支付完成")
	Integer payState;//支付状态    0： 下单    1：支付完成
	String payMoney;//支付的金额
	
	
	
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	public String getItPayId() {
		return itPayId;
	}
	public void setItPayId(String itPayId) {
		this.itPayId = itPayId;
	}
	public String getPayTime() {
		return PayTime;
	}
	public void setPayTime(String payTime) {
		PayTime = payTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getBiz_type() {
		return biz_type;
	}
	public void setBiz_type(String biz_type) {
		this.biz_type = biz_type;
	}
	public String getBiz_id() {
		return biz_id;
	}
	public void setBiz_id(String biz_id) {
		this.biz_id = biz_id;
	}
	
	
	
}
