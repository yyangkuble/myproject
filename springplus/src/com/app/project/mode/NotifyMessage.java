package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@Entity
@ApiDesc("通知消息")
public class NotifyMessage {
	@Id
	@Generated
	String id;
	@ApiDesc("头像地址")
	String imgUrl;
	@ApiDesc("标题")
	String title;
	@ApiDesc("详情")
	String context;
	@ApiDesc("通知类型  1： 加入团队申请      2：日志评论  3：普通问题回答被点赞   4:回答普通问题    5：回答付费提问    6日志点赞 ")
	String notifyType;
	@ApiDesc("需要时使用，保存点击通知需要的数据")
	String jsonData;
	@ApiDesc("触发通知的用户id")
	String fromUserId;
	@ApiDesc("要通知的用户id")
	String toUserId;
	@ApiDesc("是否已读  0：未读  1：已读")
	Integer isread;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	
	public String getNotifyType() {
		return notifyType;
	}
	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}
	public String getJsonData() {
		return jsonData;
	}
	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public Integer getIsread() {
		return isread;
	}
	public void setIsread(Integer isread) {
		this.isread = isread;
	}
	
	
	
	
}
