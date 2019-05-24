package concurrency.learn.bill.learn;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class NewAccount {
	public static final ConcurrentMap<String, Long> map = new ConcurrentHashMap<>(4);

	public NewAccount(String name, Long balance) {
		super();
		this.balance = balance;
		this.name = name;
	}

	private Long balance;
	private String name;

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void transfer(NewAccount target, Long amount) {
		map.compute(this.name, (k, v) -> v - amount);
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		map.compute(target.name, (k, v) -> v + amount);
	}
}
