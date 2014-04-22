package waitandnotify;

import java.util.LinkedList;
// playing around with spacing
class DataObj{
	
	private 		LinkedList<Integer> ll 		= 	new	LinkedList();
	private 		Object 				lock	= 	new	Object();
	private final 	int 				LIMIT 	= 		10;
	
	public void producer() throws InterruptedException{
		
  		int value = 0;
		
		while ( true ){

			synchronized ( lock ){

				while ( ll.size() == LIMIT )	{ lock.wait(); } // hands over control of the synchronized block lock
				
				System.out.println( "				adding value" + value );
				ll.add( value++ );
				lock.notify(); // will notify the first other thread to lock on the same lock object, if waiting to wake up, does not give up control at this point
				// but you should do it following
			}
		}
	}
	public void consumer() throws InterruptedException{
		
		while ( true ){
			
			synchronized ( lock ){
				
				while ( ll.size() == 0 )	{ lock.wait(); } // hands over control of the synchronized block lock like a goto it leave the execution flow
				
				System.out.print( "list size " + ll.size() + " " );
				System.out.println( ll.removeFirst() + " is removed " );
				lock.notify(); // continues the flow but now the other thread will run with the lock when given the chance at end of this flow
			}
			
			Thread.sleep(2000);
		}
	}
}

public class Example {

	public static void main( String...strings ){
		
		final DataObj d = new DataObj();
		
		Thread t1 = new Thread(){
			@Override
			public void run(){
				
				try { d.producer(); }
				catch (InterruptedException e) { e.printStackTrace(); }
			}
		};
		
		
		Thread t2 = new Thread(){
			@Override
			public void run(){
				
				try { d.consumer(); }
				catch ( InterruptedException e ){ e.printStackTrace(); }
			}
		};
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
