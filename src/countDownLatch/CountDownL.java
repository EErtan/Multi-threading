package countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Runn implements Runnable {
	CountDownLatch cdl = null;

	Runn(CountDownLatch cdl) {
		this.cdl = cdl;
	}

	@Override
	public void run() {
		System.out.println("Counting");
		try {
			for (int i = 0; i < 3; ++i) {
				System.out.print(".");
				Thread.sleep(300);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cdl.countDown();
	}
}

public class CountDownL { // this class is for recieving when the latched runnables are done ex. uploading pics, then its

	public static void main(String... strings) {
		CountDownLatch latch = new CountDownLatch(3);

		ExecutorService es = Executors.newFixedThreadPool(3);

		for (int i = 0; i < 3; ++i) {
			es.submit(new Runn(latch));
		}

		try {
			System.out.println("waiting");
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
	}
}
