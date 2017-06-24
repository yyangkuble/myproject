package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@Entity
@ApiDesc("团队日志")
@TableCreate
public class GroupJournalComment {
	@Id
	@Generated
	@ApiDesc("评论id  自动生成")
	String id;
	@ApiDesc("日志id")
	String journalId;
	@ApiDesc("评论内容  内容为：_Fabulous_ 为点赞，两边有下划线")
	String context;
	@ApiDesc("评论时间  服务器自动生成")
	String createTime;
	@ApiDesc("评论用户id")
	String userId;
	@Transient
	@ApiDesc("用户名称")
	String userImgUrl;
	@ApiDesc("用户名称")
	@Transient
	String userName;
	
	
	public String getUserImgUrl() {
		return userImgUrl;
	}
	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getJournalId() {
		return journalId;
	}
	public void setJournalId(String journalId) {
		this.journalId = journalId;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
