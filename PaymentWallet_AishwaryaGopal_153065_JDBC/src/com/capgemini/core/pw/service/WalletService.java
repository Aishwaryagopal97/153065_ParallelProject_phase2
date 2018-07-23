package com.capgemini.core.pw.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.exception.InsufficientBalanceException;

public interface WalletService 
{
	public Customer createAccount(String name ,String mobileno, BigDecimal amount);
	public Customer showBalance (String mobileno);
	public Customer fundTransfer (String sourceMobileNo,String targetMobileNo, BigDecimal amount) throws InsufficientBalanceException, ClassNotFoundException, SQLException;
	public Customer depositAmount (String mobileNo,BigDecimal amount ) throws InsufficientBalanceException, ClassNotFoundException, SQLException ;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InsufficientBalanceException, ClassNotFoundException, SQLException;
	public boolean isValidateDetails(Customer customer);
	
	
	public List printTransaction(String mobileNo) throws SQLException, ClassNotFoundException;

}
