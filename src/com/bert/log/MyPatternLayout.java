package com.bert.log;

import ch.qos.logback.classic.PatternLayout;

/**
 * @author Administrator	
 */
public class MyPatternLayout extends PatternLayout{
	 static {    
	        defaultConverterMap.put("ip",IpConvert.class.getName());  
	        PatternLayout.defaultConverterMap.put("threadNum", ThreadNumConverter.class.getName());
	    }  
}
