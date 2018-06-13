/**
 * 文件名:VerifySign.java 
 * 工程名：lsp
 * 包名:com.lzz.lsp.gas.sign
 * 作者:Administrator
 * 创建时间:2017年9月1日
 * Copyright (C) 2017 绿蜘蛛科技有限公司
 */
package com.bert.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bert.util.io.PropertiesSource;

/**
 * 验证签名
 * @author Administrator	
 */
public class SignUtil {
	 private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
	 
	/** 
     * 微信支付签名算法sign 
     * @param characterEncoding  字符编码
     * @param parameters  参数集合
     * @return publicKey 密钥
     */  
    @SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
    	String sign  = "";
    	String signKey = PropertiesSource.getProperty("signKey");
    	try {
        StringBuffer sb = new StringBuffer();  
        Set es = parameters.entrySet();
        Iterator it = es.iterator();  
        while(it.hasNext()) {  
            Map.Entry entry = (Map.Entry)it.next();  
            String k = (String)entry.getKey();  
            Object v = entry.getValue();  
            if(null != v && !"".equals(v)   
                    && !"sign".equals(k) && !"key".equals(k)) {  
                sb.append(k + "=" + v + "&");  
            }  
        }  
        sb.append("key=" + signKey);  
        logger.info(sb.toString());
        Mac hasher = Mac.getInstance("HmacSHA256");
	    hasher.init(new SecretKeySpec(signKey.getBytes(), "HmacSHA256"));
	    byte[] hash = hasher.doFinal(sb.toString().getBytes());
	    sign = DatatypeConverter.printHexBinary(hash);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 return sign.toLowerCase();  
    }  
    
    /**
     * 验证签名
     * Administrator
     * @param outSign
     * @param characterEncoding
     * @param parameters
     * @return 
     * 2017年9月1日 下午4:34:16
     */
  /*  public static boolean verify(String outSign,String characterEncoding,SortedMap<Object,Object> parameters){
    	boolean flag = false;
    	try {
    		String inSign = createSign(characterEncoding,parameters,SignUtil.key_lzz);
    		if(outSign != null && outSign.equals(inSign)){
    			flag = true;
    		}else{
    			logger.error("sign verify fail ! innerSign->{}",inSign);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return flag;
    }*/
    
    
    /**
     * 验证签名V2
     * Administrator
     * @param outSign
     * @param characterEncoding
     * @param parameters
     * @return 
     * 2017年9月1日 下午4:34:16
     */
    public static boolean verify2(String outSign,String characterEncoding,SortedMap<Object,Object> parameters){
    	boolean flag = false;
    	try {
    		String inSign = createSign(characterEncoding,parameters);
    		if(outSign != null && outSign.equals(inSign)){
    			flag = true;
    		}else{
    			logger.error("sign verify fail ! innerSign->{}",inSign);
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return flag;
    }
    
    /**
     * 返回数据签名处理
     * Administrator
     * @param model 
     * 2017年9月7日 上午11:36:52
     */
/*    public static void dealResponseSign(Map<String, Object> model){
    	String appid = PropertiesSource.getProperty("gas_app_id");
    	String code = model.get("code").toString();
    	model.put("appid", appid);
    	model.put("nonce_str", RandomUtil.getRandomString(32));
    	model.put("timestamp", System.currentTimeMillis()/1000+"");
    	if("0".equals(code)){
    		model.put("order_id_lzz", model.get("order_id_lzz"));
    	}
    	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
    	for (Map.Entry<String, Object> entry : model.entrySet()) {  
    		parameters.put(entry.getKey(), entry.getValue());
    	} 
    	
        String characterEncoding = "UTF-8";  
    	String sign = createSign(characterEncoding, parameters,SignUtil.key_lzz);
    	model.put("sign", sign);
    	
    }*/
}
