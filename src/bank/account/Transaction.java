package bank.account;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Transaction implements Serializable{

	private static final long serialVersionUID = 1L;
	private double amount;
	private double balance;
	private Date valuta;

	public Transaction(double amount, double balance) {
		this.amount = amount;
		this.balance = balance;
		this.valuta = Calendar.getInstance().getTime();
	}

	public double getAmount() {
		return this.amount;
	}

	public double getBalance() {
		return this.balance;
	}

	public Date getValuta() {
		return this.valuta;
	}

}
