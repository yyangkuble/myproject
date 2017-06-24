package com.app.project.mode;

import javax.persistence.Id;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@Table(name="sys_user_role")
@TableCreate
public class User_role implements RedisSerializable {
	@ApiDesc("用户id")
	String userid;
	@ApiDesc("角色id")
	String roleid;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	
	
}
