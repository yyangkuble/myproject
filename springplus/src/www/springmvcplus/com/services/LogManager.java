package www.springmvcplus.com.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import www.springmvcplus.com.modes.AppLog;
import www.springmvcplus.com.services.service.MyService;
import www.springmvcplus.com.util.StringUtil;

public class LogManager {
	public static void saveLog(HttpServletRequest request,MyService myService) {
		AppLog appLog=new AppLog(request);
		if (StringUtil.hashText(appLog.getUsername()) && StringUtil.hashText(appLog.getDes())) {
			myService.save(appLog);
		}
	}
	public static void saveLog(HttpServletRequest request,Map<String, String> map,MyService myService) {
		AppLog appLog=new AppLog(request,map);
		if (StringUtil.hashText(appLog.getUsername()) && StringUtil.hashText(appLog.getDes())) {
			myService.save(appLog);
		}
	}
}
