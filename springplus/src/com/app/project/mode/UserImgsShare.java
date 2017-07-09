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
public class UserImgsShare {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	String userId;
	@Text
	String imgUrls;//图片地址
	@Text
	String voiceUrl;//语音地址
	String voiceTime;//语音秒数
	String shareUrl;//分享的url  添加时不用
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
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	public String getVoiceTime() {
		return voiceTime;
	}
	public void setVoiceTime(String voiceTime) {
		this.voiceTime = voiceTime;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	
}
