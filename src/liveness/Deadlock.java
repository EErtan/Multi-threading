package liveness;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Account {
	int bal = 1000;

	public void withdraw(int amount) {
		bal -= amount;
	}

	public void deposit(int amount) {
		bal += amount;
	}

	public int getBal() {
		return bal;
	}

	public static void transfer(Account a, Account aa, int amount) {
		a.withdraw(amount);
		aa.deposit(amount);
	}
}

public class Deadlock { // two or more threads are blocked forever waiting for
						// each other
	// prone to happend when two threads do an operation at the same time which
	// require
	// input from the other to continue
	static Random r = new Random();
	static Account a = new Account();
	static Account aa = new Account();

	static Lock l = new ReentrantLock();
	static Lock ll = new ReentrantLock();

	public static void acquireLocks(Lock fl, Lock sl) throws InterruptedException{
		while(true){
			boolean haveFirst = false;
			boolean haveSecond = false;
			
			try{
				haveFirst = fl.tryLock(1, TimeUnit.SECONDS); 
				haveSecond = sl.tryLock(1, TimeUnit.SECONDS); 
				// ensures that the lock times out if taking too long,
				// this should be done any possible concurrent mechanism 
			}
			finally{
				if(haveFirst && haveSecond) // success have both
					return;
				if(haveFirst)
					fl.unlock();
				if(haveSecond)
					sl.unlock();
			}
			// otherwise did not succeed in getting both locks
			Thread.sleep(1);
		}
	}
	
	public static void main(String... strings) {
		new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					t1();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();;
		new Thread(new Runnable(){
			@Override
			public void run(){
				try {
					t2();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();;
		
		try {
			fin();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void t1() throws InterruptedException {
		for (int i = 0; i < 1000; ++i) {
			acquireLocks(l,ll); // solution to deadlock as you separate the lock mech ensure a sequential lock grab
			// always lock your locks in the same order to avoid deadlock
			try {
				Account.transfer(a, aa, r.nextInt(100));
			} finally {
				l.unlock();
				ll.unlock();
			}
		}
	}

	public static void t2() throws InterruptedException {
		for (int i = 0; i < 1000; ++i) {
			acquireLocks(ll,l);
			try {
				Account.transfer(aa, a, r.nextInt(100));
			} finally {
				l.unlock();
				ll.unlock();
			}
		}
	}

	public static void fin() throws InterruptedException {
		System.out.println("acount 1 bal" + a.getBal());
		System.out.println("acount 2 bal" + aa.getBal());
		System.out.println("total is " + (a.getBal() + aa.getBal()));
	}

}
