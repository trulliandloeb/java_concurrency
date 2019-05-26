package concurrency.learn.bill.learn;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

public class BillTest {
	@Test
	public void test() throws InterruptedException, IOException {
		final BlockingQueue<Object> queue = new LinkedBlockingQueue<>();

		// Producer
		new Thread(() -> {
			try {
				for (int i = 0; i < 9999; i++) {
					TimeUnit.MILLISECONDS.sleep(1);
					queue.put(new Object());
					System.out.println("put");
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}).start();

		// Consumer
		new Thread(() -> {
			try {
				for (int i = 0; i < 4999; i++) {
					TimeUnit.MILLISECONDS.sleep(10);
					queue.take();
					System.out.println("take");
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}).start();

		System.in.read();
		System.out.println(queue.size());
	}

}
