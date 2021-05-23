package com.sk.loan.calculator.validate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.sk.loan.calculator.bean.LoanInput;
import com.sk.loan.calculator.properties.MessageProperties;
/**
 * Validator class to validate user input.
 * @author sdagur
 *
 */
@Component
public class LoanInputValidator implements Validator{

	Logger logger=Logger.getLogger(LoanInputValidator.class.getName());
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return LoanInput.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.getErrorCount() == 0) {
			LoanInput input=(LoanInput) target;
			
			if(input.getDuration()<=0) {
				
				errors.reject("duration",MessageProperties.DURATION_VALIDATION_ERROR);
			}
			if(input.getLoanAmount()<=0) {
				errors.reject("loanAmount",MessageProperties.LOAN_AMOUNT_VALIDATION_ERROR);
			}
			if(input.getNominalRate()<=0) {
				errors.reject("nominalRate",MessageProperties.NOMINAL_RATE_VALIDATION_ERROR);
			}
			if(input.getStartDate()==null || input.getStartDate().trim()=="") {
				errors.reject("startDate",MessageProperties.START_DATE_VALIDATION_ERROR);
			}else {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
					Date startDate=dateFormat.parse(input.getStartDate());
				}catch(Exception e) {
					logger.log(Level.WARNING,"start date format not acceptable");
					errors.reject("startDate",MessageProperties.START_DATE_FORMAT_ERROR);
				}
			}
			
		}
		
	}

}
