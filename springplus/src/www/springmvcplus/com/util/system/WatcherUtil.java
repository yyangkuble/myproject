package www.springmvcplus.com.util.system;
import java.util.List;


import www.springmvcplus.com.services.service.SystemService;
import www.springmvcplus.com.util.SpringBeanUtil;
import www.springmvcplus.com.util.thread.SaveEnitiesThread;
import www.springmvcplus.com.util.thread.SaveEntityThread;
import www.springmvcplus.com.util.thread.ThreadPoolManager;
public class WatcherUtil {
	public static void insert(List<Object> baseEntities) {
		SaveEnitiesThread saveEnitiesThread=new SaveEnitiesThread();
		saveEnitiesThread.setBaseEntities(baseEntities);
		ThreadPoolManager.workNoResult(saveEnitiesThread);
	}
	public static void insert(Object baseEntity) {
		SaveEntityThread saveEnityThread=new SaveEntityThread();
		saveEnityThread.setBaseEntity(baseEntity);
		ThreadPoolManager.workNoResult(saveEnityThread);
	}
	
	
}
