package com.bert.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * @author Administrator	
 */
public class ThreadNumConverter extends ClassicConverter {

	@Override
	public String convert(ILoggingEvent arg0) {
		  return String.valueOf(Thread.currentThread().getId());
	}

}
