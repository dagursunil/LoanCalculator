package com.sk.loan.calculator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Locale;

import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
/**
 * Test for LoanRepaymentController class.
 * @author sdagar
 *
 */
@SpringBootTest
@AutoConfigureMockMvc
public class LoanRepaymentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	public void testGenereatePlan() throws Exception {
		String body = getLoanInputJson(5000, 5, 24, "2021-05-23T14:05:42Z");
		mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", IsEqual.equalTo(1)));
		;

	}

	@Test
	public void testGenereatePlanWhenLoanAmountZero() throws Exception {
		String body = getLoanInputJson(0, 5, 24, "2021-05-23T14:05:42Z");
		mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("loanAmount", IsEqual.equalTo("Loan amount should be more than zero")));

	}

	@Test
	public void testGenereatePlanWhenLoanDurationZero() throws Exception {
		String body = getLoanInputJson(5000, 5, 0, "2021-05-23T14:05:42Z");
		mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("duration", IsEqual.equalTo("Loan duration should be more than zero in months")));

	}

	@Test
	public void testGenereatePlanWhenRateIsZero() throws Exception {
		String body = getLoanInputJson(5000, 0, 5, "2021-05-23T14:05:42Z");
		mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("nominalRate", IsEqual.equalTo("Nominal rate should be more than zero")));

	}

	@Test
	public void testGenereatePlanWhenStartDateIsNull() throws Exception {
		String body = getLoanInputJsonWithoutDate(5000, 5, 5);
		mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("startDate", IsEqual.equalTo("Start Date can not be null or empty")));

	}

	@Test
	public void testGenereatePlanWhenStartDateIsWrongFormat() throws Exception {
		String body = getLoanInputJson(5000, 5, 5, "2021-05-23T14:05:42");
		mockMvc.perform(post("/generate-plan").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("startDate", IsEqual.equalTo(
						"Start date format incorrect.Please input start date in format of yyyy-MM-dd'T'HH:mm:ss'Z'")));

	}

	private String getLoanInputJson(double loanAmount, double nominalRate, int duration, String startDate) {
		return String.format(Locale.US,
				"{\"loanAmount\":\"%.2f\",\"nominalRate\":\"%.2f\",\"duration\":\"%d\",\"startDate\":\"%s\"}",
				loanAmount, nominalRate, duration, startDate);

	}

	private String getLoanInputJsonWithoutDate(double loanAmount, double nominalRate, int duration) {
		return String.format(Locale.US, "{\"loanAmount\":\"%.2f\",\"nominalRate\":\"%.2f\",\"duration\":\"%d\"}",
				loanAmount, nominalRate, duration);

	}
}
