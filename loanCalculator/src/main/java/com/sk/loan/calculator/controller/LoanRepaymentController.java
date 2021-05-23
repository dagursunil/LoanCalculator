package com.sk.loan.calculator.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sk.loan.calculator.bean.BorrowerPayments;
import com.sk.loan.calculator.bean.LoanInput;
import com.sk.loan.calculator.service.LoanRepaymentService;
import com.sk.loan.calculator.validate.LoanInputValidator;

/**
 * 
 * @author sdagur
 *
 */
@RestController
public class LoanRepaymentController {

	@Autowired
	LoanRepaymentService loanService;
	@Autowired
	BorrowerPayments payments;
	@Autowired
	LoanInputValidator validator;
	Logger logger = Logger.getLogger(LoanRepaymentController.class.getName());

	@InitBinder("loanInput")
	public void initMerchantOnlyBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	/**
	 * Rest service to generate loan term plan.
	 * @param loanInput loanInput
	 * @return response
	 */
	@PostMapping("generate-plan")
	public ResponseEntity generatePlan(@Valid @RequestBody LoanInput loanInput) {
		try {
			payments = loanService.calculateMonthlyPayment(loanInput);
			return new ResponseEntity<BorrowerPayments>(payments, HttpStatus.OK);
		} catch (Exception ex) {
			logger.log(Level.SEVERE, null, ex);
			return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
/**
 * Method to handle validation exception.
 * @param ex Exception 
 * @return Map with all errors.
 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			errors.put(error.getCode(), error.getDefaultMessage());
		});
		return errors;
	}
}
