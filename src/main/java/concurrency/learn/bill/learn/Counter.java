package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.sleep;

import java.util.concurrent.locks.StampedLock;

public class Counter {
	private int count = 0;

	private final StampedLock sl = new StampedLock();

	public int getCount() {
		long stamp = sl.readLock();
		try {
			System.out.println("Time: " + System.currentTimeMillis() + " Count: " + count + " Thread: "
					+ Thread.currentThread().getName() + " Type: read");
			sleep(2);
			return count;
		} finally {
			sl.unlockRead(stamp);
		}
	}

	public void setCount(int c) {
		long stamp = sl.writeLock();
		try {
			System.out.println("Time: " + System.currentTimeMillis() + " Count: " + count + " Thread: "
					+ Thread.currentThread().getName() + " Type: write");
			sleep(2);
			this.count = c;
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	public int getCountOptimistic() {
		long stamp = sl.tryOptimisticRead();
		int c = this.count;
		if (sl.validate(stamp)) {
			System.out.println("Time: " + System.currentTimeMillis() + " Count: " + count + " Thread: "
					+ Thread.currentThread().getName() + " Type: optimistic read");
			return c;
		} else {
			stamp = sl.readLock();
			try {
				System.out.println("Time: " + System.currentTimeMillis() + " Count: " + count + " Thread: "
						+ Thread.currentThread().getName() + " Type: read");
				return count;
			} finally {
				sl.unlockRead(stamp);
			}
		}
	}
}
