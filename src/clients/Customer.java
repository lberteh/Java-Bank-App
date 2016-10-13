package clients;

import java.util.*;
import financial.Account;
import financial.CheckingAccount;
import financial.CreditAccount;
import financial.SavingsAccount;
import general.ServiceClass;

public class Customer {
    
    private String firstName, lastName, email, streetAddress, postalCode, phoneNumber, gender, province, country, city;
    private Date dob;
    private static ArrayList<Account> customerAccounts = new ArrayList<>(); // datatype and another datatype
    
    // constructor with customer data
    public Customer(String firstName, String lastName, String email, String streetAddress,
            String postalCode, String phoneNumber, String gender, String province,
            String country, String city, int year, int month, int day)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.province = province;
        this.country = country;
        this.city = city;
        this.dob = new Date(year, month-1, day);  // real life -1
        
        // create account list and make first account
        ArrayList<Account> customerAccounts = new ArrayList<>();
        //customerAccounts.add(createNewAccount());
    }

    // getters
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getStreetAddress()
    {
        return streetAddress;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public String getGender()
    {
        return gender;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getProvince()
    {
        return province;
    }

    public String getCountry()
    {
        return country;
    }

    public String getCity()
    {
        return city;
    }

    public Date getDob()
    {
        return dob;
    }
    
    public ArrayList getCustomerAccounts()
    {
        return customerAccounts;
    }
    
    // setters
        
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
    
    public void addAccount(int accountType) {
    	this.customerAccounts.add(createNewAccount(accountType));
    }
    
    private Account createNewAccount(int accountType) {
    	// Checkings account
    	Scanner read = new Scanner(System.in);
    	
    	if(accountType == 1) {
    		try {
        		System.out.println("1. Yes, overdraft");
        		System.out.println("2. No thank you");
    			int overdraft = read.nextInt();
    			
    			if(overdraft == 1) {
    				System.out.println("Successfully created a Checking Account with overdraft!");
    				return new CheckingAccount(true);
    			}
    			else {
    				System.out.println("Successfully created a Checking Account!");
    				return new CheckingAccount(false);
    			}
    		}
    		catch(InputMismatchException e) {
    			ServiceClass.inputMismatchMessage();
    		}
    		catch(Exception e) {
    			ServiceClass.genericInputMessage();
    		}
    	}
    	// Savings account
    	else if(accountType == 2) {
			System.out.println("Successfully created a Savings Account!");
    		return new SavingsAccount();
    	}
    	// Credit account
    	else 
			System.out.println("Successfully created a Credit Account!"); // keep compiler happy
    	
    	return new CreditAccount();
    }
    
    // method that iterates through customer account list and prints what type of account they are
    public void displayAccountsByType() {
        for (int i=0; i<getCustomerAccounts().size(); i++) 
        {
            if (getCustomerAccounts().get(i).getClass().getSimpleName().equalsIgnoreCase("CheckingAccount")) 
            {
                System.out.println((i+1) + " - Checking Account");
            }
            
            else if (getCustomerAccounts().get(i).getClass().getSimpleName().equalsIgnoreCase("SavingsAccount")) 
            {
                System.out.println((i+1) + " - Savings Account");
            }
            
            else if (getCustomerAccounts().get(i).getClass().getSimpleName().equalsIgnoreCase("CreditAccount")) 
            {
                System.out.println((i+1) + " - Credit Account");
            }
            else {
                
            }           
        }
    }
}