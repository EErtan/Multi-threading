package waitandnotify;

class Pro {
	public void producer() {
		synchronized (this) {
			System.out.println("Producer running \nWait called");
			try {
				wait(); // hands over control of lock
				// only be used in synchronized code, exits here
			System.out.println("wait returned");

			}catch (InterruptedException e){e.printStackTrace();}
		}
	}

	public void consumer() {
		System.out.println("start consumer");

		try {
			for (int i = 0; i < 10; ++i) {
				System.out.print('.');
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		synchronized (this) { // same lock
			notify(); // only be used in synchronized code
			// notifys the other thread to wake and use the lock
			// finished then returns to wait
			System.out.println("\nconsumer done");
		}
	}
}

public class WaitAndNotify {

	public static void main(String... strings) {
		final Pro p = new Pro();

		Thread t1 = new Thread() {
			@Override
			public void run() {
				p.producer();
			}
		};

		Thread t2 = new Thread() {
			@Override
			public void run() {
				p.consumer();
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
