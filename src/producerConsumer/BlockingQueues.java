package producerConsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

// using the put and take from the brockingqueue class, the methods will be initiated at the 
// next possible instance

public class BlockingQueues {
	private static BlockingQueue<Integer> bq = new ArrayBlockingQueue<Integer>(10); // q with size of ten

	public static void main(String... strings) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	private static void producer() throws InterruptedException {
		Random r = new Random();
		while (true) {
			int i = r.nextInt(100);
			bq.put(i);
			System.out.println("				put in int " + i);
		}
	}
	
	private static void consumer() throws InterruptedException {
		Random r = new Random();
		while (true) {
			Thread.sleep(100);
			if(r.nextInt(10) == 0){ // 1/10 chance
				Integer value = bq.take();
				System.out.println("taken out value " + value + " q size is " + bq.size());
			}
		}
	}

}
