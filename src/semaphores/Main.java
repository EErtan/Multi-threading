package semaphores;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

// used to control access for a resource

class Connection{
	private Connection(){}
	private static Connection connection = new Connection();
	private int con = 0;
	private Semaphore sem = new Semaphore(10,true); // second param is the fairness which is fifo que for acquiring the sem
	// send signals between threads to avoid missed signals, or to guard a critical section like you would with a lock. 
	
	
	public static Connection getConnection(){
		return connection;
	}
	
	public void connect(){
		try {
			sem.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			doConnect();
		}finally{
			sem.release();
		}
		
	}
	
	public void doConnect(){
		
		synchronized(this){
			++con;
			System.out.println("curr num con  = " + con);
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace(); // if exception is called then release may never be aquired
		}
		
		synchronized (this) {
			--con;
		}
	}
}

public class Main {
	public static void main(String...strings) throws InterruptedException{
		{
			Semaphore sem = new Semaphore(0); // the val is the number of available permits of the semaphore for each individual thread to access
			
			sem.release(); // inc permits  // bit like unlock if using a semaphore with 1 permit // can call there globally from anywhere
			sem.acquire(); // dec permits, will wait if no permits available untill a release // lock
			
			
			System.out.println("Avail permits " + sem.availablePermits());
		}
		
		ExecutorService es = Executors.newCachedThreadPool();
		for(int i =0; i< 200 ; i++){
			es.submit(new Runnable(){
				@Override
				public void run(){
					Connection.getConnection().connect();					
				}
			});
		}
		es.shutdown();
		
		es.awaitTermination(1,TimeUnit.DAYS);
		
	}
}










 



