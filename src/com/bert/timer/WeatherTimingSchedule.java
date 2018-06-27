package com.bert.timer;

import java.util.Date;

import com.bert.common.util.RandomUtil;
import com.bert.dwr.MessagePusher;

public class WeatherTimingSchedule {
	 //定时执行的方法
    public void execute(){
        System.out.println("执行时间"+ new Date());
        MessagePusher push = new MessagePusher();
        push.sendMessage("1", "天气信息:"+RandomUtil.getRandomString(16));
    }
}
