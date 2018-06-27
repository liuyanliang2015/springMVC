package com.bert.timer;

import java.util.Date;

import com.bert.common.util.RandomUtil;
import com.bert.dwr.MessagePusher;

public class StockTimingSchedule {
	 //定时执行的方法
    public void execute(){
        System.out.println("执行时间2"+ new Date());
        MessagePusher push = new MessagePusher();
        push.sendMessage("2", "股票信息:"+RandomUtil.getRandomString(16));
    }
}
