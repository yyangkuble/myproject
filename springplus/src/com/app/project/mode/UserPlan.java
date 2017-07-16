package com.app.project.mode;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.util.DateUtil;

@Entity
@TableCreate
@ApiDesc("客户计划拜访")
public class UserPlan {
	@Id
	@Generated
	String id;
	String customId;
	String userId;
	String planDate=DateUtil.dateFormat(new Date(), "yyyy-MM");
	public String getId() {
		return id;
	}
	
	
	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public void setId(String id) {
		this.id = id;
	}
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	
	
	
}
