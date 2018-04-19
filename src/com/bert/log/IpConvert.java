package com.bert.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;


/**
 * @author Administrator	
 */
public class IpConvert extends ClassicConverter{
	@Override
	public String convert(ILoggingEvent event) {
		 return "10.10.10.10";
	}

}
