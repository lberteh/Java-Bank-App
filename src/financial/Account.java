package financial;

import java.util.Date;
import general.ServiceClass;

public abstract class Account {
	
	private String type;
	private double balance;
	private int accountNum;
	private int monthlyFee;
	// monthly interest rate
	private final double INTEREST_RATE;
	// max transaction limit per day/transaction
	private final double MAX_TRANSACTION;
	// date of account creation
	private Date creationDate;
	
	// Constructor
	public Account(String type, int monthlyFee, double interestRate, 
			double maxTransaction) {
		this.type = type;
		this.balance = 0; // default balance value
		this.accountNum = ServiceClass.getAccountNumber();
		this.monthlyFee = monthlyFee;
		this.INTEREST_RATE = interestRate;
		this.MAX_TRANSACTION = maxTransaction;
		this.creationDate = new Date(); // generates current time
	}
	
	// Abstract methods
	public abstract void withdraw(double amount);
	public abstract void deposit(double amount);
	
	// Adjusts account balance based off of various account attributes
	// Add money from positive interest: Balance += (INTEREST_RATE * balance);
	// Subtract money from negative interest: Balance -= (monthlyFee + [SPECIFIC ACCOUNT ATTRIBUTES])
	public abstract void calculateMonthlyFees();
	protected abstract double calculateInterest();
	
	// Concrete methods
	public String getType() {
		return type;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public int getAccountNum() {
		return accountNum;
	}
	
	public int getMonthlyFee() {
		return monthlyFee;
	}
	
	public double getInterestRate() {
		return INTEREST_RATE;
	}
	
	public double getMaxTransactionAmount() {
		return MAX_TRANSACTION;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	// Needed when withdraw/deposit is used
	protected void changeBalance(double amount) {
		this.balance += amount;
	}
	
	@Override
	public String toString() {
		return "Type: " + type + "\n" + 
			   "Balance: " + balance + "\n" +
			   "Number: " + accountNum + "\n" + 
			   "Monthly Fee: " + monthlyFee + "\n" + 
			   "Interest Rate: " + INTEREST_RATE + "\n" + 
			   "Maximum transaction limit: " + MAX_TRANSACTION + "\n" + 
			   "Date created: " + creationDate + "\n";
	}
}