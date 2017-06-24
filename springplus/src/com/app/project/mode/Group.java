package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.table.TableCreate;

@Table(name="Group_")
@ApiDesc("团队信息")
@TableCreate
public class Group {
	@Id
	@Generated(GeneratedType.dbBase)
	@ApiDesc("团队号  自动生成")
	Integer id;
	@ApiDesc("团队名称")
	String groupName;
	@ApiDesc("创建时间  服务器自动生成")
	String createTime;
	@ApiDesc("对应融云的群号")
	String rongCloudGroupId;
	@ApiDesc("创建组的用户id")
	String createUserId;
	
	
	
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getRongCloudGroupId() {
		return rongCloudGroupId;
	}
	public void setRongCloudGroupId(String rongCloudGroupId) {
		this.rongCloudGroupId = rongCloudGroupId;
	}
	
	
	
}
