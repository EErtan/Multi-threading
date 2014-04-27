package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
// work stealing algorithm , when a worker thread runs out of tasks it takes tasks from other working threads

// pseudo code of use, if small task do else de-construct task invoke two pieces and wait for results
// usually for recursive computation

// main component is ForkJoinPool class

// see parallelSort()

class Task extends RecursiveAction { // which extends ForkJoinTask

	Task(int[] 		mSour, int 		mSta, int 		mLen, int[] 		mDest){
		mSource = 	mSour; mStart = mSta; mLength = mLen; mDestination =mDest;
	}
	Task(){}// should not be here
	
	int mStart =0;
	int[] mSource;
	int[] mDestination;
	
	
	int mLength;
	int sThreshold = 1000; // this must be a dynamic number dependent on the hardware
	// some cpu/gpu can do more or less
	
	@Override
	protected void compute() {
		if (mLength < sThreshold) {
			computeDirectly();
			return;
		}

		int split = mLength / 2;

		invokeAll(	new Task(mSource, mStart, 			split, 				mDestination), // here is the magic split, ensure that correct vars are in place to support the split
					new Task(mSource, mStart + split, 	mLength - split,	mDestination));
	}
	
	protected void computeDirectly(){
		// do here
	}

}

public class ForkJoin {

	public static void main(String... strings) {
		ForkJoinPool fjp = new ForkJoinPool();
		fjp.invoke(new Task()); // no constructor params for display purposes only
		// works with call-ables in a collection of Futures
	}
}
