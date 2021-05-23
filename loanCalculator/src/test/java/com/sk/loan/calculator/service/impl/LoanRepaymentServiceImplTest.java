package com.sk.loan.calculator.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sk.loan.calculator.bean.BorrowerPayments;
import com.sk.loan.calculator.bean.LoanInput;
import com.sk.loan.calculator.exception.LoanCalculatorExcpetion;
import com.sk.loan.calculator.service.LoanRepaymentService;

/**
 * Test  for LoanRepaymentServiceImpl class.
 * @author sdagar
 *
 */
@SpringBootTest
public class LoanRepaymentServiceImplTest {
	
	@Autowired
	private LoanRepaymentService service;
	
	@Autowired
	private LoanInput input;
		
	@Test
	public void calculateMonthlyPaymentTest() throws Exception {
		LoanInput loanInput=setupInput();
		BorrowerPayments payments=service.calculateMonthlyPayment(loanInput);
		assertNotNull(payments);
		assertEquals(payments.getBorrowerPayments().size(),24);
		assertEquals(payments.getBorrowerPayments().get(0).getInitialOutstandingPrincipal(), 5000);
	}

	@Test
	public void calculateMonthlyPaymentTestWhenDateNull() {
		LoanInput input=setupInput();
		input.setStartDate(null);
		LoanCalculatorExcpetion ex=assertThrows(LoanCalculatorExcpetion.class, () -> {
			service.calculateMonthlyPayment(input);
		  });
		assertEquals(ex.getMessage(),"Error while calculating monthly installment.");
		

	}

	private LoanInput setupInput() {
		input.setLoanAmount(5000);
		input.setNominalRate(5);
		input.setDuration(24);
		input.setStartDate("2021-05-23T14:05:42Z");
		return input;
	}

}
