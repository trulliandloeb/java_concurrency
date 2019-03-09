package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CounterTest {
	@Test
	public void incrementWith4Threads() {
		Counter counter = new Counter();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 10000).forEach(i -> executor.submit(counter::increment));

		stop(executor);

		System.out.println(counter.getCount());

		assertTrue(true);
	}

	@Test
	public void reentrantLockDemo() {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		ReentrantLock lock = new ReentrantLock();

		executor.submit(() -> {
			lock.lock();
			try {
				sleep(2);
			} finally {
				lock.unlock();
			}
		});

		executor.submit(() -> {
			System.out.println("isLocked() : " + lock.isLocked());
			System.out.println("isHeldByCurrentThread(): " + lock.isHeldByCurrentThread());
			boolean locked = lock.tryLock();
			System.out.println("tryLock(): " + locked);
		});

		stop(executor);

		assertTrue(true);
	}
}
