package general;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import clients.Customer;

public class ServiceClass {
	//static variable for account numbers
    private static int accountNum = 1000;
    //static variable for Main active customer
    
    public static int getAccountNumber()
    {
        return accountNum++;
    }
    
    public static void mainMessage()
    {
    	System.out.println("Welcome to Java Bank!");
    	System.out.println("You must CREATE and then SEARCH for a customer before doing Account manipulations.");
        System.out.println("Please select one of the following:");
        System.out.println("\n1.  Create Customer" +
                    "\n2.  Search Customer" +
                    "\n3.  Simulate All Monthly Fees" +
                    "\n4.  Exit");
    }

    public static void promptUser() {
    	System.out.print(">> ");
    }
    
    public static void searchMenuMessage() {
    	System.out.println("Before we search for a customer, please select one of the following: ");
    	System.out.println("\n1. Search by list\n2. Search by name\n3. Back to main menu");
    }
    
    public static void searchFromListMessage() {
    	System.out.println("Select one of the following customers by their numerical value:");
    }
    
    public static void activeCustomerMenu() {
    	System.out.println("-- Customer Menu --");
    	System.out.println("1. Create account");
    	System.out.println("2. Select an existing account");
    	System.out.println("3. Display all accounts");
    	System.out.println("4. Exit");
    }
    
    public static void askForAccountType() {
    	System.out.println("-- Account Creation --");
    	System.out.println("1. Checking Account");
    	System.out.println("2. Savings Account");
    	System.out.println("3. Credit Account");
    	promptUser();
    }
    
    public static void farewellMessage() {
    	System.out.println("\nThanks for using Java Bank! Good bye.");
    }
    
    /* ------------------
     * - Error Messages -
     * ------------------ */
    public static void inputMismatchMessage() {
    	System.out.println("\nMISMATCH ERROR: Invalid input. Please enter an integer.\n");
    }
    
    public static void inputOutOfRangeMessage() {
    	System.out.println("\nOOR ERRROR: Invalid input. Number is out of valid range.\n");
    }
    
    public static void genericInputMessage() {
    	System.out.println("\nGENERIC ERROR: Invalid input. Please enter an integer.\n");
    }
    
    public static void emptyCustomerListMessage() {
    	System.out.println("\nERROR: Customer List is empty. Create a customer before doing Search/Simulate operation.");
    }
    
    /* ---------------------
     * - Create a customer -
     * --------------------- */
    public static Customer createCustomer(){
        
        Scanner read = new Scanner(System.in);
        String firstName;
        String lastName;
        String email;
        String streetAddress;
        String postalCode;
        String phoneNumber;
        String gender;
        String province;
        String country;
        String city;
        
        // birthday
        int day = 0;
        int month = 0;
        int year = 0;
        
        // get information from user
        // using a do while
        boolean inputSuccess = false;
        	
    	// get first name
    	do {
    		inputSuccess = false;
    		System.out.print("First Name: ");
        	firstName = read.nextLine();
        	if(firstName.trim().isEmpty()) { // prevents white-spaces
        		System.out.println("Empty strings are not accepted!");
        	}
        	else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get last name
    	do {
    		inputSuccess = false;
    		System.out.print("Last Name: ");
        	lastName = read.nextLine();
        	if(lastName.trim().isEmpty()) { // prevents white-spaces
        		System.out.println("Empty strings are not accepted!");
        	}
        	else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get email 
    	do {
    		inputSuccess = false;
    		System.out.print("Email: ");
    		email = read.nextLine();
    		if(email.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get street address
    	do {
    		inputSuccess = false;
    		System.out.print("Street Address: ");
    		streetAddress = read.nextLine();
    		if(streetAddress.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get postal code
    	do {
    		inputSuccess = false;
    		System.out.print("Postal Code: ");
    		postalCode = read.nextLine();
    		if(postalCode.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get phone number 
    	do {
    		inputSuccess = false;
    		System.out.print("Phone Number: ");
    		phoneNumber = read.nextLine();
    		if(phoneNumber.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get gender
    	do {
    		inputSuccess = false;
    		System.out.print("Gender: ");
    		gender = read.nextLine();
    		if(gender.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		
    		else if(gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))
    			inputSuccess = true;
    		
    		else if(!gender.equalsIgnoreCase("male") || !gender.equalsIgnoreCase("female")) {
    			System.out.println("Please enter either 'male' or 'female'");
    		}
    	} while(!inputSuccess);
    	
    	// get province
    	do {
    		inputSuccess = false;
    		System.out.print("Province: ");
    		province = read.nextLine();
    		if(province.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get country
    	do {
    		inputSuccess = false;
    		System.out.print("Country: ");
    		country = read.nextLine();
    		if(country.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get city
    	do {
    		inputSuccess = false;
    		System.out.print("City: ");
    		city = read.nextLine();
    		if(city.trim().isEmpty()) {
    			System.out.println("Empty strings are not accepted!");
    		}
    		else inputSuccess = true;
    	} while(!inputSuccess);
    	
    	// get year of birthday
    	do {
    		try {
    			inputSuccess = false;
        		System.out.print("Year of birth (yyyy): ");
        		year = read.nextInt();
        		if(year < 1900 || year > 2016) {
        			System.out.println("Empty strings are not accepted!");
        		}
        		else inputSuccess = true;
    		}
    		catch(InputMismatchException e) {
    			System.out.println("ERROR - year - Invalid input! Please enter only integers");
    		}
    		catch(Exception e) {
    			System.out.println("Invalid input! Please enter only integers");
    		}
    	} while(!inputSuccess);
    	
    	// get month of birthday
    	do {
    		try {
    			inputSuccess = false;
        		System.out.print("Month of birth (mm): ");
        		month = read.nextInt();
        		if(month < 1 || month > 12) {
        			System.out.println("Month out of range. Please enter in range [01-12]");
        		}
        		else inputSuccess = true;
    		}
    		catch(InputMismatchException e) {
    			System.out.println("ERROR - year - Invalid input! Please enter only integers");
    		}
    		catch(Exception e) {
    			System.out.println("Invalid input! Please enter only integers");
    		}
    	} while(!inputSuccess);
    	
    	// get day of birthday
    	do {
    		try {
	    		inputSuccess = false;
	    		System.out.print("Day of birth (dd): ");
	    		read.nextLine();
	    		day = read.nextInt();
	    		if(day < 1 || day > 31) {
	    			System.out.println("Day out of range. Please enter in range [01-31]");
	    		}
	    		else inputSuccess = true;
    		}
    		catch(InputMismatchException e) {
    			System.out.println("ERROR - day - Invalid input! Please enter only integers");
    		}
    		catch(Exception e) {
    			System.out.println("Invalid input! Please enter only integers");
    		}
    	} while(!inputSuccess);
    	
    	System.out.println("\nSuccess creating new customer, "+firstName+" "+lastName+".\n");
        	
        Customer tempCust =  new Customer(firstName, lastName, email, streetAddress, postalCode,
        		phoneNumber, gender, province, country, city, year, 
        		month, day);  
        
        boolean accountSuccess = false;
		int accountType = 0;
		do {
			try {
				ServiceClass.askForAccountType();
				read.nextLine();
				accountType = read.nextInt();
				
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
        
		// add account after creation
        tempCust.addAccount(accountType);
        
        return tempCust;
    }
}
