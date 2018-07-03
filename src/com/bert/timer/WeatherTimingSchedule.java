package com.bert.timer;

import com.bert.common.util.RandomUtil;
import com.bert.dwr.MessagePusher;

public class WeatherTimingSchedule {
	 //定时执行的方法
    public void execute(){
        MessagePusher push = new MessagePusher();
        //第一个参数0，表示广播
        push.sendMessage("1", "天气预报:"+RandomUtil.getRandomString(16));
    }
}
