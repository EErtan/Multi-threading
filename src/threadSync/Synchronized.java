package threadSync;

public class Synchronized {

	private volatile long count = 0;

	public static void main(String... strings) {

		Synchronized s = new Synchronized();
		s.doWork();
		s.printC();
	}

	public void doWork() {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; ++i) {
					inc();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 10000; ++i) {
					inc();
				}
			}
		});
		
		t1.start();
		t2.start();
		
		try {
			t1.join(); // pauses execution in main thread until it's thread is finished
			t2.join(); // then this one triggers 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	private synchronized void inc() {
		++count;
	}

	public void printC() {
		System.out.println(count);
	}
}
