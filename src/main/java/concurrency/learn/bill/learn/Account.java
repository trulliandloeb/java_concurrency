package concurrency.learn.bill.learn;

public class Account {
	private long balance;
	private String name;
	private long id;

	public Account(long id, String name, long balance) {
		this.id = id;
		this.name = name;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public long getBalance() {
		return balance;
	}

	public String getName() {
		return name;
	}

	public void transfer(Account target, long amount) {
		Account first = this;
		Account second = target;
		if (this.getId() > target.getId()) {
			first = target;
			second = this;
		}

//		synchronized (this) {
//			this.balance -= amount;
//		}

//		try {
//			Thread.sleep(1);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

//		synchronized (target) {
//			target.balance += amount;
//		}

		synchronized (first) {
			synchronized (second) {
				this.balance -= amount;
//				try {
//					Thread.sleep(1);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				target.balance += amount;
			}
		}
	}
}
