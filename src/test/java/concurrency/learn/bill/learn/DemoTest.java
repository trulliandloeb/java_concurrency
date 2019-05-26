package concurrency.learn.bill.learn;

import static concurrency.learn.bill.learn.ConcurrentUtils.stop;
import static java.lang.System.out;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;

public class DemoTest {
	@Test
	public void test() throws InterruptedException {
		Instant start = Instant.now();
		int id = 0;

		Account a = new Account(id++, "a", 99999);
		Account b = new Account(id++, "b", 99999);
		Account c = new Account(id++, "c", 99999);
		Account d = new Account(id++, "d", 99999);

		ExecutorService executor = Executors.newFixedThreadPool(8);

		Runnable task = () -> {
			a.transfer(b, 1);
		};
		Runnable task2 = () -> {
			b.transfer(c, 1);
		};
		Runnable task3 = () -> {
			c.transfer(d, 1);
		};
		Runnable task4 = () -> {
			d.transfer(a, 1);
		};

		for (int i = 0; i < 9999999; i++) {
			executor.submit(task);
			executor.submit(task2);
			executor.submit(task3);
			executor.submit(task4);
		}

		stop(executor);

		out.println(a.getBalance());
		out.println(b.getBalance());
		out.println(c.getBalance());
		out.println(d.getBalance());
		out.println(a.getBalance() + b.getBalance() + c.getBalance() + d.getBalance());

		out.println("Total cost " + Duration.between(start, Instant.now()).toMillis() + "ms.");
	}

	@Test
	public void testNewAccount() throws InterruptedException {
		Instant start = Instant.now();

		NewAccount a = new NewAccount("a", 99999L);
		NewAccount b = new NewAccount("b", 99999L);
		NewAccount c = new NewAccount("c", 99999L);
		NewAccount d = new NewAccount("d", 99999L);

		NewAccount.map.put(a.getName(), a.getBalance());
		NewAccount.map.put(b.getName(), b.getBalance());
		NewAccount.map.put(c.getName(), c.getBalance());
		NewAccount.map.put(d.getName(), d.getBalance());
		
		ExecutorService executor = Executors.newFixedThreadPool(8);

		Runnable task = () -> {
			a.transfer(b, 1L);
		};
		Runnable task2 = () -> {
			b.transfer(c, 1L);
		};
		Runnable task3 = () -> {
			c.transfer(d, 1L);
		};
		Runnable task4 = () -> {
			d.transfer(a, 1L);
		};

		for (int i = 0; i < 9999999; i++) {
			executor.submit(task);
			executor.submit(task2);
			executor.submit(task3);
			executor.submit(task4);
		}

		stop(executor);

		a.setBalance(NewAccount.map.get(a.getName()));
		b.setBalance(NewAccount.map.get(b.getName()));
		c.setBalance(NewAccount.map.get(c.getName()));
		d.setBalance(NewAccount.map.get(d.getName()));

		out.println(a.getBalance());
		out.println(b.getBalance());
		out.println(c.getBalance());
		out.println(d.getBalance());
		out.println(a.getBalance() + b.getBalance() + c.getBalance() + d.getBalance());

		out.println("Total cost " + Duration.between(start, Instant.now()).toMillis() + "ms.");
	}

	@Test
	public void testNewAccountWithLongAdder() throws InterruptedException {
		Instant start = Instant.now();

		NewAccountWithLongAdder a = new NewAccountWithLongAdder("a", 99999);
		NewAccountWithLongAdder b = new NewAccountWithLongAdder("b", 99999);
		NewAccountWithLongAdder c = new NewAccountWithLongAdder("c", 99999);
		NewAccountWithLongAdder d = new NewAccountWithLongAdder("d", 99999);

		ExecutorService executor = Executors.newFixedThreadPool(1);

		Runnable task = () -> {
			a.transfer(b, 1);
		};
		Runnable task2 = () -> {
			b.transfer(c, 1);
		};
		Runnable task3 = () -> {
			c.transfer(d, 1);
		};
		Runnable task4 = () -> {
			d.transfer(a, 1);
		};

		for (int i = 0; i < 99999999; i++) {
			executor.submit(task);
			executor.submit(task2);
			executor.submit(task3);
			executor.submit(task4);
		}

		stop(executor);

		out.println(a.getBalance().longValue());
		out.println(b.getBalance().longValue());
		out.println(c.getBalance().longValue());
		out.println(d.getBalance().longValue());
//		out.println(a.getBalance() + b.getBalance() + c.getBalance() + d.getBalance());

		out.println("Total cost " + Duration.between(start, Instant.now()).toMillis() + "ms.");
	}
}
