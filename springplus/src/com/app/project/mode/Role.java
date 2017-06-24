package com.app.project.mode;

import javax.persistence.Id;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.table.Number;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@Table(name="sys_role")
@TableCreate
public class Role implements RedisSerializable {
	@Id
	@Generated
	String id;
	@ApiDesc("角色名称")
	String name;
	@ApiDesc("角色描述")
	String des;
	
	
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
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	
}
