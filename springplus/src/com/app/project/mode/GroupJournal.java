package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

@Entity
@ApiDesc("团队日志")
@TableCreate
public class GroupJournal {
	@Id
	@Generated
	@ApiDesc("日志id  自动生成")
	String id;
	@ApiDesc("团队id")
	String groupId;
	@ApiDesc("标题")
	String title;
	@ApiDesc("正文")
	String context;
	@ApiDesc("发表时间  服务器自动生成")
	String createTime;
	@ApiDesc("发表用户id")
	String userId;
	@Text
	@ApiDesc("图片地址，多张用,号隔开")
	String imgurls;
	@Transient
	@ApiDesc("用户名称")
	String userImgUrl;
	@ApiDesc("用户名称")
	@Transient
	String userName;
	
	
	public String getUserImgUrl() {
		return userImgUrl;
	}
	public void setUserImgUrl(String userImgUrl) {
		this.userImgUrl = userImgUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getImgurls() {
		return imgurls;
	}
	public void setImgurls(String imgurls) {
		this.imgurls = imgurls;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	
}
