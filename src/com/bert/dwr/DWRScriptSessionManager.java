package com.bert.dwr;

import org.directwebremoting.impl.DefaultScriptSessionManager;

/**
 * DWR ScriptSession 管理器
 * DWRScriptSessionManager
 */
public class DWRScriptSessionManager extends DefaultScriptSessionManager {  
	  public DWRScriptSessionManager(){  
          //绑定一个ScriptSession增加销毁事件的监听器  
          this.addScriptSessionListener( new DWRScriptSessionListener());  
         System. out.println( "bind DWRScriptSessionListener");  
   }  
}
