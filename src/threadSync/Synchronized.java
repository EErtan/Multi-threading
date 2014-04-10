package threadSync;

public class Synchronized { // solves thread interference and memory consistency
							// errors
// but can introduce thread contention, 2 or more threads access same resource
// which causes
	// one of the thread to slow down or even suspend execution - see starvation
	// and live locks
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
				// code blocks can be synchronized within the method to add
				// exclusivity to
				// critical components of the method, by passing the lock object or string
				synchronized ("Locked") {
					for (int i = 0; i < 10000; ++i) {
						synchronized (this) {
							inc();
						}
					}
				}
			}
		});
		t1.start();
		t2.start();
		try {
			t1.join(); // pauses execution in main thread until it's thread is
						// finished joining...
			t2.join(); // can override, to set waiting period but os dependent -
						// not precise
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
