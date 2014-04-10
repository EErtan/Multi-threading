package startingThreads;

class AThread extends Thread { // subtype
	static int instance = 0;
	final int id = instance++;

	@Override
	public void run() {
		for (int i = 0; i < 10; ++i) {
			System.out.println(id + "Thread Started: i = " + i);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class StartThread {

	public static void main(String... strings) {
		AThread at = new AThread();
		at.start();
		new AThread().start();
	}
}
