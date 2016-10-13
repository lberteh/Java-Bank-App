package general;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import clients.Customer;
import financial.Account;

public class Main {

	public static Customer activeCustomer;
	
	public static void main(String[] args) {
		
		// create our input handler
		Scanner keyboard = new Scanner(System.in);
		
		// hold all customers in this ArrayList
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		
		// hold active customer for Account operations
		
		
		int mainMenuChoice = 0;
		
		// Java Bank menu
		do {
			// display main menu
			try {
				if(activeCustomer != null) {
					boolean accountSuccess = false;
					do {
						ServiceClass.activeCustomerMenu();
						ServiceClass.promptUser();
						
						mainMenuChoice = keyboard.nextInt();
						
						if(mainMenuChoice < 1 || mainMenuChoice > 4)
							ServiceClass.inputOutOfRangeMessage();
						else {
							// create account
							if(mainMenuChoice == 1) {
								accountSuccess = false;
								int accountType = 0;
								do {
									try {
										ServiceClass.askForAccountType();
										keyboard.nextLine();
										accountType = keyboard.nextInt();
										
										if(accountType < 1 || accountType > 4) {
											ServiceClass.inputOutOfRangeMessage();
										}
										else 
											accountSuccess = true;
									}
									catch(InputMismatchException e) {
										ServiceClass.inputMismatchMessage();
									}
									catch(Exception e) {
										ServiceClass.genericInputMessage();
									}
								}while(!accountSuccess);
								
								// we got our account type
								// lets create our account
								activeCustomer.addAccount(accountType);
								
								// kick out to non-active main menu
								accountSuccess = false;
							}
							
							// select an account
							else if(mainMenuChoice == 2) {
								
							}
							// view all accounts
							else if(mainMenuChoice == 3) {
								for(int i=0; i<activeCustomer.getCustomerAccounts().size(); i++) {
									System.out.println(activeCustomer.getCustomerAccounts().get(i).toString());
								}
							}
							else if(mainMenuChoice == 4) {
								activeCustomer = null;
								accountSuccess = true;
								mainMenuChoice = 0;
							}
						}
					}while(!accountSuccess);
				} //end of activeCustomer menu
				
				ServiceClass.mainMessage();
				ServiceClass.promptUser();
				
				mainMenuChoice = keyboard.nextInt();
				
				// option cannot be out of [1-4] range
				if(mainMenuChoice < 1 || mainMenuChoice > 4)
					ServiceClass.inputOutOfRangeMessage();
				
				// valid input. handle it
				else {
					
					// Create customer
					if(mainMenuChoice == 1) {
						customerList.add(ServiceClass.createCustomer());
						keyboard.nextLine();
					}
					
					// Search customer
					else if(mainMenuChoice == 2) {
						// ensure there are existing customers
						if(customerList.isEmpty()) {
							ServiceClass.emptyCustomerListMessage(); 
						}
						
						else {
							searchCustomer(customerList);
						}
					}
					
					// Simulate fees
					else if(mainMenuChoice == 3) {
						// ensure there are existing customers
						if(customerList.isEmpty()) {
							ServiceClass.emptyCustomerListMessage();
						}
						else {
						    for(Customer customer : customerList) {
						    	ArrayList<Account> acct = customer.getCustomerAccounts();
						    	
						    	if(acct.isEmpty()) {
						    		System.out.println("The account list is empty!");
						    	}
						    	else {
						    		 for(Account account : acct)
									     account.calculateMonthlyFees();
						    	} 	      
						    }
						    
						    System.out.println("Monthly fees have been added.");
						    System.out.println("Please review accounts in Search Customer section.\n");
						}
					}
				}
			}
			catch(InputMismatchException e) {
				ServiceClass.inputMismatchMessage();
				keyboard.next(); // remove faulty buffer to prevent infinite loop
			}
			
			catch(Exception e) {
				System.out.println("Hello, world!");
				ServiceClass.genericInputMessage();
			}
			
		} while(mainMenuChoice != 4);
		
		// say good-bye and shut down
		ServiceClass.farewellMessage();
		keyboard.close();
		System.exit(0);
	}
	
	/* -----------------------
     * - Search for customer -
     * ----------------------- */
	private static void searchCustomer(ArrayList<Customer> customerList) {
		// loop until user selects:
		// 1. search by list
		// 2. search by name
		// 3. back to main menu
		
		Scanner keyboard = new Scanner(System.in);
		int searchInput = 0;
		boolean searchSuccess = false;
		
		do {
			try {
				ServiceClass.searchMenuMessage();
				searchInput = keyboard.nextInt();
				
				if(searchInput < 1 || searchInput > 3) {
					System.out.println("Invalid input. Please enter in range [1-3]");
				}
				
				// Customer Search Options
				else {
						if(activeCustomer != null) {
							System.out.println("There is an active customer!");
							System.exit(0);
						}
					
						// search by list
						if(searchInput == 1) {
							displayCustomerList(customerList);
							
							// prompt user for input
							int customerSelection = receiveListInput(customerList);
							
							// assign activeCustomer
							activeCustomer = customerList.get(customerSelection);
							searchSuccess = true;
						}
						
						// search by name
						else if(searchInput == 2) {
							
							String tempFirst, tempLast;
							
							keyboard.nextLine();
							System.out.println("Please enter first name");
							ServiceClass.promptUser();
							tempFirst = keyboard.nextLine();
							
							System.out.println("Please enter last name");
							ServiceClass.promptUser();
							tempLast = keyboard.nextLine();
							
							for(Customer customer : customerList) {
								System.out.println("Inside customer list for loop");
								if(customer.getFirstName().equalsIgnoreCase(tempFirst) && customer.getLastName().equalsIgnoreCase(tempLast)) {
									activeCustomer = customer;
									System.out.println("Successfully found: " + activeCustomer.getFirstName() + " " + activeCustomer.getLastName());
									searchSuccess = true;
								}
							}
							
							if(activeCustomer == null) {
								System.out.println("No customer was found. Please create customer with that name.");
								searchSuccess = true; // Prevent user from being stuck
							}
						}
						
						// return to main menu
						else if(searchInput == 3) {
							searchSuccess = true;
						}
					}	
				}
		
			catch(InputMismatchException e) {
				System.out.println("Invalid input. Please enter an integer.");
				keyboard.next();
			}
			catch(Exception e) {
				System.out.println("Invalid input. Please enter an integer.");
				keyboard.next();
			}
		} while(!searchSuccess);
	}
	
	// method to loop through the current customer list and display each customer
	private static void displayCustomerList(ArrayList<Customer> customerList) {
		for(int i=0; i<customerList.size(); i++) {
			System.out.println((i+1) + ". " + customerList.get(i).getFirstName() + " " + customerList.get(i).getLastName());
		}
	}
	
	private static int receiveListInput(ArrayList<Customer> customerList) {
		Scanner read = new Scanner(System.in);
		int listInput = 0;
		boolean listSuccess = false;
		do {
		
			try {
				ServiceClass.promptUser();
				listInput = read.nextInt();
				
				if(listInput < 1 || listInput > customerList.size())
					ServiceClass.inputOutOfRangeMessage();
				else
					listSuccess = true;
			}
			catch(InputMismatchException e) {
				ServiceClass.inputMismatchMessage();
				read.next();
			}
			catch(Exception e) {
				ServiceClass.genericInputMessage();
				read.next();
			}
			
		}while(!listSuccess);
		
		// Decrement because ArrayList.get() will start at zero (0)
		return listInput-1;
	}
}