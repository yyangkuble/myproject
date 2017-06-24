package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;

/**
 * @author Administrator
 *
 */
@Entity
@TableCreate
@ApiDesc("拜访客户记录")
public class UserVisitLog {
	@Generated
	@Id
	@ApiDesc("拜访id  自动生成")
	String id;
	@ApiDesc("客户id")
	String customId;
	@ApiDesc("拜访情况")
	String visitContext;
	@ApiDesc("拜访时间")
	String visitTime;
	@Text
	@ApiDesc("多张使用,分割")
	String imgUrls;
	
	
	public String getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(String imgUrls) {
		this.imgUrls = imgUrls;
	}
	public String getId() {
		return id;
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
	public String getVisitContext() {
		return visitContext;
	}
	public void setVisitContext(String visitContext) {
		this.visitContext = visitContext;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	
	
	
}
