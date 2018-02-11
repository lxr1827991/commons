package commons;

import com.lxr.commons.lang.DynamicTimerTask;
import com.lxr.commons.lang.DynamicTimerTaskScheduler;

public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		
		DynamicTimerTaskScheduler scheduler = new DynamicTimerTaskScheduler(new s());
		scheduler.start();
		

		
	}
	
	
static class s extends DynamicTimerTask{
	
	int[] time = new int[]{15,15,30,180,1800,1800,1800,1800,3600};
	
	int cindex = 0;
	

	@Override
	public int getNextTime() {
		if(cindex>=time.length-1)
			return -1;
		
		cindex++;
		
		
		return time[cindex];
	}

	@Override
	public void work() {
		System.out.println(cindex+","+time[cindex]);
		
	}
	
	
}

}
