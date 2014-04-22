package reentrantlock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


class DataObj{ // where lock replaces the synch methods, cond replaces the use of object monitor methods

	private int 		count 	= 	0;
	private Lock 		l 		= 	new ReentrantLock();
	private Condition 	cond 	=	l.newCondition(); // is the lock wait and await synonym
	
	private void inc(){
		
		for ( int i = 0; i < 10000; ++ i ){ ++count; }
	}
	
	public void firstThread() throws InterruptedException{
		
		l.lock();
		System.out.println( "locked, cond await, hand lock over via goto second thread" );
		cond.await(); // same as wait, can only be called after a lock, hands over
		
		System.out.println( "woken, unlocked" );
		try { inc(); }
		finally { l.unlock(); }
	}
	
	public void secondThread() throws InterruptedException{
		
		Thread.sleep(100); // ensures that the firstThread locks first
		l.lock();
		System.out.println( "							aquired lock, locked, and will now call cond signal " );
		cond.signal();
		System.out.println( "							signal called, unlocked, continue to the awaiting thread" );
		try { inc(); }
		finally { l.unlock(); }
	}
	
	public void fin(){
		System.out.println( count + " is count" );
	}
}

public class ReentrantLocks {

	public static void main( String ... strings ){
		
	final DataObj d = new DataObj();
		
		Thread t1 = new Thread(){
			@Override
			public void run(){
				
				try { d.firstThread(); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		};
		
		Thread t2 = new Thread(){
			@Override
			public void run(){
				
				try { d.secondThread(); }
				catch ( InterruptedException e ){ e.printStackTrace(); }
			}
		};
		
		t1.start();		t2.start();
		
		try { t1.join();	t2.join(); }
		catch ( InterruptedException e ){ e.printStackTrace(); }
		finally { d.fin(); }
	}
}
