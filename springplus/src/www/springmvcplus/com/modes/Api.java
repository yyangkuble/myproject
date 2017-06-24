package www.springmvcplus.com.modes;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;



import www.springmvcplus.com.common.Generated;
import www.springmvcplus.com.common.Generated.GeneratedType;
import www.springmvcplus.com.common.RedisSerializable;
import www.springmvcplus.com.common.table.Char;
import www.springmvcplus.com.common.table.TableCreate;
import www.springmvcplus.com.common.table.Text;
import www.springmvcplus.com.common.table.Varchar;
import www.springmvcplus.com.common.table.TableCreate.TableCreateType;

@TableCreate(TableCreateType.isNotExistsCreate)
@Table(name ="sys_api")
public class Api implements RedisSerializable {
	@Id
	@Generated(GeneratedType.uuid)
	@Varchar
	String id;
	String modename;
	@Text
	String requestparam;
	@Text
	String url;
	@Varchar(1000)
	@Column(name="describle")
	String desc;
	String apitype;
	
	
	
	public String getApitype() {
		return apitype;
	}
	public void setApitype(String apitype) {
		this.apitype = apitype;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModename() {
		return modename;
	}
	public void setModename(String modename) {
		this.modename = modename;
	}
	public String getRequestparam() {
		return requestparam;
	}
	public void setRequestparam(String requestparam) {
		this.requestparam = requestparam;
	}
	
	
}
