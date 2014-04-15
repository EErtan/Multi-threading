/**
 * Callable: producing return values from tasks, use a callable with call(), must be submitted in executor service
 * 
 * Priority: allows the scheduler to favor some waiting threads over others, but all will be called
 * 
 * Yielding: is an indicator to the thread scheduler that most critical work is done, so it can leave
 * 
 * Daemon Threads: non essential background threads started with threadName.setDaemon( true ) then threadName.start()
 * used in conjunction with a DaemonThreadFactory which extends a thread factory which is in the arg for Executors.newCachedThreadPool( new DaemonThreadFactory() );
 * or
 * extend the ThreadPoolExcutor to create your own DaemonThreadPoolExecutor with a constructor of 
 * public DaemonThreadPoolExecutor() {
		super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new DaemonThreadFactory());
	}
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
/**
 * @author ersin
 *
 */
package thinkinginjavaconcurrency;
