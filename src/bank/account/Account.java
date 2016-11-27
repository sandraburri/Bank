package bank.account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import bank.Printable;

@SuppressWarnings("nls")
public abstract class Account implements Printable, Serializable {

	private static final long serialVersionUID = 1L;
	protected double balance;
	private String customer;
	private String pin;
	private List<Transaction> transactions = new ArrayList<Transaction>();

	public Account(String customer, String pin, double balance) {
		this.customer = customer;
		this.pin = pin;
		this.balance = balance;
	}

	public Account(String customer, String pin) {
		this(customer, pin, 0.0);
	}

	public double getBalance() {
		return balance;
	}

	public String getCustomer() {
		return customer;
	}

	public void checkPIN(String pinToCheck) throws CredentialsException {
		if (!pinToCheck.equals(pin))
			throw new CredentialsException("Invalide PIN");
	}

	public void deposit(double amount) throws TransactionException {
		if (amount < 0)
			throw new TransactionException("Inavlide Sume");
		balance += amount;
		Transaction transaction = new Transaction(amount, balance);
		transactions.add(transaction);
	}

	public void withdraw(double amount) throws TransactionException {
		if (amount < 0)
			throw new TransactionException("Inavlide Sume");
		balance -= amount;
		Transaction transaction = new Transaction(-amount, balance);
		transactions.add(transaction);
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public abstract double getInterestRate();

	public void payInterests() {
		balance += getInterestRate() * balance;
	}

	@Override
	public void print() {
		System.out.println("Customer: " + this.getCustomer());
		System.out.println("Balance: " + this.getBalance());
	}

}
