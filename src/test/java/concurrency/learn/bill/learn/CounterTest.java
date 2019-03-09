package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class CounterTest {
	@Test
	public void reentrantReadWriteLockDemo() {
		Counter counter = new Counter();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		executor.submit(() -> counter.setCount(10));

		executor.submit(counter::getCount);
		executor.submit(counter::getCount);
		executor.submit(counter::getCount);

		stop(executor);

		assertTrue(true);
	}
}
