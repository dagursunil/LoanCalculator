package com.sk.loan.calculator.exception;

public class LoanCalculatorExcpetion extends Exception{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	    public LoanCalculatorExcpetion(String message) {
	        this.message = message;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
	    
}
