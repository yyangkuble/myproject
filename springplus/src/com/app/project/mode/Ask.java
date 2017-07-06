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
public class Ask {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	String title;//问题标题
	@Text
	String context;//问题描述
	String askType;//问题类型    营销  增员  其他
	@Text
	String imgUrls;//图片
	String userId;//问题人id
	String bestAnswerId;//最佳回答id
	Integer answerCount;//回答的数量
	Integer allYesCount;//全部赞的累积
	String createTime;
	
	
	
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getAllYesCount() {
		return allYesCount;
	}
	public void setAllYesCount(Integer allYesCount) {
		this.allYesCount = allYesCount;
	}
	public Integer getAnswerCount() {
		return answerCount;
	}
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	public String getBestAnswerId() {
		return bestAnswerId;
	}
	public void setBestAnswerId(String bestAnswerId) {
		this.bestAnswerId = bestAnswerId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getAskType() {
		return askType;
	}
	public void setAskType(String askType) {
		this.askType = askType;
	}
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
	
	
	
}
