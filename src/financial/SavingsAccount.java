
package financial;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SavingsAccount extends Account {

        // withdraw limit still available today
        private double availableWithdrawLimit;
        private static LocalDate lastWithdrawDate;
        private static String storedDate; // = lastWithdrawDate.format(DateTimeFormatter.ISO_DATE);
    
	// Constructor with the super class variables
	public SavingsAccount() {
		
		// Savings type
		// $5 monthly fee
		// 4% interest rate
		// $1000 max withdraw per day
		super("Savings", 5, 0.04, 1000);
                
                this.availableWithdrawLimit = getMaxTransactionAmount();
		this.lastWithdrawDate = LocalDate.now();
                this.storedDate = lastWithdrawDate.format(DateTimeFormatter.ISO_DATE);
	}
	
        // Savings account methods
        public double getAvailableWithdrawLimit() {
            LocalDate testDate = LocalDate.now();
            String testDateNow = testDate.format(DateTimeFormatter.ISO_DATE);
            if (!storedDate.equals(testDateNow)) {
                resetWithdrawLimit();
            }
		return availableWithdrawLimit;
	}
        
        public void changeWithdrawLimit(double amount) {
		this.availableWithdrawLimit -= amount;
	}
        
        public double resetWithdrawLimit() {
                availableWithdrawLimit = getMaxTransactionAmount();
                return availableWithdrawLimit;
        }
        
        
        // overriden account methods
	@Override
	public void withdraw(double amount) {
		       
        // amount cannot be <= 0
		if(amount <= 0) {
			System.out.println("ERROR -> SavingsAccount() -- withdraw(): Invalid amount was given. No changes were made");
		}
		
		// amount cannot exceed current withdraw limit
		else if(amount > getAvailableWithdrawLimit()) {
            // ERROR: withdraw value request is higher than available limit
			System.out.println("ERROR -> SavingsAccount() -- withdraw(): Exceeded maximum available transaction." + 
					" Limit is " + getAvailableWithdrawLimit() + ".");
		}
		
		// amount is valid; continue.
		else {
			// subtract balance from amount
			double remainder = getBalance() - amount;
			
			// if remainder is positive, transaction will be performed
			if(remainder > 0) {
                            
                // get current date then store it in a variable as a string
                LocalDate tempDate = LocalDate.now();
                String dateNow = tempDate.format(DateTimeFormatter.ISO_DATE);
                
                // if it's not past midnight, keep the last available withdraw limit and perform transaction
                if (storedDate.equals(dateNow)) {
                    changeWithdrawLimit(amount);
                    changeBalance(-amount); 
                }
                // if it's past midnight (date changed), reset available withdraw limit to $1000 then perform operation
                else {
                    resetWithdrawLimit();
                    storedDate = dateNow;
                    changeWithdrawLimit(amount);
                    changeBalance(-amount); 
                }       
            }    
            else {
                // ERROR: remainder is less than 0, insufficient funds
                System.out.println("ERROR -> SavingsAccount() -- withdraw(): Insufficient funds. No changes were made");   
            }				
		}
	}
	
	@Override
	public void deposit(double amount) {
		
		// ensure amount is valid
		if(amount <= 0) {
			System.out.println("ERROR -> CheckingAccount -- deposit(): Invalid amount given. No changes were made"); 
			return;
		}
		// valid amount, make deposit
		else {
			changeBalance(amount);
		}
	}
	
	@Override
	public void calculateMonthlyFees() {
		double fees = 0;
		
		// get monthly fee		
		fees -= getMonthlyFee();
                availableWithdrawLimit = -fees;
                // remove from current balance
		// can't use withdraw() in case no money in bank                
		this.changeBalance(fees);
		availableWithdrawLimit += fees;
                // reset temp fees variable
                fees = 0;
                // calculate interest rate 
		fees += calculateInterest();
                // pay interest over current balance
                this.changeBalance(fees);
	}
	
	@Override 
	protected double calculateInterest() {
		double totalAmount = 0;
				
		// interest gained from balance
		totalAmount += (getInterestRate() * getBalance());
		
		return totalAmount;
	}
	
	
	// CheckingAccount testing method
	public static void main(String[] args) {
		SavingsAccount test = new SavingsAccount();
		test.deposit(1500);
                System.out.println("Balance: " + test.getBalance());
		System.out.println("Interest: " +test.calculateInterest());
                // this should return an error 
		test.withdraw(2000);
                // now it should work
                test.withdraw(500);
		
		// balance == 1000
		System.out.println("Balance: " + test.getBalance());
		
                // this should not work, exceeds daily withdraw limit;
                test.withdraw(600);
                
                // now it should work, but it will consume the remaiining limit
                test.withdraw(500);
                System.out.println("Balance: " + test.getBalance());
                
                // We have balance but no limit available
                test.withdraw(5);
                test.calculateMonthlyFees();
		System.out.println("Balance: " + test.getBalance());
                System.out.println(test.getAvailableWithdrawLimit());
		
                test.storedDate = "changed";
                System.out.println(test.getAvailableWithdrawLimit());
		
		//System.out.println(test.toString());
	}
	
	
}