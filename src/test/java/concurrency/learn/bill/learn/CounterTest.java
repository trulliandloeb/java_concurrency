package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.sleep;
import static concurrency.learn.bill.learn.ConcurrentUtils.stop;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

import org.junit.jupiter.api.Test;

public class CounterTest {
	@Test
	public void stampedLockDemo() {
		Counter counter = new Counter();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		executor.submit(() -> counter.setCount(10));
		executor.submit(counter::getCount);
		executor.submit(counter::getCount);
		executor.submit(counter::getCount);

		sleep(1);
		executor.submit(() -> counter.setCount(40));
		executor.submit(counter::getCount);
		executor.submit(counter::getCount);
		executor.submit(counter::getCount);

		stop(executor);

		assertTrue(true);
	}

	@Test
	public void stampedLockOptimisticDemo() {
		Counter counter = new Counter();
		ExecutorService executor = Executors.newFixedThreadPool(2);

		executor.submit(() -> {
			counter.getCountOptimistic();
			sleep(1);
			counter.getCountOptimistic();
			sleep(2);
			counter.getCountOptimistic();
		});

		executor.submit(() -> {
			counter.setCount(20);
		});

		stop(executor);

		assertTrue(true);
	}

	@Test
	public void reentrantDemo() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		ReadWriteLock lock = new ReentrantReadWriteLock();

		Runnable task = () -> {
			lock.writeLock().lock();
			try {
				System.out.println("Enter write lock");
				sleep(1);
			} finally {
//		        lock.readLock().unlock();
			}
		};

		executor.submit(task);
		executor.submit(task);
		executor.submit(task);
		executor.submit(task);

		stop(executor);

		assertTrue(true);
	}

	@Test
	public void notReentrantDemo() {
		ExecutorService executor = Executors.newFixedThreadPool(1);
		StampedLock sl = new StampedLock();

		Runnable task = () -> {
			long stamp = sl.writeLock();
			try {
				System.out.println("Enter write lock");
				sleep(1);
			} finally {
//				sl.unlockWrite(stamp);
			}
		};

		executor.submit(task);
		executor.submit(task);
		executor.submit(task);
		executor.submit(task);

		stop(executor);

		assertTrue(true);
	}
}
