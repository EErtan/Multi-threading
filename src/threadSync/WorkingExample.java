package threadSync;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Worker {

	Random r = new Random();

	Object l1 = new Object();
	Object l2 = new Object();
	// lock objects

	List<Integer> al1 = new ArrayList<Integer>();
	List<Integer> al2 = new ArrayList<Integer>();

	public void stageOne() {
		synchronized (l1) {

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			al1.add(r.nextInt(100));
		}
	}

	public void stageTwo() {
		synchronized (l2) {
			// critical code here
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			al2.add(100);
		}
	}

	public void process() {
		for (int i = 0; i < 1000; ++i) {
			stageOne();
			stageTwo();
		}
	}

	public void main(String... strings) {

		long start = System.currentTimeMillis();

		Thread thread1 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}

		});
		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				process();
			}

		});
		thread1.start();
		thread2.start();
	
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long end = System.currentTimeMillis();

		System.out.println("time taken " + (end - start));
		System.out.println(al1.size() + " and " + al2.size());
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(al1);
		System.out.println(al2);
	}
}

public class WorkingExample {

	public static void main(String... strings) {

		new Worker().main(null);
	}
}
