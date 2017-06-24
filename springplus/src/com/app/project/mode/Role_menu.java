package com.app.project.mode;

import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.table.TableCreate;
@Table(name="sys_role_menu")
@TableCreate
@ApiDesc("用户角色菜单")
public class Role_menu implements RedisSerializable {
	@ApiDesc("权限id")
	String roleid;
	@ApiDesc("菜单id")
	String menuid;
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	
	
}
