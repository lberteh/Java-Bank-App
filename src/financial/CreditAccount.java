
package financial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class CreditAccount extends Account {
        
        private double creditLimit;
        // Transaction limit still available today
        private double availableTransactionLimit;
        private static LocalDate lastTransactionDate;
        private static String storedDate;
    
	// Constructor with the super class variables
	public CreditAccount() {
		
		// Credit type
		// $10 monthly fee
		// 14.95% interest rate
		// $1000 max transaction per day
		super("Credit", 10, 0.1495, 1000);
                
                this.creditLimit = 10000;
                this.availableTransactionLimit = getMaxTransactionAmount();
		this.lastTransactionDate = LocalDate.now();
                this.storedDate = lastTransactionDate.format(DateTimeFormatter.ISO_DATE);
	}
	
        // Credit account methods
        public double getAvailableTransactionLimit() {
            LocalDate testDate = LocalDate.now();
            String testDateNow = testDate.format(DateTimeFormatter.ISO_DATE);
            if (!storedDate.equals(testDateNow)) {
                resetTransactionLimit();
            }
		return availableTransactionLimit;
	}
        
        public void changeTransactionLimit(double amount) {
		this.availableTransactionLimit -= amount;
	}
        
        public double resetTransactionLimit() {
                availableTransactionLimit = getMaxTransactionAmount();
                return availableTransactionLimit;
        }
        
        public void changeAvailableCredit(double amount) {
                creditLimit -= amount;
                changeBalance(amount);
        }
        
        public double getAvailableCredit(){
                return creditLimit;
        }
        
        // overriden account methods
        @Override
        public void deposit(double amount){
            
        }
        // This method will be used to perform transactions with the credit card
	@Override
	public void withdraw(double amount) {
		       
        	// amount cannot be <= 0
		if(amount <= 0) {
			System.out.println("ERROR -> SavingsAccount() -- withdraw(): Invalid amount was given. No changes were made");
			return; // exit out of method
		}
		
		// amount cannot exceed current transaction limit
		else if(amount > getAvailableTransactionLimit()) {
                        // ERROR: transaction value request is higher than available limit
			System.out.println("ERROR -> SavingsAccount() -- withdraw(): Exceeded maximum available transaction." + 
					" Limit is " + getAvailableTransactionLimit() + ".");
			return; // exit out of method
		}
		
		// amount is valid; continue.
		else {
			// subtract available credit from amount
			double remainder = getAvailableCredit() - amount;
			
			// if remainder is positive, transaction will be performed
			if(remainder > 0) {
                            
                            // get current date then store it in a variable as a string
                            LocalDate tempDate = LocalDate.now();
                            String dateNow = tempDate.format(DateTimeFormatter.ISO_DATE);
                            
                            // if it's not past midnight, keep the last available transaction limit and perform transaction
                            if (storedDate.equals(dateNow)) {
                                changeTransactionLimit(amount);
                                changeAvailableCredit(amount); 
                            }
                            // if it's past midnight (date changed), reset available withdraw limit to $1000 then perform operation
                            else {
                                resetTransactionLimit();
                                storedDate = dateNow;
                                changeTransactionLimit(amount);
                                changeAvailableCredit(amount); 
                            }
                            
                        }    
                        else {
                            // ERROR: remainder is less than 0, insufficient funds
                            System.out.println("ERROR -> SavingsAccount() -- withdraw(): Insufficient funds. No changes were made");   
                        }				
		}
	}
	
	@Override
	public void calculateMonthlyFees() {
		double fees = 0;
		
		// get monthly fee		
		fees -= getMonthlyFee();
                availableTransactionLimit = -fees;
                // remove from current balance
		// can't use withdraw() in case no money in bank                
		this.changeAvailableCredit(-fees);
		availableTransactionLimit += fees;
                
	}
	
	@Override 
	protected double calculateInterest() {
		double totalAmount = 0;
				
		// interest gained from balance
		totalAmount += (getInterestRate() * getBalance());
		
		return totalAmount;
	}
        
        public double getTotalBill() {
                double totalBill = getBalance() + calculateInterest();
                return totalBill;
        }
        
        public void chargeInterest() {
                changeAvailableCredit(calculateInterest()); 
        }
	
        public void payCreditCardBill(double amount) {
                double minPay = getTotalBill()*0.08;
                // amount has to be at least 8% of the total bill
                if (amount < (getTotalBill()*0.08)) {
                        System.out.printf("ERROR -> CreditAccount() -- payCreditCardBill(): Invalid amount was given.\n"
                                            + "Minimum payment is: %,.2f"
                                            + ". Your total bill is: " 
                                            + getTotalBill() + ". \nNo changes were made.\n", minPay);
			return; // exit out of method 
                }
                // valid ammount, make payment
                else {
                        // how can we charge interest once a month?
                        chargeInterest();
                        changeAvailableCredit(-amount);                    
                }
        }
	
	// CheckingAccount testing method
	public static void main(String[] args) {
		CreditAccount test = new CreditAccount();
		
                System.out.println("Available Credit: " + test.getAvailableCredit());
                System.out.println("Balance: " + test.getBalance());
                // this has to be 0
		System.out.println("Interest: " +test.calculateInterest());
                // this should return an error 
		test.withdraw(2000);
                // now it should work
                test.withdraw(500);
		
		// available credit now should be == 9500
		System.out.println("Available Credit: " + test.getAvailableCredit());
                // balance should be 500
		System.out.println("Balance: " + test.getBalance());
                
                // this should not work, exceeds daily transaction limit;
                test.withdraw(600);
                
                // now it should work, but it will consume the remaiining limit
                test.withdraw(500);
                // balance is now 1000
                System.out.println("Balance: " + test.getBalance());
                
                // We have credit but no daily transaction limit available
                test.withdraw(5);
                test.storedDate = "changed";
                System.out.println("One day has passed new transaction limit is: " + test.getAvailableTransactionLimit());
                
                System.out.println("Interest to be paid is: " + test.calculateInterest());
                test.payCreditCardBill(50);
                System.out.println("Balance: " + test.getBalance());
                System.out.println("Available Credit: " + test.getAvailableCredit());
                test.payCreditCardBill(500);
		System.out.println("Balance: " + test.getBalance());
                System.out.println("Available Credit: " + test.getAvailableCredit());
		
		//System.out.println(test.toString());
	}
	
	
}