package www.springmvcplus.com.util.system;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.ibm.db2.jcc.am.s;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import www.springmvcplus.com.common.SpringMVCPlusArgsConfig;
import www.springmvcplus.com.services.AppFastInit;
import www.springmvcplus.com.util.SpringBeanUtil;

public class RedisManager {
	public static Logger logger = LoggerFactory.getLogger(RedisManager.class);
	public static JedisPool jedisPool=null;
	public static JedisPool getJedisPool() {
		if (jedisPool==null) {
			  JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxIdle(-1);
	            config.setTestOnBorrow(true);
	            //config,host,port,timeout,password,dbname
	            SpringMVCPlusArgsConfig springMVCPlusArgsConfig=SpringBeanUtil.getBean(SpringMVCPlusArgsConfig.class);
	            jedisPool = new JedisPool(config, springMVCPlusArgsConfig.getRedisHost(), springMVCPlusArgsConfig.getRedisPort(),4000,null,1);
		}
		return jedisPool;
	}
	public static void flushRedis() {
		JedisPool jedisPool1=getJedisPool();
		Jedis jedis=jedisPool1.getResource();
		jedis.flushAll();
	}
	
	public static void saveSelectValue(String sql,Object value) {
		boolean isHaveNoCacheTable=false;
		String tablenamesStr="";
		Set<String> tablenames = getTableName(sql);
		for (String tablename : tablenames) {//判断是否包含不缓存的表，如果包含，就不做缓存
			tablenamesStr+="#"+tablename.toLowerCase();
			if (AppFastInit.noRedisTables.contains("##"+tablename.toLowerCase()+"##")) {
				isHaveNoCacheTable=true;
			}
		}
		if (!isHaveNoCacheTable) {
			JedisPool jedisPool1=getJedisPool();
			Jedis jedis=jedisPool1.getResource();
			//jedis.expire(userid, -1);//��������
			try {
				String key=sql+tablenamesStr;
				jedis.setex(key.getBytes(),60*60*24, SerializeUtil.serialize(value));
				logger.info("存储缓存:"+key);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if (jedisPool1 != null && jedis != null) {
					jedisPool1.returnResource(jedis);
				}
			}
		}
	}
	
	/**
	 * ����ֵ
	 * @param userid
	 * @param value
	 */
	public static void delRedis(String sql) {
		boolean isHaveNoCacheTable=false;
		Set<String> tablenames = getTableName(sql);
		for (String tablename : tablenames) {//判断是否包含不缓存的表，如果包含，就不做删除缓存操作
			if (AppFastInit.noRedisTables.contains("##"+tablename.toLowerCase()+"##")) {
				isHaveNoCacheTable=true;
			}
		}
		if (!isHaveNoCacheTable) {
			JedisPool jedisPool1=getJedisPool();
			Jedis jedis=jedisPool1.getResource();
			//jedis.expire(userid, -1);//��������
			try {
				for (String tablename : tablenames) {//循环update insert delete语句获取所有的表名称，其实只有一个表名称
					Set<byte[]> keys=jedis.keys(("*#"+tablename.toLowerCase()+"*").getBytes());//获取所有含有此表名称的key，搜索出来
					for (byte[] key : keys) {
						jedis.del(key);
						logger.info("删除key缓存:"+new String(key));
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if (jedisPool1 != null && jedis != null) {
					jedisPool1.returnResource(jedis);
				}
			}
		}
	}
	/**
	 * ��ȡֵ
	 * @param userid
	 * @return
	 */
	public static <T> T get(String sql,Class<T> clazz) {
		boolean isHaveNoCacheTable=false;
		Set<String> tablenames = getTableName(sql);
		String tablenamesStr="";
		for (String tablename : tablenames) {//判断是否包含不缓存的表，如果包含，就不做查询缓存操作
			tablenamesStr+="#"+tablename.toLowerCase();
			if (AppFastInit.noRedisTables.contains("##"+tablename.toLowerCase()+"##")) {
				isHaveNoCacheTable=true;
			}
		}
		if (!isHaveNoCacheTable) {
			JedisPool jedisPool1=getJedisPool();
			Jedis jedis=jedisPool1.getResource();
			String key=sql+tablenamesStr;
			byte[] value=jedis.get(key.getBytes());
			try {
				if(value==null){
					return null;
				}else {
					logger.info("使用缓存:"+key);
					return clazz.cast(SerializeUtil.unserialize(value));
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				if (jedisPool1 != null && jedis != null) {
					jedisPool1.returnResource(jedis);
				}
			}
		}
		return null;
	}
	private static Pattern p = Pattern.compile(  
	        "(?i)(?<=(?:from|into|update|join)\\s{1,1000}"  
	        + "(?:w*[\\.]?\\w*(?:\\s{0,1000},\\s{0,1000})?)?" // 重复这里, 可以多个from后面的表  
	        + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"  
	        + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"  
	        + ")(\\w+)"  
	        );
	public static Set<String> getTableName(String sql) {
			Matcher m = p.matcher(sql.toLowerCase()); 
			Set<String> tablenames=new HashSet<String>();
			while (m.find()) {  
				tablenames.add(m.group());
			}  
		return tablenames;
	}
	public static void main(String[] args) {
		//put("abc", "dfdsfsd");
		//System.out.println(jedis.lrange("listDemo", 0, -1));
	}
	
}
