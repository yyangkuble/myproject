package www.springmvcplus.com.util.thread;

import www.springmvcplus.com.modes.SqlMode;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.SpringBeanUtil;

public class SaveEntityThread extends Thread {
	Object baseEntity;
	
	public Object getBaseEntity() {
		return baseEntity;
	}

	public void setBaseEntity(Object baseEntity) {
		this.baseEntity = baseEntity;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		MyService myService=SpringBeanUtil.getBean(MyService.class);
		SqlMode sqlMode=(SqlMode)baseEntity;
		myService.execute("insert into app_sql values('"+sqlMode.getRequest_id()+"','"+sqlMode.getSql().replaceAll("'", "\''")+"',"+sqlMode.getStartTime()+","+sqlMode.getEndTime()+",'"+sqlMode.isIsAsyn()+"','"+sqlMode.isRedis()+"')");
	}
}
