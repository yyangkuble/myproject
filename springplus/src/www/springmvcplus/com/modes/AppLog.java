package www.springmvcplus.com.modes;

import java.lang.reflect.Field;
import java.util.Map;

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;

import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Varchar;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;
import www.springmvcplus.com.util.DateUtil;
import www.springmvcplus.com.util.SpringBeanUtil;

@Table(name="sys_log")
@TableCreate
public class AppLog implements RedisSerializable {
	String username;
	String name;
	String remoteaddress;
	@Varchar(1000)
	String des;
	String updatetime;
	public AppLog(HttpServletRequest request) {
		// TODO Auto-generated constructor stub
		SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
		Object user= request.getSession().getAttribute(springMVCPlusArgsConfig.getSessionUserKey());
		if (user != null) {
			try {
				Field fieldUsername=user.getClass().getDeclaredField(springMVCPlusArgsConfig.getUser_username());
				fieldUsername.setAccessible(true);
				this.username=(String) fieldUsername.get(user);
				Field fieldName=user.getClass().getDeclaredField(springMVCPlusArgsConfig.getUser_name());
				fieldName.setAccessible(true);
				this.name=(String) fieldName.get(user);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.remoteaddress=request.getRemoteAddr()+":"+request.getRemotePort();
			this.updatetime=DateUtil.getDate();
			this.des=request.getParameter("log");
		}
	}
	public AppLog(HttpServletRequest request,Map<String, String> map) {
		// TODO Auto-generated constructor stub
		SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
		Object user= request.getSession().getAttribute(springMVCPlusArgsConfig.getSessionUserKey());
		if (user != null) {
			try {
				Field fieldUsername=user.getClass().getDeclaredField(springMVCPlusArgsConfig.getUser_username());
				fieldUsername.setAccessible(true);
				this.username=(String) fieldUsername.get(user);
				Field fieldName=user.getClass().getDeclaredField(springMVCPlusArgsConfig.getUser_name());
				fieldName.setAccessible(true);
				this.name=(String) fieldName.get(user);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.remoteaddress=request.getRemoteAddr()+":"+request.getRemotePort();
			this.updatetime=DateUtil.getDate();
			this.des=map.get("log");
		}
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemoteaddress() {
		return remoteaddress;
	}
	public void setRemoteaddress(String remoteaddress) {
		this.remoteaddress = remoteaddress;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
