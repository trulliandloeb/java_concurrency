package concurrency.learn.bill.learn;

import static java.lang.System.out;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DemoTest {
	@Test
	public void test() throws InterruptedException {
		Instant start = Instant.now();
		int id = 0;

		Account a = new Account(id++, "a", 5000);
		Account b = new Account(id++, "b", 5000);
		Account c = new Account(id++, "c", 5000);
		Account d = new Account(id++, "d", 5000);

		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				a.transfer(b, 1);
			});
			threads.add(t);
			t.start();
		}

		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				b.transfer(a, 1);
			});
			threads.add(t);
			t.start();
		}

		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				c.transfer(d, 1);
			});
			threads.add(t);
			t.start();
		}

		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				d.transfer(c, 1);
			});
			threads.add(t);
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}

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

		NewAccount a = new NewAccount("a", 5000L);
		NewAccount b = new NewAccount("b", 5000L);
		NewAccount c = new NewAccount("c", 5000L);
		NewAccount d = new NewAccount("d", 5000L);

		NewAccount.map.put(a.getName(), a.getBalance());
		NewAccount.map.put(b.getName(), b.getBalance());
		NewAccount.map.put(c.getName(), c.getBalance());
		NewAccount.map.put(d.getName(), d.getBalance());

		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				a.transfer(b, 1L);
			});
			threads.add(t);
			t.start();
		}

		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				b.transfer(a, 1L);
			});
			threads.add(t);
			t.start();
		}

		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				c.transfer(d, 1L);
			});
			threads.add(t);
			t.start();
		}

		for (int i = 0; i < 5000; i++) {
			Thread t = new Thread(() -> {
				d.transfer(c, 1L);
			});
			threads.add(t);
			t.start();
		}

		for (Thread t : threads) {
			t.join();
		}

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
}
