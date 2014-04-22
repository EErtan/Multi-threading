package futureandcallable;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class FC { // callable and future allow you to get return results from your threads and allows thread code to throw exceptions
	public static void main(String...strings){
		ExecutorService es = Executors.newCachedThreadPool();
		
		Future<?> future = es.submit(new Callable<Void>(){ // using wild card and the void allows you to use methods of future while returning null
			public Void call() throws Exception{ 
				Random r = new Random();
				int duration = r.nextInt(4000);
				
				if(duration > 2000){ throw new IOException("broke"); }
				
				System.out.println("starting ...");
				
				Thread.sleep(duration);
				
			
				System.out.println("done...");
				
				return null;
			}
		});
		
		es.shutdown();
		
		
		/*try {
			es.awaitTermination(10, TimeUnit.SECONDS); // or can wait here for the thread to finish
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		try{
			System.out.println("result is " + future.get()); // will block until terminated
		}catch(InterruptedException e){ e.printStackTrace(); } 
		catch(ExecutionException e){ System.out.println(e.getMessage());
		// or
		IOException ex = (IOException) e.getCause();
		System.out.println(ex.getMessage());
		} 
		
		if(future.isDone()) System.out.println(future.isDone() + "im done");
	}

}
