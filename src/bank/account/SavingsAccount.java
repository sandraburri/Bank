package bank.account;

@SuppressWarnings("nls")
public class SavingsAccount extends Account {

	private static final long serialVersionUID = 1L;
	public static final double WITHDRAW_LIMIT = 5000;
	private static final double INTEREST_RATE = 0.045;

	public SavingsAccount(String customer, String pin, double balance) {
		super(customer, pin, balance);
	}

	public SavingsAccount(String customer, String pin) {
		this(customer, pin, 0.0);
	}

	@Override
	public void withdraw(double amount) throws TransactionException {
		if (amount > balance)
			throw new TransactionException("Summe ist grösser als das Guthaben");
		if (amount > WITHDRAW_LIMIT)
			throw new TransactionException("Summe übersteigt die Limite");
		super.withdraw(amount);
	}

	@Override
	public double getInterestRate() {
		return INTEREST_RATE;
	}

	@Override
	public void print() {
		System.out.println("Type SavingsAccount");
		super.print();
	}
}
