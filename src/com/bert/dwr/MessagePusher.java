package com.bert.dwr;

import java.util.Collection;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptBuffer;
import org.directwebremoting.ScriptSession;
import org.directwebremoting.ScriptSessionFilter;
import org.directwebremoting.WebContextFactory;
import org.springframework.util.StringUtils;

/**
 * MessagePusher
 */
public class MessagePusher {
	public void onPageLoad(String userId) {
        if (!StringUtils.isEmpty(userId)){
        	ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        	scriptSession.setAttribute("userId", userId);
        	System.out.println("添加了一个scriptSession：  "+userId);
        }
	}
	
	public void sendMessage(final String userid, final String message) {
		try {
			final String id = userid;
	        final String msg = message;
			Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
				//如果返回true,那将匹配的ScriptSession添加到Browser.getTargetSessions()中,待后面调用
				public boolean match(ScriptSession session) {
					String userId = (String) session.getAttribute("userId");
					if (userId == null) {
						return false;
					} else if ("0".equalsIgnoreCase(id)) {
						return true;
					} else {
						return userId.equalsIgnoreCase(id);
					}
				}
			}, new Runnable() {
				private ScriptBuffer script = new ScriptBuffer();
				public void run() {
					//对应页面调用的方法名称及参数
				    script.appendCall("showMessage", msg);
					Collection<ScriptSession> sessions = Browser.getTargetSessions();
					for (ScriptSession scriptSession : sessions) {
						scriptSession.addScript(script);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
