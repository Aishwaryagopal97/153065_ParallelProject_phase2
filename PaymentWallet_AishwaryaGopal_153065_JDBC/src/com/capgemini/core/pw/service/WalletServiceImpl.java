package com.capgemini.core.pw.service;

import java.math.BigDecimal;
import java.util.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.beans.Wallet;
import com.capgemini.core.pw.exception.InsufficientBalanceException;
import com.capgemini.core.pw.exception.InvalidInputException;
import com.capgemini.core.pw.model.WalletRepo;
import com.capgemini.core.pw.model.WalletRepoImpl;

public class WalletServiceImpl implements WalletService{
private WalletRepo repo;
	
//	public WalletServiceImpl(Map<String, Customer> data)
//	{
//		repo= new WalletRepoImpl(data);
//	}
	
	public WalletServiceImpl(WalletRepo repo) 
	{
		super();
		this.repo = repo;
	}

	public WalletServiceImpl()
	{
		repo= new WalletRepoImpl();
	}

	@Override
	public Customer createAccount(String name, String mobileno, BigDecimal amount) 
	{
		Customer customer = new Customer(name, mobileno, new Wallet(amount));
		if(!isValidateDetails(customer))
			throw new InvalidInputException("******Invalid details******");
		if(isBalanceValidate(amount))
			throw new InvalidInputException("*******Amount Cannot be negative******");
		if(repo.findOne(mobileno) != null)
			throw new InvalidInputException("The account with mobile Number "+ mobileno+" is already created");
		
		
		boolean result=repo.save(customer);
			
		return customer;
		 
	}
	public Customer showBalance(String mobileNo)
	{
		Customer customer = null ;
		if(isPhoneNumberInvalid(mobileNo))
			throw new InvalidInputException("******Invalid Mobile Number******");
		customer=repo.findOne(mobileNo);
		if(customer!=null)
			return customer;
		return null;
	}
	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException, ClassNotFoundException, SQLException
	{
		Customer customer = null;
		Customer customer1 = null;
		if(isPhoneNumberInvalid(sourceMobileNo) )
			if(isPhoneNumberInvalid(targetMobileNo))
			throw new InvalidInputException("*******Invalid Details******");
		customer=repo.findOne(sourceMobileNo);
		customer1=repo.findOne(targetMobileNo);
		

		customer = withdrawAmount(sourceMobileNo,amount);
		
		if(repo.save(customer)==false)
			throw new InvalidInputException("******Invalid Details******...Please Check your Mobile Number");
		
		customer1 =depositAmount(targetMobileNo,amount);
		
		if(repo.save(customer)==false)
			throw new InvalidInputException("******Invalid Details******...Please Check your Mobile Number");
		
		
		return customer;
	}
	
	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException, ClassNotFoundException, SQLException
	{
		
		Customer customer=null;
		repo.saveTransaction(mobileNo,"You have deposited the amount of"  +  amount  +  "on"  +  new Date());
		
		if(isPhoneNumberInvalid(mobileNo))
			throw new InsufficientBalanceException("******Invalid Mobile Number******");
		
		if(isBalanceValidate(amount))
			throw new InvalidInputException("*******Amount Cannot be negative******");
		
		 customer= repo.findOne(mobileNo);
		if(customer!=null)
		customer.getWallet().setBalance(customer.getWallet().getBalance().add(amount));

		if(repo.save(customer)==false)
			throw new InvalidInputException("******Sorry Invalid details******....Please Check your Mobile Number ");
		
		return customer;
	}
	
	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException, ClassNotFoundException, SQLException 
	{
		
		Customer customer=null;
		
			repo.saveTransaction(mobileNo,"You have withdrawed the amount of " + amount + "on" + new Date());
		
		if(isPhoneNumberInvalid(mobileNo))
			throw new InsufficientBalanceException("******Invalid Mobile Number******");
		
		if(isBalanceValidate(amount))
			throw new InvalidInputException("*******Amount Cannot be negative******");
		
	 customer= repo.findOne(mobileNo);
		if(customer!=null)
			if(amount.compareTo(customer.getWallet().getBalance())>0)
				throw new InsufficientBalanceException("Insufficient Balance");
		customer.getWallet().setBalance(customer.getWallet().getBalance().subtract(amount));
		
		if(repo.save(customer)==false)
			throw new InvalidInputException("");
		
		return customer;
		
	}
	//validate phone number
	public static boolean isPhoneNumberInvalid(String phoneNumber)
	{
		if(phoneNumber.matches("[1-9][0-9]{9}"))
			return false;
		else
			return true;
	}
	public static boolean isBalanceValidate(BigDecimal amount)
	{
		if(amount.compareTo(new BigDecimal(0))>0)
			return false;
		else
			return true;
	}

		public boolean isNameValidate(String pName)
		{
			String pattern2="[A-Z] [a-zA-Z]*";
			if(pName.matches(pattern2))
			{
				return true;
			}
			else {
				return false;
		}}

		@Override
		public boolean isValidateDetails(Customer customer) {
			// TODO Auto-generated method stub
			
				if(customer==null)
					throw new InvalidInputException("Customer details cannot be null");
				
				if(customer.getMobileNo()==null||isPhoneNumberInvalid(customer.getMobileNo()))
					throw new InvalidInputException("******Phone Number is invalid******....Please provide valid details");

				if(customer.getName()==null||isNameValidate(customer.getName()))
					throw new InvalidInputException("******The Given Name is Invalid******");
			
				
				
				return true;
			}

		
		@Override
		public List printTransaction(String mobileNo) throws SQLException, ClassNotFoundException {
			List list=repo.getTransaction(mobileNo);
			if(list !=null)
			  return list;
			else
				throw new InvalidInputException("Mobile number not found");
			
		}


}
