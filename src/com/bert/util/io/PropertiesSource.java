package com.bert.util.io;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.google.gson.Gson;
import com.lzz.lsp.base.domain.SysParam;
import com.lzz.lsp.core.sysParam.service.SysParamService;
import com.lzz.lsp.util.ApplicationUtil;

/**
 * PropertiesSource(属性信息源)用来统一负责管理指定的属性文件资源信息。
 * <p>PropertiesSource继承了{@link InitializingBean}接口，会在初始化SpringBean时，使用{@link PropertiesResourceParser}来加载、解析指定路径下的属性资源文件。</p>
 * @ClassName:PropertiesSource 
 * @author shengning
 * @version 1.0
 */
public class PropertiesSource implements InitializingBean{
	
	private static Logger logger = Logger.getLogger(PropertiesSource.class);
	SysParamService sysParamService = (SysParamService) ApplicationUtil.getBean("sysParamService");
	
	private Gson gson = new Gson();

	
	private PropertiesResourceParser propertiesResourceParser;
	
	private static Map<String,String> props ;

	/**
	 * 设置属性文件的解析器
	 * @param propertiesResourceParser
	 */
	public void setPropertiesResourceParser(PropertiesResourceParser propertiesResourceParser) {
		this.propertiesResourceParser = propertiesResourceParser;
	}
	
	private void reload(){
		String propertiesSourcePath="";
		try {
			try {
				SysParam param = sysParamService.getSysParam("PROPERTIES_SOURCE");
				if(param != null){
					String propertiesSource = param.getParamValue();
					logger.info("=======>get PROPERTIES_SOURCE from db = "+propertiesSource);
					if("1".equals(propertiesSource)){
						propertiesSourcePath = "classpath:conf/props/system_config_test.properties";
					}else if("2".equals(propertiesSource)){
						propertiesSourcePath = "classpath:conf/props/system_config_online.properties";
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("==============================>PropertiesSource reload!");
			logger.info("PropertiesSource-path="+propertiesSourcePath);
			props = propertiesResourceParser.parse(propertiesSourcePath);
			logger.debug("props="+gson.toJson(props));
		} catch (ResourceParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 如果PropertiesSource中含有key的属性信息，返回{@code true}，反之返回{@code false}。
	 * @param key
	 * @return
	 */
	public static boolean containsKey(String key){
		return props.containsKey(key);
	}

	/**
	 * 获得key对应的属性值
	 * @param key
	 * @return
	 */
	public static String getProperty(String key){
		return props.get(key);
	}
	
	/**
	 * 获得key对应的属性值，如果属性值为{@code null}，那么返回 {@code defaultValue}。
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public String getProperty(String key, String defaultValue){
		return props.get(key)==null?defaultValue: props.get(key);
	}
	
	/**
	 * 如果不含有任何的属性信息，返回{@code true}，反之返回{@code false}。
	 * @return
	 */
	public static boolean isEmpty(){
		return props.isEmpty();
	}
	
	public void afterPropertiesSet() throws Exception{
		reload();
	}
}
