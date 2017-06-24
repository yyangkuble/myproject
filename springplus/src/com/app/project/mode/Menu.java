package com.app.project.mode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.table.Number;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Varchar;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@TableCreate
@Table(name="sys_menu")
public class Menu implements RedisSerializable {
	@Id
	@Generated
	@ApiDesc("自动生成id，无需添加,并且是更新，删除，通过id查询的唯一标识")
	String id;
	@ApiDesc("菜单名称")
	String name;
	@ApiDesc("菜单描述")
	String des;
	@ApiDesc("是否启用，1启动，0不启用")
	@Number
	Integer isused;
	@ApiDesc("父id，为0的是一级菜单")
	@Number
	Integer parentid;
	@ApiDesc("若为2级菜单，请填写点击要打开的url")
	@Varchar(1000)
	String url;
	@ApiDesc("改变排序的代码，填写一个Float值即可")
	@www.springmvcplus.com.common.table.Double
	Double orderby;
	
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public Double getOrderby() {
		return orderby;
	}
	public void setOrderby(Double orderby) {
		this.orderby = orderby;
	}
	public void setIsused(Integer isused) {
		this.isused = isused;
	}
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
	public int getIsused() {
		return isused;
	}
	public void setIsused(int isused) {
		this.isused = isused;
	}
	
	public Integer getParentid() {
		return parentid;
	}
	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
