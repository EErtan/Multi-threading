package startingThreads;

class ARunnable implements Runnable{
	static int instance = 0;
	final int id = instance++;
	@Override
	public void run(){
		for(int i = 0; i<10; ++i){
			System.out.println(id + "Thread Started: i = " + i);
			try{
				Thread.sleep(100);
			}catch(InterruptedException e){ e.printStackTrace();}
		}
	}
}


public class StartRunnable{

	public static void main(String...strings){
		new Thread(new ARunnable()).start();

		new Thread(new ARunnable()).start();
		
		new Thread(new Runnable(){
			int in = 2;
			final int id = in++;
			
			@Override
			public void run(){
				for(int i = 0; i<10; ++i){
					System.out.println(id + "Thread Started: i = " + i);
					try{
						Thread.sleep(100); // not a precise measure as in can be interrupted by the os
					}catch(InterruptedException e){ e.printStackTrace();} // interrupt is when the thread stops and does something else
				}
			}
		}).start();
		
		
	}
}
