package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

@TableCreate
@ApiDesc("付费问答提问")
@Entity
public class Answer {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@Text
	String context;//问题描述
	String answerType;//回答类型  text,voice
	@Text
	String fileUrls;//如果answerType 是text为图片url集合,  如果answerType是voice为语音url
	String userId;//回答人
	Integer yesCount;//赞数量
	Integer commentCount;//评论数量
	String createTime;
	String askId;//问题id
	
	
	public String getAskId() {
		return askId;
	}
	public void setAskId(String askId) {
		this.askId = askId;
	}
	public String getAnswerType() {
		return answerType;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public String getFileUrls() {
		return fileUrls;
	}
	public void setFileUrls(String fileUrls) {
		this.fileUrls = fileUrls;
	}
	public Integer getYesCount() {
		return yesCount;
	}
	public void setYesCount(Integer yesCount) {
		this.yesCount = yesCount;
	}
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
	
	
}
