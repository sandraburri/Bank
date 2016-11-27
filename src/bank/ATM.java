package bank;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import bank.account.AccountType;
import bank.account.CredentialsException;
import bank.account.Transaction;
import bank.account.TransactionException;

@SuppressWarnings("nls")
public class ATM {

	private Bank bank;

	public ATM(Bank bank) {
		this.bank = bank;
	}

	private Scanner scanner;

	public void run() {
		while (true) {
			System.out.println("   A   TTTTTTT M     M");
			System.out.println("  A A     T    MM   MM");
			System.out.println(" AAAAA    T    M M M M");
			System.out.println("A     A   T    M  M  M");
			System.out.println();
			System.out.println("A  Open Account");
			System.out.println("B  Get Balance");
			System.out.println("C  Get Transactions");
			System.out.println("D  Deposit");
			System.out.println("E  Withdraw");
			System.out.println("F  Close Account");
			System.out.println("X  Exit");
			System.out.println();
			System.out.print("> ");
			scanner = new Scanner(System.in);
			String choice = scanner.nextLine().toUpperCase();
			switch (choice) {
			case "A":
				openAccount();
				break;
			case "B":
				getBalance();
				break;
			case "C":
				getTransactions();
				break;
			case "D":
				deposit();
				break;
			case "E":
				withdraw();
				break;
			case "F":
				closeAccount();
				break;
			case "X":
				try {
					bank.printAccounts();
				} catch (CredentialsException e) {
					System.err.println(e.getMessage());
				}
				System.exit(0);
				break;
			default:
				System.out.println("Error: Invalid input");
			}
			System.out.println("Hit Enter to continue...");
			scanner.nextLine();
		}
	}

	private void openAccount() {
		System.out.print("Type (Personal/Savings): ");
		AccountType type = AccountType.PERSONAL;
		if (scanner.nextLine().toUpperCase().equals("S"))
			type = AccountType.SAVINGS;
		System.out.print("Customer: ");
		String customer = scanner.nextLine();
		System.out.print("PIN: ");
		String pin = scanner.nextLine();
		System.out.print("Start balance: ");
		double balance = Double.parseDouble(scanner.nextLine());
		Integer nr = bank.openAccount(type, customer, pin, balance);
		if (nr != null)
			System.out.println("Account with number " + nr + " opened");
		else
			System.out.println("Failed to open account");
	}

	private void getBalance() {
		System.out.print("Account Nr: ");
		int nr = Integer.parseInt(scanner.nextLine());
		System.out.print("PIN: ");
		String pin = scanner.nextLine();
		try {
			double balance = bank.getBalance(nr, pin);
			System.out.println("Balance of account with number " + nr + " is " + balance);
		} catch (CredentialsException e) {
			System.err.println(e.getMessage());
		}
	}

	private void getTransactions() {
		System.out.print("Account Nr: ");
		int nr = Integer.parseInt(scanner.nextLine());
		System.out.print("PIN: ");
		String pin = scanner.nextLine();
		System.out.println("Transactions of account with number " + nr);
		try {
			List<Transaction> transactions = bank.getTransactions(nr, pin);
			for (int i = 0; i < transactions.size(); i++) {
				Transaction transaction = transactions.get(i);
				double amount = transaction.getAmount();
				double balance = transaction.getBalance();
				Date valuta = transaction.getValuta();
				System.out.print("Date: " + valuta + "\n Amount: " + amount + "\n New balance is: " + balance + "\n");
			}
		} catch (CredentialsException e) {
			System.err.println(e.getMessage());
		}
	}

	private void deposit() {
		System.out.print("Account Nr: ");
		int nr = Integer.parseInt(scanner.nextLine());
		System.out.print("Amount: ");
		double amount = Double.parseDouble(scanner.nextLine());
		try {
			bank.deposit(nr, amount);
			System.out.println("Deposit of " + amount + " to account with number " + nr);
		} catch (CredentialsException | TransactionException e) {
			System.err.println(e.getMessage());
		}
	}

	private void withdraw() {
		System.out.print("Account Nr: ");
		int nr = Integer.parseInt(scanner.nextLine());
		System.out.print("PIN: ");
		String pin = scanner.nextLine();
		System.out.print("Amount: ");
		double amount = Double.parseDouble(scanner.nextLine());
		try {
			bank.withdraw(nr, pin, amount);
			System.out.println("Withdraw of " + amount + " from account with number " + nr);
		} catch (CredentialsException e) {
			System.err.println(e.getMessage());
		} catch (TransactionException e) {
			System.err.println(e.getMessage());
		}
	}

	private void closeAccount() {
		System.out.print("Account Nr: ");
		int nr = Integer.parseInt(scanner.nextLine());
		System.out.print("PIN: ");
		String pin = scanner.nextLine();
		try {
			bank.closeAccount(nr, pin);
			System.out.println("Account with number " + nr + " closed");
		} catch (CredentialsException e) {
			System.err.println(e.getMessage());
		}
	}
}
