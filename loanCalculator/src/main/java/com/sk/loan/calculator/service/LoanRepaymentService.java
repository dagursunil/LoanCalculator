package com.sk.loan.calculator.service;

import com.sk.loan.calculator.bean.BorrowerPayments;
import com.sk.loan.calculator.bean.LoanInput;
import com.sk.loan.calculator.exception.LoanCalculatorExcpetion;

/**
 * 
 * @author sdagar
 *
 */
public interface LoanRepaymentService {
/**
 * Method to calculate monthly payment.
 * @param loanInput loan input
 * @return borrowerPayments
 * @throws LoanCalculatorExcpetion
 */
	BorrowerPayments calculateMonthlyPayment(LoanInput loanInput) throws  LoanCalculatorExcpetion;
}
