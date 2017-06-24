package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@ApiDesc("客户纪念日")
@Entity
public class CustomImportanceDay {
	@Id
	@Generated
	@ApiDesc("主键 自动生成")
	String id;
	@ApiDesc("客户id")
	String customId;
	@ApiDesc("纪念日内容")
	String context;
	@ApiDesc("纪念时间")
	String day;


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
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	
}
