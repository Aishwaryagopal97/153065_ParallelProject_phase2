package com.capgemini.core.pw.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.capgemini.core.pw.beans.Customer;
import com.capgemini.core.pw.beans.Wallet;
import com.capgemini.core.pw.exception.InvalidInputException;



public class WalletRepoImpl implements WalletRepo {
	private Map<String, Customer> data=new HashMap<>(); 
	
	
	public WalletRepoImpl(Map<String, Customer> data) 
	{
		super();
		this.data = data;
	}
	public WalletRepoImpl() {
		super();
	}
	Customer customer=new Customer();
	@Override
	public boolean save(Customer customer) throws InvalidInputException
	{
		try(Connection con=DBUtil.getConnection())
		{
			PreparedStatement pstm=con.prepareStatement("select * from wallet_service where mobile_no=?");
		
				pstm.setString(1, customer.getMobileNo());
		ResultSet res=pstm.executeQuery();
//			if(customer==null)
//				throw new InvalidInputException("");
		if(res.next()==true)
		{
			//throw new InvalidInputException("Invalid Mobile Number");
		System.out.println();

			PreparedStatement pstm1 = con
					.prepareStatement("update wallet_service set name=?,balance=?  where mobile_no=? ");

			pstm1.setString(3, customer.getMobileNo());
			pstm1.setString(1, customer.getName());
			pstm1.setBigDecimal(2, customer.getWallet().getBalance());

			pstm1.executeUpdate();

		
		}
		else
		{
		
	PreparedStatement pstm2=con.prepareStatement("insert into Wallet_Service values(?,?,?)");

			pstm2.setString(1, customer.getMobileNo());
			pstm2.setString(2, customer.getName());
			pstm2.setBigDecimal(3, customer.getWallet().getBalance());
			
			pstm2.execute();
			
			
		}}
				catch(Exception e)
				{
					throw new InvalidInputException(e.getMessage());
				}
					
		
		//data.put(customer.getMobileNo(),customer);
		return true;
	}
	
	@Override
	public Customer findOne(String mobileNo)
	{
		
		Customer customer = null;
		try(Connection con=DBUtil.getConnection())
		{
			
PreparedStatement pstm=con.prepareStatement("select * from wallet_service where mobile_no like ?");

			
			pstm.setString(1, mobileNo);
		//	System.out.println("'");
			ResultSet res=pstm.executeQuery();
			

			while(res.next())
			
			{
				customer=new Customer();
			
			
			customer.setMobileNo(res.getString(1));
			customer.setName(res.getString(2));
			customer.setWallet(new Wallet(res.getBigDecimal(3)));}
		}
	
		catch(Exception e)
		{
			System.out.println("*******Invalid Mobile Number....Please Provide valid mobile Number*******");
		}
		
		
		return customer;
	}
	@Override
	public List getTransaction(String mobileNo) throws SQLException, ClassNotFoundException {
		
		try(Connection con=DBUtil.getConnection())
		{
		PreparedStatement pstm;
		
		pstm=con.prepareStatement("select statement from transaction_details where mobile_no=?");
	    pstm.setString(1, mobileNo);
	    ResultSet set=pstm.executeQuery();
	    List list=new LinkedList();
	    while(set.next())
	    {
	    	list.add(set.getString(1));
	    }
	  pstm.close();
	  return list;}
	}
	@Override
	public void saveTransaction(String mobileNo, String statement) throws SQLException, ClassNotFoundException {
		
		try(Connection con=DBUtil.getConnection())
		{
		
		PreparedStatement pstm;
		 pstm=con.prepareStatement("insert into transaction_details values(?,?)");
		 pstm.setString(1, mobileNo);
		 pstm.setString(2, statement);
		 pstm.execute();
		 pstm.close();
		
	}
	
	
		
	}
	}

	

	

	

