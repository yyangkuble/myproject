package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@ApiDesc("邀请表")
@Entity
public class UserFriendsAsk {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@ApiDesc("用户id")
	String userId;
	@ApiDesc("朋友用户电话")
	String friendTel;
	@ApiDesc("朋友用户id")
	String friendUserId;
	@ApiDesc("朋友是否开通会员")
	Integer isVip;
	@ApiDesc("创建时间 服务器自动生成")
	String createTime;
	@ApiDesc("用户得红包")
	Integer myMoney;
	@ApiDesc("朋友得红包")
	Integer friendMoney;
	
	
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
	public String getFriendTel() {
		return friendTel;
	}
	public void setFriendTel(String friendTel) {
		this.friendTel = friendTel;
	}
	public String getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(String friendUserId) {
		this.friendUserId = friendUserId;
	}
	public Integer getIsVip() {
		return isVip;
	}
	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer getMyMoney() {
		return myMoney;
	}
	public void setMyMoney(Integer myMoney) {
		this.myMoney = myMoney;
	}
	public Integer getFriendMoney() {
		return friendMoney;
	}
	public void setFriendMoney(Integer friendMoney) {
		this.friendMoney = friendMoney;
	}
	
	
}
