/**
 * 文件名:RandomUtil.java 
 * 工程名：lsp
 * 包名:com.lzz.lsp.gas.util
 * 作者:Administrator
 * 创建时间:2017年9月6日
 * Copyright (C) 2017 绿蜘蛛科技有限公司
 */
package com.bert.common.util;

import java.util.Random;

/**
 * 随机数工具类
 * @author Administrator	
 */
public class RandomUtil {
	
	/**
	 * 生成length长度的字母跟数字混合的随机数
	 * Administrator
	 * @param length
	 * @return 
	 * 2017年9月6日 下午4:45:37
	 */
	public static String getRandomString(int length) {
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }     
	
	/**
	 * 生成length长度的数字随机数
	 * Administrator
	 * @param length
	 * @return 
	 * 2017年9月6日 下午4:46:19
	 */
	public static String getRandomNum(int length) {
		StringBuffer charValue = new StringBuffer();
		for (int i = 0; i < length; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue.append(c);
		}
		return charValue.toString();
	}

	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}

	
}
