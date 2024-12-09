package com.company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.request.EmployeeRequest;
import com.company.response.EmployeeTaxResponse;
import com.company.service.EmployeeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/employees")
@Validated
@AllArgsConstructor
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save")
	public ResponseEntity<String> addEmployee(@Valid @RequestBody EmployeeRequest employeeRequest) {
		Boolean isSaved = employeeService.addEmployee(employeeRequest);
		if(isSaved)
		return new ResponseEntity<>("Employee details added successfully!", HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Unable to save employee details", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	// =================================================================================================

	@GetMapping("/{employeeId}/taxdeductions")
	public ResponseEntity<EmployeeTaxResponse> getTaxDeductions(@PathVariable String employeeId) {
		EmployeeTaxResponse taxResponse = employeeService.calculateTaxDeductions(employeeId);
		if(taxResponse != null)
		return new ResponseEntity<>(taxResponse, HttpStatus.OK);
		else
			return new ResponseEntity<>(taxResponse, HttpStatus.NO_CONTENT);
	}
}
