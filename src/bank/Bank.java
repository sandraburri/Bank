package bank;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bank.account.Account;
import bank.account.AccountType;
import bank.account.CredentialsException;
import bank.account.PersonalAccount;
import bank.account.SavingsAccount;
import bank.account.Transaction;
import bank.account.TransactionException;

@SuppressWarnings({ "nls", "boxing" })
public class Bank extends Thread implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String DB_FILE = "Datei";
	private int numAccounts = 0;
	private Map<Integer, Account> accounts = new HashMap<Integer, Account>();
	private static final long INTEREST_PERIOD = 10000;

	public Bank() {
		File newBank = new File(DB_FILE);
		if (newBank.exists())
			loadData();
		else {
			try {
				newBank.createNewFile();
			} catch (IOException e) {
				System.err.println("Fehler beim erstellen der Datei " + e.getMessage());
			}
			this.start();
		}
	}

	public Integer openAccount(AccountType type, String customer, String pin, double balance) {
		int nr = numAccounts++;
		Account account;
		if (type == AccountType.PERSONAL)
			account = new PersonalAccount(customer, pin, balance);
		else
			account = new SavingsAccount(customer, pin, balance);
		accounts.put(nr, account);
		saveData();
		return nr;
	}

	public double getBalance(int nr, String pin) throws CredentialsException {
		Account account = getAccount(nr);
		account.checkPIN(pin);
		return account.getBalance();
	}

	public void deposit(int nr, double amount) throws CredentialsException, TransactionException {
		Account account = getAccount(nr);
		account.deposit(amount);
		saveData();
	}

	public void withdraw(int nr, String pin, double amount) throws CredentialsException, TransactionException {
		Account account = getAccount(nr);
		account.checkPIN(pin);
		account.withdraw(amount);
		saveData();
	}

	public void closeAccount(int nr, String pin) throws CredentialsException {
		Account account = getAccount(nr);
		account.checkPIN(pin);
		accounts.remove(nr);
		saveData();
	}

	private Account getAccount(int nr) throws CredentialsException {
		if (nr < 0 || nr >= numAccounts)
			throw new CredentialsException("Ungültige Kontonummer");
		Account account = accounts.get(nr);
		if (account == null)
			throw new CredentialsException("Wrong Account");
		return account;
	}

	public void printAccounts() throws CredentialsException {
		for (int count = 0; count < numAccounts; count++) {
			if (getAccount(count) != null)
				getAccount(count).print();
		}
	}

	private void saveData() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DB_FILE));
			out.writeObject(this);
			out.close();
		} catch (IOException e) {
			System.err.println("Daten konnten nicht gesichert werden " + e.getMessage());
		}
	}

	private void loadData() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(DB_FILE));
			Bank bank = (Bank) in.readObject();
			in.close();
			this.numAccounts = bank.numAccounts;
			this.accounts = bank.accounts;
		}catch (Exception e) {
			System.err.println("Daten konnten nicht geladen werden " + e.getMessage());
		}
	}

	public List<Transaction> getTransactions(int nr, String pin) throws CredentialsException {
		Account account = getAccount(nr);
		account.checkPIN(pin);
		return account.getTransactions();
	}

	@Override
	public void run() {
		while (true) {
			for (int count = 0; count < numAccounts; count++) {
				try {
					if (getAccount(count) != null)
						getAccount(count).payInterests();
				} catch (CredentialsException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(INTEREST_PERIOD);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}