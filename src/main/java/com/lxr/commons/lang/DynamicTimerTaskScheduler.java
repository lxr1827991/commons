package com.lxr.commons.lang;

import java.util.Timer;
import java.util.TimerTask;

import com.lxr.commons.lang.DynamicTimerTask.DynamicTimerTaskListening;

public class DynamicTimerTaskScheduler extends Timer implements DynamicTimerTaskListening{
	
	DynamicTimerTask dynamicTask;
	
	public DynamicTimerTaskScheduler(DynamicTimerTask task) {
		this(task,true);
	}
	
	public DynamicTimerTaskScheduler(DynamicTimerTask task,boolean isDeamon) {
		super(isDeamon);
		dynamicTask = task;
		task.setListening(this);
	}
	
	
	public void start() {
	
		schedule(getTimerTask(),0);

	}

	
	private TimerTask getTimerTask() {
		return new TimerTask() {
			
			@Override
			public void run() {
				dynamicTask.run();
				
			}
		};
	}
	
	

	@Override
	public void onFinsh(DynamicTimerTask task) {
		
		int time =  dynamicTask.getNextTime();
		
		if(time==-1){
			cancel();
			return;
		}
			
		schedule(getTimerTask(), time);
		
		
	}
	
	
	
	

}
