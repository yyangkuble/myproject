package www.springmvcplus.com.util;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public abstract class MyTask extends TimerTask {
	/**
	 * 设置开始时间
	 * @return
	 */
	public abstract Date getStartTime();
	/**
	 * 设置间隔时间
	 * @return
	 */
	public abstract long getIntervalTime();
	/**
	 * 是否循环
	 * @return
	 */
	public abstract boolean getIsLoop();
	
	
	//添加定时器
	public static void task(Map<String, MyTask> map) {
		for (MyTask myTask : map.values()) {
			Timer timer = new Timer(true);
			if (myTask.getIsLoop()) {
				timer.schedule(myTask, myTask.getStartTime(), myTask.getIntervalTime());
			}else {
				timer.schedule(myTask, myTask.getStartTime());
			}
		}
	}
	
}
