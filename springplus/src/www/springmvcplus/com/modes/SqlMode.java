package www.springmvcplus.com.modes;

import javax.persistence.Column;
import javax.persistence.Table;




import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.table.Char;
import www.springmvcplus.com.common.table.Number;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@TableCreate
@Table(name="sys_request_sql")
public class SqlMode implements RedisSerializable {
	@Char(36)
	String request_id;
	@Text
	@Column(name="sqlstr")
	String sql;
	@Number
	long startTime;
	@Number
	long endTime;
	boolean isAsyn=false;//是否是多线程sql
	boolean isRedis=false;
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public String getSql() {
		return sql;
	}
	public boolean isAsyn() {
		return isAsyn;
	}
	public void setAsyn(boolean isAsyn) {
		this.isAsyn = isAsyn;
	}
	public boolean isRedis() {
		return isRedis;
	}
	public void setRedis(boolean isRedis) {
		this.isRedis = isRedis;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public boolean isIsAsyn() {
		return isAsyn;
	}
	public void setIsAsyn(boolean isAsyn) {
		this.isAsyn = isAsyn;
	}
	
}
