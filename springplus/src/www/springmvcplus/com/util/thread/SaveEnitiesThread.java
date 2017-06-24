package www.springmvcplus.com.util.thread;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;






import www.springmvcplus.com.modes.RequestMode;
import www.springmvcplus.com.modes.SqlMode;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.SpringBeanUtil;

public class SaveEnitiesThread extends Thread {
	List<Object> baseEntities;

	public List<Object> getBaseEntities() {
		return baseEntities;
	}

	public void setBaseEntities(List<Object> baseEntities) {
		this.baseEntities = baseEntities;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		MyService myService=SpringBeanUtil.getBean(MyService.class);
		String requestid="";
		for (Object baseEntity : baseEntities) {
			if (RequestMode.class.isInstance(baseEntity)) {
				RequestMode requestMode=(RequestMode)baseEntity;
				requestid=requestMode.getId();
				myService.execute("insert into app_request values('"+requestid+"','"+requestMode.getUrl().replaceAll("'", "\''")+"',"+requestMode.getStartTime()+","+requestMode.getEndTime()+","+requestMode.getthreadId()+")");
			}
			if (SqlMode.class.isInstance(baseEntity)) {
				SqlMode sqlMode=(SqlMode)baseEntity;
				myService.execute("insert into app_sql values('"+requestid+"','"+sqlMode.getSql().replaceAll("'", "\''")+"',"+sqlMode.getStartTime()+","+sqlMode.getEndTime()+",'"+sqlMode.isIsAsyn()+"','"+sqlMode.isRedis()+"')");
			}
		}
		
	}
	
}
