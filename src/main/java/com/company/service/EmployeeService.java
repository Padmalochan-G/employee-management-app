package com.company.service;

import com.company.request.EmployeeRequest;
import com.company.response.EmployeeTaxResponse;

public interface EmployeeService {

	public Boolean addEmployee(EmployeeRequest employeeRequest);

	public EmployeeTaxResponse calculateTaxDeductions(String employeeId);
}
