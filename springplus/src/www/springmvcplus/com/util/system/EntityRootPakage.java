package www.springmvcplus.com.util.system;

import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.services.EntityManager;
import www.springmvcplus.com.util.SpringBeanUtil;

public class EntityRootPakage {
	public static String getRootPackage(String entityName) {
		if (EntityManager.sysEntityList.contains(entityName)) {
			return "www.springmvcplus.com.modes.";
		}else {
			SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
			return springMVCPlusArgsConfig.getEntityRootPackage()+".";
		}
	}
}
