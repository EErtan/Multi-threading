package semaphores;

class Sem{
	private boolean signal = false; // stored internally 
	
	public synchronized void take(){ // if take() happs before release, thread which calls release will know take was called due to signal bool
		// where wait and notify might have misses when calling each other
		signal = true;
		notify();
	}
	public synchronized void release() throws InterruptedException{
		while(!signal) wait();
		signal = false;
	}
}

class SendingThread extends Thread{
	Sem s = null;
	public SendingThread(Sem s){
		this.s = s;
	}
	
	public void run(){
		while(true){
			try {
				for(int i = 0; i < 10; i++){
					System.out.print(".");
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("taken");
			s.take();
		}
	}
}

class ReceivingThread extends Thread{
	Sem s = null;
	public ReceivingThread(Sem ss){
		s = ss;
	}
	public void run(){
		while(true){
			try {
				s.release();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			try {
				System.out.println("released");
				for(int i = 0; i < 10; i++){
					System.out.print(",");
					Thread.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class SimpleSemaphores {
	// semaphore signaling
	public static void main(String ...strings){
		Sem s = new Sem();
		SendingThread sender = new SendingThread(s);
		ReceivingThread rec = new ReceivingThread(s);
		
		rec.start();
		sender.start();
	
	}
}
