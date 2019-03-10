package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.sleep;
import static concurrency.learn.bill.learn.ConcurrentUtils.stop;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class DemoTest {
	@Test
	public void semaphoreDemo() {
		ExecutorService executor = Executors.newFixedThreadPool(4);
		Semaphore semaphore = new Semaphore(2);

		Runnable longRunningTask = () -> {
			boolean permit = false;
			try {
				permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
				if (permit) {
					System.out.println("Semaphore acquired");
					sleep(5);
				} else {
					System.out.println("Could not acquire semaphore");
				}
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			} finally {
				if (permit) {
					semaphore.release();
				}
			}
		};

		IntStream.range(0, 4).forEach(i -> executor.submit(longRunningTask));

		stop(executor);

		assertTrue(true);
	}

	@Test
	public void atomicIntegerDemo() {
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 1000).forEach(i -> executor.submit(atomicInt::incrementAndGet));

		stop(executor);

		System.out.println(atomicInt.get());

		assertTrue(true);
	}

	@Test
	public void atomicIntegerDemo2() {
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 1000).forEach(i -> executor.submit(() -> atomicInt.updateAndGet(n -> n + 2)));

		stop(executor);

		System.out.println(atomicInt.get());

		assertTrue(true);
	}

	@Test
	public void atomicIntegerDemo3() {
		AtomicInteger atomicInt = new AtomicInteger(0);

		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 1000).forEach(i -> executor.submit(() -> atomicInt.accumulateAndGet(i, (n, m) -> n + m)));

		stop(executor);

		System.out.println(atomicInt.get());

		assertTrue(true);
	}

	@Test
	public void longAdderDemo() {
		LongAdder la = new LongAdder();

		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 1000).forEach(i -> executor.submit(la::increment));

		stop(executor);

		System.out.println(la.sumThenReset());

		assertTrue(true);
	}

	@Test
	public void longAccumulatorDemo() {
		LongBinaryOperator op = (x, y) -> x + y;
		LongAccumulator accumulator = new LongAccumulator(op, 0L);

		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 1000).forEach(i -> executor.submit(() -> accumulator.accumulate(i)));

		stop(executor);

		System.out.println(accumulator.getThenReset());

		assertTrue(true);
	}
}
