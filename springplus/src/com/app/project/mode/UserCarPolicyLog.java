package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@ApiDesc("车险投保记录")
@Entity
public class UserCarPolicyLog {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@ApiDesc("用户id")
	String userId;
	@ApiDesc("客户id")
	String customId;
	@ApiDesc("记录时间")
	String createTime;
	@ApiDesc("车辆信息")
	String carInfo;
	@ApiDesc("车险过期时间")
	String carExpireTime;
	@Transient
	String customName;
	@Transient
	String imgurl;
	
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
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
	public String getCustomId() {
		return customId;
	}
	public void setCustomId(String customId) {
		this.customId = customId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCarInfo() {
		return carInfo;
	}
	public void setCarInfo(String carInfo) {
		this.carInfo = carInfo;
	}
	public String getCarExpireTime() {
		return carExpireTime;
	}
	public void setCarExpireTime(String carExpireTime) {
		this.carExpireTime = carExpireTime;
	}
	
	
	
	
}
