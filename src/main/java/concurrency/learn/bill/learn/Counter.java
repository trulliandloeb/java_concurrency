package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.sleep;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Counter {
	private int count = 0;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public int getCount() {
		lock.readLock().lock();
		try {
			System.out.println("Time: " + System.currentTimeMillis() + " Count: " + count + " Thread: "
					+ Thread.currentThread().getName());
			sleep(2);
			return count;
		} finally {
			lock.readLock().unlock();
		}
	}

	public void setCount(int c) {
		lock.writeLock().lock();
		try {
			System.out.println("Time: " + System.currentTimeMillis() + " Count: " + count + " Thread: "
					+ Thread.currentThread().getName());
			sleep(2);
			this.count = c;
		} finally {
			lock.writeLock().unlock();
		}
	}
}
