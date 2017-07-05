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
public class PayAsk {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	String askUserId;//问题人
	String title;//问题标题
	@Text
	String context;//问题内容
	@Text
	String imgUrls;//问题图片  以,隔开
	Integer isNoName;//匿名  0: 不匿名  1:匿名
	Integer isPublic;//是否公开  0:不公开  1:公开
	Integer askMoney;//金额
	String askTime;//提问时间  系统自动生成
	String answerTime;//回答问题时间   系统自动生成
	String answerUserId;//回答人
	@Text
	String voiceUrls;//语音url
	String voiceTime;//语音时长
	Integer answerMoney;//收费   暂时固定为1(元)
	Integer listenNumber;//听的数量
	Integer yesNumber;//赞的数量
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAskUserId() {
		return askUserId;
	}
	public void setAskUserId(String askUserId) {
		this.askUserId = askUserId;
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
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public Integer getIsNoName() {
		return isNoName;
	}
	public void setIsNoName(Integer isNoName) {
		this.isNoName = isNoName;
	}
	public Integer getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
	public Integer getAskMoney() {
		return askMoney;
	}
	public void setAskMoney(Integer askMoney) {
		this.askMoney = askMoney;
	}
	
	public String getAskTime() {
		return askTime;
	}
	public void setAskTime(String askTime) {
		this.askTime = askTime;
	}
	public String getAnswerTime() {
		return answerTime;
	}
	public void setAnswerTime(String answerTime) {
		this.answerTime = answerTime;
	}
	public String getAnswerUserId() {
		return answerUserId;
	}
	public void setAnswerUserId(String answerUserId) {
		this.answerUserId = answerUserId;
	}
	public String getVoiceUrls() {
		return voiceUrls;
	}
	public void setVoiceUrls(String voiceUrls) {
		this.voiceUrls = voiceUrls;
	}
	public String getVoiceTime() {
		return voiceTime;
	}
	public void setVoiceTime(String voiceTime) {
		this.voiceTime = voiceTime;
	}
	public Integer getAnswerMoney() {
		return answerMoney;
	}
	public void setAnswerMoney(Integer answerMoney) {
		this.answerMoney = answerMoney;
	}
	public Integer getListenNumber() {
		return listenNumber;
	}
	public void setListenNumber(Integer listenNumber) {
		this.listenNumber = listenNumber;
	}
	public Integer getYesNumber() {
		return yesNumber;
	}
	public void setYesNumber(Integer yesNumber) {
		this.yesNumber = yesNumber;
	}
	
	
	
}
