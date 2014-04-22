package interruptingthreads;

import java.util.Random;

public class Interrup {
	public static void main(String...string){
		System.out.println("starting...");
		
		
		Thread t = new Thread(new Runnable(){
			public void run(){
				Random r = new Random();
				for(int i = 0; i< 1E7; ++i){
					
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) { // just a flag
						System.out.println("interrupted");
						break;
					}
					
					// which is the same as
					
					if(Thread.currentThread().isInterrupted()){
						System.out.println("interrupted");
						break;
					}
					
					Math.sin(r.nextDouble());
				}
			}
		});
		
		t.start();
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		// or if using a callable you can callable.cancel(true); which will interrupt or will false which will cancel if the thread has already started
		// or executorservice.shutdownNow(); // which sets the flag
		
		t.interrupt();
		
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("done");
		
	}
}
