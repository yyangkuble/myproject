package www.springmvcplus.com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.util.system.SerializeUtil;

public class SessionUtil {
	public static JedisPool jedisPool=null;
	public static JedisPool getJedisPool() {
		if (jedisPool==null) {
			  JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxIdle(-1);
	            config.setTestOnBorrow(true);
	            //config,host,port,timeout,password,dbname
	            SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
	            jedisPool = new JedisPool(config, springMVCPlusArgsConfig.getSessionRedisHost(), springMVCPlusArgsConfig.getSessionRedisPort(),4000,null,2);
		}
		return jedisPool;
	}
	
	public static Object getAttribute(HttpServletRequest request,String attrName) {
		HttpSession session=request.getSession();
		JedisPool jedisPool1=getJedisPool();
		Jedis jedis=jedisPool1.getResource();
		Object obj = SerializeUtil.unserialize(jedis.get((session.getId()+"_"+attrName).getBytes()));
		return obj;
	}
	
	public static void setAttribute(HttpServletRequest request,String attrName,Object value) {
		HttpSession session=request.getSession();
		JedisPool jedisPool1=getJedisPool();
		Jedis jedis=jedisPool1.getResource();
		jedis.set((session.getId()+"_"+attrName).getBytes(),SerializeUtil.serialize(value));
	}
}
