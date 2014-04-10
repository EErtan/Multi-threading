package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Runn implements Runnable {

	static long idCount = 0;
	final long id = idCount++;

	@Override
	public void run() {
		System.out.println("Running " + id);
		for (int i = 0; i < 5; ++i) {
			try {
				System.out.print("." + i);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.print("\nDone ");
		printId(); // calling another function might not trigger right away... careful
	}

	public void printId() {
		System.out.println(id);
	}

}

public class ExecutorFramework {

	public static void main(String... s) {

		ExecutorService es = Executors.newFixedThreadPool(2); // input is max number of threads running concurrently
		// service with the fixed threadpool allows for thread management and
		// recycling so you do not have the overhead of creation to deal with
		// each time
		for (int i = 0; i < 6; ++i) { // will run in sets of 2 threads at a time till the 6 is completed
			es.submit(new Runn());
		}
		es.submit(new Runn()); // this will be the sixth and solo task
		
		es.shutdown(); // stops potential in-bound requests of submitted tasks to run

		// es.submit(new Runn()); // rejected execution
		
		try {
			es.awaitTermination(10, TimeUnit.SECONDS); // blocks until the time request is fulfilled, a guarantee of time for the threads to work before the main thread continues
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
