package www.springmvcplus.com.modes;

import javax.persistence.Id;
import javax.persistence.Table;



import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.table.Char;
import www.springmvcplus.com.common.table.Number;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@TableCreate
@Table(name="sys_request")
public class RequestMode implements RedisSerializable {
	@Id
	@Generated
	@Char(36)
	String id;
	@Text
	String url;
	@Number
	long startTime;
	@Number
	long endTime;
	@Number
	long threadId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public long getthreadId() {
		return threadId;
	}
	public void setthreadId(long threadId) {
		this.threadId = threadId;
	}
	
}
