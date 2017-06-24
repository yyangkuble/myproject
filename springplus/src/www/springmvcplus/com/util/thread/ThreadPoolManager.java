package www.springmvcplus.com.util.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolManager {
	public static ExecutorService threadExecutor=Executors.newCachedThreadPool();
	
	/**
	 *  此方法执行一个没有结果的方法
	 *  请注意:应为是没有执行结果的线程,主线程是不会等待 子线程执行完毕的.
	 * @param runnable
	 */
	public static void workNoResult(Runnable runnable) {
		threadExecutor.execute(runnable);
	}

	
	/**
	 * 这个方法是执行多任务 ,且每个任务都是有返回结果的
	 * 请注意1:每个任务的返回结果会放到同一个list当中,取出list中的值转换成本来返回的类型即可
	 * 请注意2:多个任务的返回结果在list中是分先后顺序的,跟放进去的list任务顺序是一一对照的,因此不会导致任务结果集错乱.
	 * 请注意3:应为主线程会等待子线程完成,在执行后面的任务,因此一定要把全部任务分配到批量任务里面.
	 * @param callableObjectList
	 * @return
	 */
	public static List<Object> workListResut(Works works) {
		List<Object> objects=new ArrayList<Object>();
		try {
			List<Future<Object>> futureObjectList= threadExecutor.invokeAll(works);
			for (Future<Object> future : futureObjectList) {
				objects.add(future.get());
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(Thread.currentThread().getClass()+"{执行中出现故障}");
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(Thread.currentThread().getClass()+"{转换类型失败}");
		}
		return objects;
	}
	
	
	//关闭worker
	public static void closeWorker() {
		threadExecutor.shutdown();
	}
}
