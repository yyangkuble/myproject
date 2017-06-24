package www.springmvcplus.com.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		applicationContext=arg0;
	}
	
	public static <T> T getBean(Class<T> beanClass) {
		return applicationContext.getBean(beanClass);
	}
	
	public static <T> T getBean(String beanName, Class<T> beanClass) {
		return applicationContext.getBean(beanName,beanClass);
	}

}
