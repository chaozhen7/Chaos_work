package com.chaos.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.cache.Cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

/**
 * @author LCZ
 * 使用redis实现mybatis的二级缓存
 */
public class RedisCache implements Cache {
	
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	private String id;
	public RedisCache(final String id){
		if(id == null){
			throw new IllegalArgumentException("必须传入ID");
		}
//		System.out.println(id);
		this.id = id;
	}
	
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public void putObject(Object key, Object value) {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		JedisPool jedisPool = null;
		boolean borrowOrOprSuccess = true;
		try {
//			System.out.println(key+" -------- "+value);
			jedis = CachePool.getInstance().getJedis();
			jedisPool = CachePool.getInstance().getJedisPool();
			jedis.set(SerializeUtil.serialize(key.hashCode()), SerializeUtil.serialize(value));
		} catch (JedisConnectionException e) {
			// TODO: handle exception
			borrowOrOprSuccess = false;
			if(jedis != null){
				jedisPool.returnBrokenResource(jedis);
			}
		} finally {
			if(borrowOrOprSuccess){
				jedisPool.returnResource(jedis);
			}
		} 
	}

	public Object getObject(Object key) {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		JedisPool jedisPool = null;
		Object value = null;
		boolean borrowOrOprSuccess = true;
		try {
			
			jedis = CachePool.getInstance().getJedis();			
			jedisPool = CachePool.getInstance().getJedisPool();
			value = SerializeUtil.unserialize(jedis.get(SerializeUtil.serialize(key.hashCode())));
//			System.out.println(key+" -------- "+value);
		} catch (JedisConnectionException e) {
			// TODO: handle exception
			borrowOrOprSuccess = false;
			if(jedis !=null){
				jedisPool.returnBrokenResource(jedis);				
			}
		} finally {
			if(borrowOrOprSuccess){
				jedisPool.returnResource(jedis);
			}
		} 
		return value;
	}

	public Object removeObject(Object key) {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		JedisPool jedisPool = null;
		Object value = null;
		boolean borrowOrOprSuccess = true;
		try {
			jedis = CachePool.getInstance().getJedis();
			jedisPool = CachePool.getInstance().getJedisPool();
			value = jedis.expire(SerializeUtil.serialize(key.hashCode()), 0);
			
		} catch (JedisConnectionException e) {
			// TODO: handle exception
			borrowOrOprSuccess = false;
			if(jedis != null){
				jedisPool.returnBrokenResource(jedis);
			}
		} finally{
			if(borrowOrOprSuccess){
				jedisPool.returnResource(jedis);
			}
		}
		return value;
	}

	public void clear() {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		JedisPool jedisPool = null;
		boolean borrowOrOprSuccess = true;
		try {
			jedis = CachePool.getInstance().getJedis();
			jedisPool = CachePool.getInstance().getJedisPool();
			jedis.flushDB();
			jedis.flushAll();
		} catch (JedisConnectionException e) {
			// TODO: handle exception
			borrowOrOprSuccess = false;
			if(jedis != null){
				jedisPool.returnBrokenResource(jedis);
			}
		} finally{
			if(borrowOrOprSuccess){
				jedisPool.returnResource(jedis);
			}
		}
	}

	public int getSize() {
		// TODO Auto-generated method stub
		Jedis jedis = null;
		JedisPool jedisPool = null;
		int result = 0;
		boolean borrowOrOprSuccess = true;
		try {
			jedis = CachePool.getInstance().getJedis();
			jedisPool = CachePool.getInstance().getJedisPool();
			result = Integer.valueOf(jedis.dbSize().toString());
		} catch (JedisConnectionException e) {
			// TODO: handle exception
			borrowOrOprSuccess = false;
			if(jedis != null){
				jedisPool.returnBrokenResource(jedis);
			}
		} finally {
			if(borrowOrOprSuccess){
				jedisPool.returnResource(jedis);
			}
		}
		return result;
	}

	public ReadWriteLock getReadWriteLock() {
		// TODO Auto-generated method stub
		return readWriteLock;
	}
	
	/** 
     *  
    * @ClassName: CachePool 
    * @Description: TODO(单例Cache池) 
    * @author LCZ  
    * 
     */
	public static class CachePool {
		JedisPool pool;
		private static final CachePool CACHE_POOL = new CachePool();
		
		public static CachePool getInstance(){
			return CACHE_POOL;
		}
		
		private CachePool() {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxIdle(100);
			config.setMaxWaitMillis(10001);
//			需要配置redis
			pool = new JedisPool(config, "127.0.0.1", 6379);
			
		}
		
		public Jedis getJedis() {
			Jedis jedis = null;
			boolean borrowOrOprSuccess = true;
			try {
				jedis = pool.getResource();
			} catch (JedisConnectionException e) {
				// TODO: handle exception
				borrowOrOprSuccess = false;
				if(jedis != null){
					pool.returnBrokenResource(jedis);
				}
			} finally {
				if(borrowOrOprSuccess){
					pool.returnResource(jedis);
				}
			}
			jedis = pool.getResource();
			return jedis;			
		}
		
		public JedisPool getJedisPool() {
			return this.pool;
		}
	}
	
	/**
	 * 序列化
	 * 
	 * */
	public static class SerializeUtil {
		public static byte[] serialize(Object object){
			ObjectOutputStream oos = null;
			ByteArrayOutputStream baos = null;
			try {
				baos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(baos);
				oos.writeObject(object);
				byte[] bytes = baos.toByteArray();
				return bytes;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;			
		}
		
		public static Object unserialize(byte[] bytes){
			if(bytes == null){
				return null;
			}
			ByteArrayInputStream bais = null;
			try {
				bais = new ByteArrayInputStream(bytes);
				ObjectInputStream ois = new ObjectInputStream(bais);
				return ois.readObject();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return null;
		}
	}

}
