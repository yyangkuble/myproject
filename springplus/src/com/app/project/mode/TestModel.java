package com.app.project.mode;

import javax.persistence.Id;
import javax.persistence.Table;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.table.Char;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@Table(name="sys_test")
@TableCreate
public class TestModel implements RedisSerializable {
	@Id
	@Generated(GeneratedType.uuid)
	@Char(36)
	String id;
	String name;
	String username;
	String password;
	String tel;
	String addtime;
	Integer islock;
	String lastsetpasswordtime;
	Integer errorpasswordtimes;
	String appclienttype;
	String approle;
	
	public String getApprole() {
		return approle;
	}
	public void setApprole(String approle) {
		this.approle = approle;
	}
	public String getAppclienttype() {
		return appclienttype;
	}
	public void setAppclienttype(String appclienttype) {
		this.appclienttype = appclienttype;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
	public Integer getIslock() {
		return islock;
	}
	public void setIslock(Integer islock) {
		this.islock = islock;
	}
	public String getLastsetpasswordtime() {
		return lastsetpasswordtime;
	}
	public void setLastsetpasswordtime(String lastsetpasswordtime) {
		this.lastsetpasswordtime = lastsetpasswordtime;
	}
	public Integer getErrorpasswordtimes() {
		return errorpasswordtimes;
	}
	public void setErrorpasswordtimes(Integer errorpasswordtimes) {
		this.errorpasswordtimes = errorpasswordtimes;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
