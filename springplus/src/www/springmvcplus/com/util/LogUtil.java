package www.springmvcplus.com.util;

import org.apache.log4j.Logger;

public class LogUtil {
	public static void info(Class<?> t,Object message) {
		Logger.getLogger(t).info(message);
	}
	public static void debug(Class<?> t,Object message) {
		Logger.getLogger(t).debug(message);
	}
	public static void error(Class<?> t,Object message) {
		Logger.getLogger(t).error(message);
	}
	public static void warn(Class<?> t,Object message) {
		Logger.getLogger(t).warn(message);
	}
	public static void info(Object message) {
		Logger.getRootLogger().info(message);
	}
	public static void debug(Object message) {
		Logger.getRootLogger().debug(message);
	}
	public static void error(Object message) {
		Logger.getRootLogger().error(message);
	}
	public static void warn(Object message) {
		Logger.getRootLogger().warn(message);
	}
}
