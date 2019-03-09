package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import concurrency.learn.bill.learn.Counter;

public class CounterTest

{
	@Test
	public void incrementWith4Threads() {
		Counter counter = new Counter();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 10000).forEach(i -> executor.submit(counter::increment));

		stop(executor);

		System.out.println(counter.getCount());
	}
}
