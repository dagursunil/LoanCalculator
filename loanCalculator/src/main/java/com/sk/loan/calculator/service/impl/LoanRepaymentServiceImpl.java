package com.sk.loan.calculator.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.sk.loan.calculator.bean.BorrowerPayment;
import com.sk.loan.calculator.bean.BorrowerPayments;
import com.sk.loan.calculator.bean.LoanInput;
import com.sk.loan.calculator.exception.LoanCalculatorExcpetion;
import com.sk.loan.calculator.service.LoanRepaymentService;
/**
 * 
 * @author sdagur
 *
 */
@Service
public class LoanRepaymentServiceImpl implements LoanRepaymentService {

	/**
	 * Method to calculate monthly payment.
	 * @param loanInput loan input
	 * @return borrowerPayments
	 * @throws LoanCalculatorExcpetion
	 */
	@Override
	public BorrowerPayments calculateMonthlyPayment(LoanInput loanInput) throws LoanCalculatorExcpetion  {
		
		try {
			BorrowerPayments payments=new BorrowerPayments();
			double loanAmount = loanInput.getLoanAmount();
			double monthlyInterestRate = loanInput.getNominalRate();
			double MonthlyRate = (monthlyInterestRate / 100) / 12;
			double balance = loanAmount;
			int duration = loanInput.getDuration();
			String date=loanInput.getStartDate();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
			Date startDate=dateFormat.parse(date);
			
			double monthlyPayment = ((loanAmount * MonthlyRate) / (1 - (1 / Math.pow(1 + MonthlyRate, duration))));
			monthlyPayment = roundDouble(monthlyPayment, 2);
			
			int counter=0;
			Calendar c = Calendar.getInstance();
			
			while (balance > 0) {
				BorrowerPayment borrowerPayment = new BorrowerPayment();
				double interest = ((monthlyInterestRate / 100) * 30 * balance) / 360;
				interest = roundDouble(interest, 2);
				borrowerPayment.setInterest(interest);
				borrowerPayment.setInitialOutstandingPrincipal(balance);
				c.setTime(startDate);
				c.set(Calendar.MONTH, c.get(Calendar.MONTH)+counter);
				
				String newDate = dateFormat.format(c.getTime());
				borrowerPayment.setDate(newDate);
				if (balance >= monthlyPayment) {

					double principal = monthlyPayment - interest;
					principal = roundDouble(principal, 2);
					balance = balance - principal;
					balance = roundDouble(balance, 2);
					borrowerPayment.setPrincipal(principal);
					borrowerPayment.setBorrowerPaymentAmount(monthlyPayment);
					borrowerPayment.setRemainingOutstandingPrincipal(balance);

				} else {
					double principal = balance;
					principal = roundDouble(principal, 2);
					balance = 0;
					balance = roundDouble(balance, 2);
					monthlyPayment = principal + interest;
					borrowerPayment.setPrincipal(principal);
					borrowerPayment.setBorrowerPaymentAmount(monthlyPayment);
					borrowerPayment.setRemainingOutstandingPrincipal(balance);
				}
				counter++;
				payments.getBorrowerPayments().add(borrowerPayment);
			}
			
			return payments;
		} catch (Exception e) {
			throw new LoanCalculatorExcpetion("Error while calculating monthly installment.");
		}
	}



	private double roundDouble(double d, int places) {

		BigDecimal bigDecimal = new BigDecimal(Double.toString(d));
		bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
		return bigDecimal.doubleValue();
	}

}
