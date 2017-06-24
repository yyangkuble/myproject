package com.app.project.mode;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;




import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

@Entity
@ApiDesc("团队公告")
@TableCreate
public class GroupNotice {
	@Id
	@Generated
	@ApiDesc("公告id  自动生成")
	String id;
	@ApiDesc("团队id")
	String groupId;
	@ApiDesc("公告内容")
	String noticeText;
	@ApiDesc("创建时间  服务器自动生成")
	String createTime;
	@ApiDesc("用户id")
	String userId;
	@Text
	@ApiDesc("图片地址，多张用,号隔开")
	String imgurls;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getNoticeText() {
		return noticeText;
	}
	public void setNoticeText(String noticeText) {
		this.noticeText = noticeText;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
}
