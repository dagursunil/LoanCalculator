package com.sk.loan.calculator.properties;

/**
 * 
 * @author sdagar
 *
 */
public class MessageProperties {

	public static final String INSTALLMENT_ERROR = "Error while calculating monthly installment.";
	public static final String START_DATE_FORMAT_ERROR = "Start date format incorrect.Please input start date in format of yyyy-MM-dd'T'HH:mm:ss'Z'";
	public static final String START_DATE_VALIDATION_ERROR = "Start Date can not be null or empty";
	public static final String NOMINAL_RATE_VALIDATION_ERROR = "Nominal rate should be more than zero";
	public static final String LOAN_AMOUNT_VALIDATION_ERROR = "Loan amount should be more than zero";
	public static final String DURATION_VALIDATION_ERROR = "Loan duration should be more than zero in months";
}
