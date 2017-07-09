package com.app.project.mode;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.ibm.db2.jcc.t4.ob;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

@Entity
@ApiDesc("团队行程")
@TableCreate
public class GroupTrip {
	@Id
	@Generated
	@ApiDesc("团队行程id  自动生成")
	String id;
	@ApiDesc("事件")
	String title;
	@ApiDesc("团队号")
	Integer groupId;
	@ApiDesc("起始时间")
	String startTime;
	@ApiDesc("结束时间")
	String endTime;
	@ApiDesc("起始日期")
	String startDate;
	@ApiDesc("结束日期")
	String endDate;
	@ApiDesc("创建时间  服务器自动生成")
	String createTime;
	@ApiDesc("团队参与人员    参数传入时用,号隔开，返回时为集合方式,并且携带用户名称和id")
	Object tripUsers;
	@ApiDesc("行程类型")
	String tripType;
	@Text
	@ApiDesc("多张使用,分割")
	String imgUrls;
	@ApiDesc("备注")
	@Text
	String context;
	@ApiDesc("是否提醒： 0：不提醒，1：提醒")
	Integer isWarn;
	@Transient
	Boolean isVisitTimeUpdate=false;
	
	
	public Boolean getIsVisitTimeUpdate() {
		return isVisitTimeUpdate;
	}
	public void setIsVisitTimeUpdate(Boolean isVisitTimeUpdate) {
		this.isVisitTimeUpdate = isVisitTimeUpdate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getIsWarn() {
		return isWarn;
	}
	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTripType() {
		return tripType;
	}
	public void setTripType(String tripType) {
		this.tripType = tripType;
	}
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Object getTripUsers() {
		return tripUsers;
	}
	public void setTripUsers(Object tripUsers) {
		this.tripUsers = tripUsers;
	}
	
	
	
	
}
