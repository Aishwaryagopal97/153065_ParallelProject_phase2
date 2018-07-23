package com.capgemini.core.pw.model;

import java.sql.SQLException;
import java.util.List;

import com.capgemini.core.pw.beans.Customer;

public interface WalletRepo
{
public boolean save(Customer customer);
	
	public Customer findOne(String mobileNo);
	
	public List getTransaction(String mobileNo) throws SQLException,ClassNotFoundException;

	public void saveTransaction(String mobileNo,String statement)throws SQLException, ClassNotFoundException;
}

