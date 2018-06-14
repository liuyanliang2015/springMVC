package com.bert.common.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证签名
 * @author Administrator	
 */
public class SignUtil {
	 private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);
	 
	 private static final String signKey = "mnbzmlkalsdfffa";
	/** 
     * 签名算法sign 
     * @param characterEncoding  字符编码
     * @param parameters  参数集合
     * @return publicKey 密钥
     */  
    @SuppressWarnings("rawtypes")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
    	String sign  = "";
    	//String signKey = PropertiesSource.getProperty("signKey");
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
    
}
