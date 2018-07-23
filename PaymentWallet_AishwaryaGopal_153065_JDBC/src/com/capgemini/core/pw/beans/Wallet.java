package com.capgemini.core.pw.beans;

import java.math.BigDecimal;

public class Wallet {

	private BigDecimal balance;

	public Wallet() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public Wallet(BigDecimal balance) {
		super();
		this.balance = balance;
	}

	
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
		public String toString() {
		return ", balance="+balance;
	}

	}


