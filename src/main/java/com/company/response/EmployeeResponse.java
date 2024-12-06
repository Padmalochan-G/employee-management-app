package com.company.response;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class EmployeeResponse implements Serializable{

	private static final long serialVersionUID = -5274426182088193402L;

	private String employeeId;

	private String firstName;

	private String lastName;

	private String email;

	private List<String> phoneNumbers;

	private String dateOfjoining; // Date of joining

	private Double salary;
}
