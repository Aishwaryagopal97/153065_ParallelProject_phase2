package com.capgemini.core.pw.view;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.exception.InsufficientBalanceException;
import com.capgemini.core.pw.exception.InvalidInputException;
import com.capgemini.core.pw.service.WalletService;
import com.capgemini.core.pw.service.WalletServiceImpl;

public class Client 
{
	 private static WalletService walletService;
		 public Client()
			{ 
				walletService=new WalletServiceImpl();
				
			}
			public void menu() throws InsufficientBalanceException, ClassNotFoundException, SQLException
			{
				
				String answer;
				Scanner console = new Scanner(System.in);
				System.out.println("Welcome to Wallet Application");
				System.out.println();
				Customer customer = new Customer();
				
				do {
					
				
				System.out.println("Options");
				System.out.println("1. Create Account");
				System.out.println("2. Show Balance");
				System.out.println("3. Fund Transfer");
				System.out.println("4. Deposit Amount");
				System.out.println("5. Withdraw Amount");
				System.out.println("6. Print Transaction History");
				System.out.println("0. Exit");
				System.out.println("Please select your choice:");
				int choice = console.nextInt();
				
				switch (choice) 
				{
				case 1: 
				
				
					System.out.println("Enter the Name: ");
					String name = console.next();
					console.nextLine();
					System.out.println("Enter the Mobile Number: ");
					String number = console.next();
					System.out.println();

					System.out.println("Enter the Initial Balance Amount: ");
				    BigDecimal amount = console.nextBigDecimal();
				    
				  
				    try {
				    customer= walletService.createAccount(name, number, amount);
				   
			
				    if(customer!=null) {
				    System.out.println("Congrats!!! Your Account has been created.");
				  System.out.println(customer);
				    
				    }
				    
				    else
				    	System.out.println("Account already exist.Please provide new Mobilenumber");
				    }catch(InvalidInputException e) {System.out.println(e.getMessage());}
				   break;
				   
				case 2:
					
					System.out.println("Enter the Mobile Number: ");
					String number1 = console.next();
					try {
				 customer =	walletService.showBalance(number1);
				 
				 System.out.println("Your Account Balance with the mobileNumber: "  + number1 +  "is"  +customer.getWallet().getBalance());
					}
					catch(InvalidInputException e)
					{
						System.out.println(e.getMessage());
					}
				break;
				
				case 3:
					
					System.out.println("Enter the Source Mobile Number: ");
					String sNumber = console.next();
					
					System.out.println("Enter the Target Mobile Number: ");
					String dNumber = console.next();
					
					System.out.println("Enter the Amount to transfer: ");
					BigDecimal amount1 = console.nextBigDecimal();
					try {
					customer = walletService.fundTransfer(sNumber, dNumber, amount1);
					
					 System.out.println("Current Balance is = "  +  customer.getWallet().getBalance());
					}
					catch(InvalidInputException e)
					{
						System.out.println(e.getMessage());
					}
					 break;
					
					 
				case 4:
					System.out.println("Enter the Mobile number: ");
					String mobileNumber = console.next();
					
					System.out.println("Enter the Amount to Deposit: ");
					BigDecimal depositAmount = console.nextBigDecimal();
					
					
					try {
					customer = walletService.depositAmount(mobileNumber, depositAmount);
				
					System.out.println("Your Account Balance with the mobileNumber: "  +mobileNumber +   "is"  +customer.getWallet().getBalance());
				//	System.out.println(customer);
					}
					catch(InvalidInputException e)
					{
						System.out.println(e.getMessage());
					}
					break;
				case 5:
					System.out.println("Enter the Mobile number: ");
					String mobileNumber1 = console.next();
					
					System.out.println("Enter the Amount to Withdraw: ");
					BigDecimal withdrawAmount = console.nextBigDecimal();
					
					
					try {
					customer = walletService.withdrawAmount(mobileNumber1 , withdrawAmount);
					
					System.out.println("Your Account Balance with the mobileNumber: "  +mobileNumber1
							+  "is"  +customer.getWallet().getBalance());
				//	System.out.println(customer);
					}
					catch(InvalidInputException e)
					{
						System.out.println(e.getMessage());
					}
					break;
					
					
				 case 6:
			    	    int count=1;
			    	     System.out.println("Enter the mobile number");
			    	     mobileNumber=console.next();
			    	     LinkedList list;
			    	     try {
							list=(LinkedList)walletService.printTransaction(mobileNumber);
							Iterator i=list.iterator();
				    	     while(i.hasNext()) {
				    	    	 System.out.println(""+count++ +""+i.next());
				    	     }
						} catch (ClassNotFoundException e) {
						    e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
			    	     
					break;
				case 0:
					System.out.println("THANK YOU !!!...Exiting....");
					System.exit(0);
					break;
				default:
					System.out.println("Invalid Option..Please select valid option");
					break;
					
				}
				System.out.println("\nDo you  want to continue YES/NO");
				answer=console.next();
				}
				while(answer.equalsIgnoreCase("yes")||answer.equalsIgnoreCase("y"));
				System.out.println("\n Thank you for using our service!!!");
			}
			
			public static void main(String[] args) 
			{
				Client client = new Client();
			while(true)
				try {
					client.menu();
				} catch (InsufficientBalanceException e) {
					System.out.println(e.getMessage());
					
				} catch (ClassNotFoundException e) {
					System.out.println(e.getMessage());
				
				} catch (SQLException e) {
					
					System.out.println(e.getMessage());
				}
		}
		
      
	    }

