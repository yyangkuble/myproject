package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("星座")
@Entity
@TableCreate
public class XingZuo_Sale {
	@ApiDesc("星座关联id")
	Integer xingzuoId;
	@ApiDesc("特点")
	String title;
	@ApiDesc("正文")
	String text;
	public Integer getXingzuoId() {
		return xingzuoId;
	}
	public void setXingzuoId(Integer xingzuoId) {
		this.xingzuoId = xingzuoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	
}
