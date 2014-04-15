package futureandcallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class C implements Callable<Integer>{
	// like a runnable but returns a result
	@Override
	public Integer call(){
		int sum = 0;
		System.out.println();
		for(int i = 0; i < 10; ++i){
			System.out.print('.');
			sum += i;
		}
		return sum;
	}
}

public class Future_Callable {

	public static void main(String...strings){
		ExecutorService es = Executors.newCachedThreadPool();
		List<Future<Integer>> list = new ArrayList<Future<Integer>>();
		
		// future is the encapsulating object that houses the result of the callable, like a monitor object, can check if the task is done
		
		for(int i = 0; i < 1000; ++i){
			Callable<Integer> c = new C();
			Future<Integer> toSubmit = es.submit(c);
			list.add(toSubmit);
		}
		
		int sum = 0;
		
		for(Future<Integer> f : list){
			try {
				sum += f.get();
				// gets the result when the work in the callable is done
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} 
		}
		System.out.println("\nthe sum is " + sum);
		es.shutdown();
	}
}
