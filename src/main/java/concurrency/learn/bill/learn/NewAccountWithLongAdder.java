package concurrency.learn.bill.learn;

import java.util.concurrent.atomic.LongAdder;

public class NewAccountWithLongAdder {
	private String name;
	private LongAdder balance;

	public NewAccountWithLongAdder(String name, long balance) {
		this.name = name;
		this.balance = new LongAdder();
		this.balance.add(balance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LongAdder getBalance() {
		return balance;
	}

	public void setBalance(LongAdder balance) {
		this.balance = balance;
	}

	public void transfer(NewAccountWithLongAdder target, long amount) {
		this.balance.add(-amount);
//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		target.balance.add(amount);
	}
}
