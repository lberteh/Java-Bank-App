package financial;

public class CheckingAccount extends Account {
	
	// Variables to hold overdraft information
	private boolean hasOverdraft;
	private final double overdraftLimit;
	private double overdraftUsed;

	// Constructor allowing ONLY a boolean to determine if overdraft is requested
	public CheckingAccount(boolean hasOverdraft) {
		
		// Checking type
		// $7 monthly fee
		// 1.5% interest rate
		// $2000 max transaction PER transaction
		super("Checking", 7, 0.015, 2000);
		
		// check if user requested overdraft protection
		this.hasOverdraft = hasOverdraft;
		if(this.hasOverdraft)
			this.overdraftLimit = 500; // Default overdraft amount
		else
			this.overdraftLimit = 0; // no protection allowed
		
		overdraftUsed = 0;
	}
	
	@Override
	public void withdraw(double amount) {
		
		// amount cannot be <= 0
		if(amount <= 0) {
			System.out.println("ERROR -> CheckingAccount() -- withdraw(): Invalid amount was given. No changes were made");
			return; // exit out of method
		}
		
		// amount cannot exceed 2000
		else if(amount > getMaxTransactionAmount()) {
			System.out.println("ERROR -> CheckingAccount() -- withdraw(): Exceeded maximum available transaction." + 
					" Limit is " + getMaxTransactionAmount() + ".");
			return; // exit out of method
		}
		
		// amount is valid; continue.
		else {
			// subtract balance from amount
			double remainder = amount - getBalance();
			
			// determine if overdraft must be used
			// if remainder is positive, overdraft will be used
			if(remainder > 0 && hasOverdraft) {
				
				// ERROR: remainder exceeds overdraft limit
				if(remainder > (overdraftLimit - overdraftUsed)) {
					System.out.println("ERROR -> CheckingAccount() -- withdraw(): Insufficient funds. No changes were made");
					return; // exit out of method
				}
				
				// update overdraft usage
				else {
					// set balance to 0
					if(getBalance() > 0)
						changeBalance(-getBalance());
					
					// add overdraft usage
					overdraftUsed += (remainder); // convert to positive
				}
			}
			
			// ERROR: remainder is positive and no overdraft, insufficient fund error
			else if(remainder > 0 && !hasOverdraft) {
				System.out.println("ERROR -> CheckingAccount() -- withdraw(): Insufficient funds. No changes were made");
				return; // exit out of method
			}
			
			// if remainder is positive, overdraft will not be used
			else if(remainder < 0) {
				changeBalance(-amount); // convert to negative
			}
			
			// if remainder is 0, balance is set to 0 and no overdraft added
			else {
				changeBalance(-getBalance());
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
		
		// remove any overdraft if applicable
		if(overdraftUsed > 0) {
			// subtract amount from overdraftUsed
			double remainder = amount - overdraftUsed;
			
			// if remainder is positive, overdraft is paid off and remainder is added to balance
			if(remainder > 0) {
				overdraftUsed = 0;
				changeBalance(remainder);
			}
			// if remainder is negative, overdraft is still not paid off. 
			else if(remainder < 0) {
				overdraftUsed = -1 * remainder; // take absolute value
			}
			// if remainder is zero, overdraft is paid off; no remainder
			else {
				overdraftUsed = 0;
			}
		}
		// no overdraft, add to account
		else {
			changeBalance(amount);
		}
	}
	
	@Override
	public void calculateMonthlyFees() {
		double totalFees = 0;
		
		// collect fees
		totalFees += calculateInterest();
		totalFees -= getMonthlyFee();
		
		// remove from current balance
		// can't use withdraw() in case no money in bank
		this.changeBalance(totalFees);
	}
	
	@Override 
	protected double calculateInterest() {
		double totalAmount = 0;
				
		// interest gained from balance
		totalAmount += (getInterestRate() * getBalance());
		
		// interest paid for overdraft
		totalAmount -= (getInterestRate() * overdraftUsed);
		
		return totalAmount;
	}
	
	// CheckingAccount methods
	public boolean hasOverdraft() {
		return hasOverdraft;
	}
	
	public double getOverdraftLimit() {
		return overdraftLimit;
	}
	
	public double getOverdraftUsed() {
		return overdraftUsed;
	}
}