package threadSync;

import java.util.Scanner;

// stops the thread from caching values
// allows other threads to access the most recent version of the data

class AThread extends Thread{
	private volatile boolean isRunning = true;
	
	public void run(){
		while(isRunning){
			System.out.println("Running");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void shutdown(){
		isRunning = false;
	}
}

public class Volatile {
	public static void main(String...strings){ // remember to ctrl + F11 to run it, not debug
		AThread a = new AThread();
		a.start();
		
		Scanner s = new Scanner(System.in);
		s.nextLine();
		
		a.shutdown();
		s.close();
	}
}
