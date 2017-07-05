package com.app.project.mode;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;
@Table(name="user")
@TableCreate
@ApiDesc("用户管理")
public class User {
	@Id
	@Generated
	String id;
	@ApiDesc("名称")
	String name;
	@ApiDesc("用户名保留字段")
	String username;
	@ApiDesc("电话")
	String tel;
	@ApiDesc("密码")
	String password;
	@ApiDesc("地址")
	String address;
	@ApiDesc("头像地址")
	String imgUrl;
	@Text
	String rongCloudToken;
	@ApiDesc("团队id  与团队表一对一关系")
	Integer groupId;
	@ApiDesc("团队权限  0：团长  1：副团长  2： 团员")
	Integer groupAuth;
	@Transient
	@ApiDesc("融云groupId")
	String rongCloudGroupId;
	@Transient
	@ApiDesc("用户类别， 1：试用用户   2：vip用户")
	Integer userLevel;
	@Transient
	@ApiDesc("组名称")
	String groupName;
	@Transient
	@ApiDesc("通知id")
	String notifyId;
	@Transient
	@ApiDesc("1： 同意用户加入群组，2：剔除群组，3：提升权限，4：降级权限")
	Integer type;
	@ApiDesc("公司名称")
	String company;
	@ApiDesc("职位")
	String job;
	@ApiDesc("日程提醒    选项为：不提醒，15分钟前，半小时前，1小时前，2小时前，3小时前，24小时前  ，默认为半小时")
	String tripWarn;
	@ApiDesc("客户自动升级提醒，0：不是  1：是，默认为1")
	Integer customWarn;
	@ApiDesc("是否在WiFi下看视频  0：不是，1：是，，默认为1")
	Integer wifiVideo;
	@ApiDesc("是否是专家")
	Integer iszhanjia;
	
	
	public Integer getIszhanjia() {
		return iszhanjia;
	}
	public void setIszhanjia(Integer iszhanjia) {
		this.iszhanjia = iszhanjia;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getTripWarn() {
		return tripWarn;
	}
	public void setTripWarn(String tripWarn) {
		this.tripWarn = tripWarn;
	}
	public Integer getCustomWarn() {
		return customWarn;
	}
	public void setCustomWarn(Integer customWarn) {
		this.customWarn = customWarn;
	}
	public Integer getWifiVideo() {
		return wifiVideo;
	}
	public void setWifiVideo(Integer wifiVideo) {
		this.wifiVideo = wifiVideo;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNotifyId() {
		return notifyId;
	}
	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getRongCloudGroupId() {
		return rongCloudGroupId;
	}
	public void setRongCloudGroupId(String rongCloudGroupId) {
		this.rongCloudGroupId = rongCloudGroupId;
	}
	public Integer getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getGroupAuth() {
		return groupAuth;
	}
	public void setGroupAuth(Integer groupAuth) {
		this.groupAuth = groupAuth;
	}
	public String getRongCloudToken() {
		return rongCloudToken;
	}
	public void setRongCloudToken(String rongCloudToken) {
		this.rongCloudToken = rongCloudToken;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
}
