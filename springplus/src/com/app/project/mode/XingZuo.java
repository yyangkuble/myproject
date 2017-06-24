package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@ApiDesc("星座")
@Entity
@TableCreate
public class XingZuo {
	@Id
	Integer id;
	@ApiDesc("星座")
	String xingzuo;
	@ApiDesc("时间范围")
	String time;
	@ApiDesc("特点")
	String tedian;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getXingzuo() {
		return xingzuo;
	}
	public void setXingzuo(String xingzuo) {
		this.xingzuo = xingzuo;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTedian() {
		return tedian;
	}
	public void setTedian(String tedian) {
		this.tedian = tedian;
	}
	
	
	
}
