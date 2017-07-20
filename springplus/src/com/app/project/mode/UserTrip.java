package com.app.project.mode;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@TableCreate
@Entity
@ApiDesc("用户日程")
public class UserTrip {
	@Id
	@Generated
	@ApiDesc("主键自动生成")
	String id;
	@ApiDesc("用户外键")
	String userId;
	@ApiDesc("拜访客户id")
	String visitCustomId;
	@ApiDesc("拜访类别")
	String visitType;
	@ApiDesc("拜访项目")
	String visitProject;
	@ApiDesc("拜访时间  例如2017-06-06 12:06:09")
	String visitTime;
	@ApiDesc("拜访日期  例如2017-06-06   服务器根据visitTime自动生成")
	String visitDate;
	@ApiDesc("是否提醒： 0：不提醒，1：提醒")
	Integer isWarn;
	@Text
	@ApiDesc("备注")
	String mark;
	@ApiDesc("创建时间   服务器自动维护")
	String createTime;
	@ApiDesc("完成状态  0:未完成(已过期)  1：未完成(未过期)， 2：完成(未记录)   3：完成(已记录)  ")
	Integer state;
	@Transient
	@ApiDesc("客户名称")
	String visitCustomName;
	@Transient
	String customName;
	@Transient
	@ApiDesc("客户等级")
	String visitCustomLevel;
	@Transient
	String customLevel;
	@Transient
	@ApiDesc("超时时间")
	String triptimeout;
	@Transient
	Boolean isVisitTimeUpdate=false;
	@Transient
	String customWarn;
	
	
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public String getCustomLevel() {
		return customLevel;
	}
	public void setCustomLevel(String customLevel) {
		this.customLevel = customLevel;
	}
	public String getCustomWarn() {
		return customWarn;
	}
	public void setCustomWarn(String customWarn) {
		this.customWarn = customWarn;
	}
	public Boolean getIsVisitTimeUpdate() {
		return isVisitTimeUpdate;
	}
	public void setIsVisitTimeUpdate(Boolean isVisitTimeUpdate) {
		this.isVisitTimeUpdate = isVisitTimeUpdate;
	}
	public String getVisitCustomLevel() {
		return visitCustomLevel;
	}
	public void setVisitCustomLevel(String visitCustomLevel) {
		this.visitCustomLevel = visitCustomLevel;
	}
	public String getTriptimeout() {
		return triptimeout;
	}
	public void setTriptimeout(String triptimeout) {
		this.triptimeout = triptimeout;
	}
	public String getVisitCustomName() {
		return visitCustomName;
	}
	public void setVisitCustomName(String visitCustomName) {
		this.visitCustomName = visitCustomName;
	}
	public String getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVisitCustomId() {
		return visitCustomId;
	}
	public void setVisitCustomId(String visitCustomId) {
		this.visitCustomId = visitCustomId;
	}
	public String getVisitType() {
		return visitType;
	}
	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}
	public String getVisitProject() {
		return visitProject;
	}
	public void setVisitProject(String visitProject) {
		this.visitProject = visitProject;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public Integer getIsWarn() {
		return isWarn;
	}
	public void setIsWarn(Integer isWarn) {
		this.isWarn = isWarn;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
}
