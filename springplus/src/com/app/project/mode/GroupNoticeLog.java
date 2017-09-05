package com.app.project.mode;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;





import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

@Entity
@ApiDesc("团队公告")
@TableCreate
public class GroupNoticeLog {
	
	@Id
	@Generated
	String id;
	@ApiDesc("公告id  自动生成")
	String groupNoticeId;
	@ApiDesc("用户id")
	String userId;
	
	
	
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
	public String getGroupNoticeId() {
		return groupNoticeId;
	}
	public void setGroupNoticeId(String groupNoticeId) {
		this.groupNoticeId = groupNoticeId;
	}
	
}
