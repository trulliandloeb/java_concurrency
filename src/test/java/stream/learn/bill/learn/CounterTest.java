package stream.learn.bill.learn;

import static stream.learn.bill.learn.ConcurrentUtils.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class CounterTest

{
	@Test
	public void incrementWith2() {
		Counter counter = new Counter();
		ExecutorService executor = Executors.newFixedThreadPool(4);

		IntStream.range(0, 10000).forEach(i -> executor.submit(counter::increment));

		stop(executor);

		System.out.println(counter.getCount());
	}
}
