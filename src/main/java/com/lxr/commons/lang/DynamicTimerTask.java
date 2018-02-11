package com.lxr.commons.lang;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**  
* 可动态修改的任务 
* @date 2015年5月9日 下午1:52:15  
* @version 1.0  
*/  
public abstract class DynamicTimerTask implements Runnable{
	
	
	DynamicTimerTaskListening listening;
	
	/**
	 * 如果返回-1则中断
	 * @return
	 */
	public abstract int getNextTime();
	
	
	public abstract void work() ;
	
	
	
	@Override
	public void run() {
		work();
		if(getListening()!=null)
			listening.onFinsh(this);
		
		
	}
	
	
	
	 public DynamicTimerTaskListening getListening() {
		return listening;
	}


	public void setListening(DynamicTimerTaskListening listening) {
		this.listening = listening;
	}






	public abstract interface DynamicTimerTaskListening{

			public void onFinsh(DynamicTimerTask task);
		 
		 
	 }
	
  
}  
