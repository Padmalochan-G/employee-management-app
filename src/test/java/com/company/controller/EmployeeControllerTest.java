package com.company.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.response.EmployeeTaxResponse;
import com.company.serviceImpl.EmployeeServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeControllerTest {

	@Autowired
	private EmployeeController employeeController;
	@Autowired
	private EmployeeServiceImpl employeeService;

	@BeforeEach
	void setUp() {
		employeeService = new EmployeeServiceImpl(); // Direct instance of the service class
		employeeController = new EmployeeController(employeeService);
	}

	@Test
	void testGetTaxDeductions_ReturnsValidResponse() {
		// Assuming EmployeeServiceImpl.calculateTaxDeductions is implemented with
		// hardcoded data or test data
		String employeeId = "EMP123";
		EmployeeTaxResponse mockResponse = new EmployeeTaxResponse(employeeId, "John", "Doe", 600000, 25000, 0);

		ResponseEntity<EmployeeTaxResponse> response = employeeController.getTaxDeductions(employeeId);

		assertEquals(200, response.getStatusCodeValue());
		assertEquals(mockResponse.getEmployeeId(), response.getBody().getEmployeeId());
		assertEquals(mockResponse.getYearlySalary(), response.getBody().getYearlySalary());
	}

	@Test
	void testGetTaxDeductions_NoContent() {
		// Simulate behavior when service returns null
		String employeeId = "UNKNOWN";

		ResponseEntity<EmployeeTaxResponse> response = employeeController.getTaxDeductions(employeeId);

		assertEquals(204, response.getStatusCodeValue());
	}
}
