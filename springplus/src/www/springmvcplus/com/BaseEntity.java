package www.springmvcplus.com;

import javax.persistence.Entity;
import javax.persistence.Transient;

import www.springmvcplus.com.common.ApiDesc;
import www.springmvcplus.com.common.table.TableCreate;

@TableCreate
@Entity
public class BaseEntity {
	
	@ApiDesc("操作类型，默认可为空")
	@Transient
	Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
