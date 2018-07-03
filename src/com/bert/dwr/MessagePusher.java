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
        	//WebContextFactory.get()获取WebContext对象
        	//WebContext.getScriptSession()获取scriptSession对象
        	ScriptSession scriptSession = WebContextFactory.get().getScriptSession();
        	//因为打开新的浏览器tab，相当于创建了多个scriptSession，不会存在覆盖的情况
        	scriptSession.setAttribute("userId", userId);
        	System.out.println("添加了一个scriptSession：  "+userId);
        }
	}
	
	/**
	 *  访问一个页面的时候，如果是第一次访问，就会创建一个新的HttpSession,之后再访问的时候，就会保持当前的Session,即使是刷新，也能保持当前的HttpSession。
	 *  但是，ScriptSession不同，第一次访问，会创建一个ScriptSession,但是，如果你刷新，就会创建一个新的ScriptSession.
	 * @param userId
	 * @param message
	 */
	public void sendMessage(final String userId, final String message) {
		try {
			final String sendUserId = userId;
	        final String msg = message;
			Browser.withAllSessionsFiltered(new ScriptSessionFilter() {
				//如果返回true,那将匹配的ScriptSession添加到Browser.getTargetSessions()中,待后面调用
				public boolean match(ScriptSession session) {
					String sessionUserId = (String) session.getAttribute("userId");
					boolean flag = false;
					if (sendUserId == null) {
						flag =  false;
					} else if ("0".equalsIgnoreCase(sendUserId)) {
						flag =  true;
					} else {
						flag = sessionUserId.equalsIgnoreCase(sendUserId);
					}
					return flag;
				}
			}, new Runnable() {
				private ScriptBuffer script = new ScriptBuffer();
				public void run() {
					//对应页面调用的方法名称及参数
					//System.out.println("begin to call showMessage()");
				    script.appendCall("showMessage", msg);
				    //得到当前浏览器下所有ScriptSession
					Collection<ScriptSession> sessions = Browser.getTargetSessions();
					System.out.println("ScriptSession size :" + sessions.size());
					for (ScriptSession scriptSession : sessions) {
						//添加待执行的脚本到dwr excution池中
						scriptSession.addScript(script);
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
