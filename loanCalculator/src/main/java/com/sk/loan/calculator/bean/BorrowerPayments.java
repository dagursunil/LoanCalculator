package com.sk.loan.calculator.bean;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;

@Configuration
public class BorrowerPayments {

	private List<BorrowerPayment> borrowerPayments;

	public List<BorrowerPayment> getBorrowerPayments() {
		if(borrowerPayments==null) {
			borrowerPayments=new ArrayList<>();
		}
		return borrowerPayments;
	}
	
}
