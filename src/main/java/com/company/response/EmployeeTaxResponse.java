package com.company.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeTaxResponse {

	private String employeeId;

	private String firstName;

	private String lastName;

	private double yearlySalary;

	private double taxAmount;

	private double cessAmount;
}
