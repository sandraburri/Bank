package bank.account;

@SuppressWarnings("nls")
public class PersonalAccount extends Account {

	private static final long serialVersionUID = 1L;
	private static final double INTEREST_RATE = 0.023;

	public PersonalAccount(String customer, String pin, double balance) {
		super(customer, pin, balance);
	}

	public PersonalAccount(String customer, String pin) {
		this(customer, pin, 0.0);
	}

	@Override
	public double getInterestRate() {
		return INTEREST_RATE;
	}

	@Override
	public void print() {
		System.out.println("Type PersonalAccount");
		super.print();
	}
}
