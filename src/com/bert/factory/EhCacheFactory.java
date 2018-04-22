package com.bert.factory;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.util.StringUtils;


public class EhCacheFactory {
	
	/**
	 * cacheManager 在ctx文件中定义，并注入到 CacheFactory 中
	 */
	private static CacheManager cacheManager;
	
	/**
	 * 获取指定的缓存对象
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存KEY
	 * @return
	 */
	public static Cache getCache(String cacheName)
	{
		Cache result = null;
		try {
			if(cacheManager!=null && StringUtils.hasText(cacheName)){
				result = cacheManager.getCache(cacheName);
				if (result == null){
					cacheManager.addCache(cacheName);
					return cacheManager.getCache(cacheName);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return result;
	}

	/**
	 *  获取缓存数据
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存KEY
	 * @param clazz 缓存对象类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCacheData(String cacheName, Object cacheKey, Class<T> clazz) {
		Cache cache = getCache(cacheName);
		Element element = cache.get(cacheKey);
		return element == null ? null : (T) element.getObjectValue();
	}

	/**
	 * 获取缓存数据
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存KEY
	 * @return
	 */
	public static Object getCacheData(String cacheName, Object cacheKey) {
		Cache cache = getCache(cacheName);
		Element element = cache.get(cacheKey);
		return element == null ? null : element.getObjectValue();
	}

	/**
	 * 缓存数据
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存KEY
	 * @param data 带缓存的数据
	 */
	public static void cacheData(String cacheName, Object cacheKey, Object data) {
		Cache cache = getCache(cacheName);
		cache.put(new Element(cacheKey, data));
	}
	
	/**
	 * <p>缓存数据并强制flush将数据保存至磁盘中</p>
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存KEY
	 * @param data 待缓存的数据
	 * @author dubl 2015年8月19日
	 */
	public static void cacheFlushData(String cacheName, Object cacheKey, Object data){
		Cache cache = EhCacheFactory.getCache(cacheName);
		cache.put(new Element(cacheKey, data));
		cache.flush();
	}

	/**
	 * 移除缓存数据
	 * @param cacheName 缓存名称
	 * @param cacheKey  缓存KEY
	 */
	public static void removeData(String cacheName, Object cacheKey) {
		Ehcache cache = getCache(cacheName);
		cache.remove(cacheKey);
	}

	public void setCacheManager(CacheManager cacheManager){
		EhCacheFactory.cacheManager = cacheManager;
	}

	public CacheManager getCacheManager()
	{
		return cacheManager;
	}
}
