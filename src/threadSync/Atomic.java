package threadSync;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomic {
// are the operations that ensure that the variable has not changed
	// ex. ++i, the first operation - read - will be atomic
	// the write will be atomic but, there is no guarantee that the value
	// is the same between the 2 operations, therefore ++i is not atomic but
	// the individual operations are
	
	// operations are atomic of type long or double if declared volatile
	
	// atomic values prevent thread interference without resorting to sync
	
	static AtomicInteger i = new AtomicInteger(0);
	public static void main(String...strings){
		System.out.println(i+ "initial");
		System.out.println(inc()+ "inc");
		System.out.println(i.get()+ "get");
		System.out.println(dec()+ "dec");
		
		System.out.println(getsetOld()+ "getold and set to 10");
		System.out.println(i+ " the set val");
		set(5);
		System.out.println(i+ "set");
		
		
	}
	
	public static int inc(){
		return i.getAndIncrement();
	}
	public static int dec(){
		return i.getAndDecrement();
	}
	public static  int get(){
		return i.get();
	}
	public static void set(int in){
		i.set(in);
	}
	public static int getsetOld(){
		return i.getAndSet(10);
	}
}
