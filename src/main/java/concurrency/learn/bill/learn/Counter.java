package concurrency.learn.bill.learn;

import java.util.concurrent.locks.ReentrantLock;

public class Counter {
	private int count = 0;

	private ReentrantLock lock = new ReentrantLock();

	public int getCount() {
		return count;
	}

	public void increment() {
		lock.lock();
		try {
			++count;
		} finally {
			lock.unlock();
		}
	}
}
