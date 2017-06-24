package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("vip用户支付记录")
@Entity
@TableCreate
public class UserOrderVip {
	@Generated
	@Id
	String id;
	@ApiDesc("下单时间")
	String orderTime;//下单时间
	@ApiDesc("用户id")
	String userId;
	@ApiDesc("支付金额")
	Double payMoney;//支付金额
	@ApiDesc("支付时间")
	String payTime;//支付时间
	@ApiDesc("会员开始时间")
	String vipStartTime;
	@ApiDesc("会员结束时间")
	String vipEndTime;
	@ApiDesc("支付类型  支付宝，微信")
	String payType;
	@ApiDesc("支付状态 0： 下单    1：支付完成")
	Integer payState;//支付状态    0： 下单    1：支付完成
	
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
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getVipStartTime() {
		return vipStartTime;
	}
	public void setVipStartTime(String vipStartTime) {
		this.vipStartTime = vipStartTime;
	}
	public String getVipEndTime() {
		return vipEndTime;
	}
	public void setVipEndTime(String vipEndTime) {
		this.vipEndTime = vipEndTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	
	
}
