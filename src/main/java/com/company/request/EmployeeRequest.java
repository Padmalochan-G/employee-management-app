package com.company.request;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequest implements Serializable {

	private static final long serialVersionUID = -591053967847707140L;

	@NotBlank(message = "Employee ID should not be null")
	private String employeeId;

	@NotBlank(message = "First name should not be null")
	private String firstName;

	@NotBlank(message = "Last name should not be null")
	private String lastName;

	@NotBlank(message = "Email is required")
	@Email(message = "Email should should not be null")
	private String email;

	@NotEmpty(message = "Phone numbers should not be null")
	@Size(min = 1, message = "At least one phone number is required")
	private List<@Pattern(regexp = "^\\d{10}$", message = "Each phone number must be exactly 10 digits") String> phoneNumbers;

	@NotBlank(message = "Date of joining should not be null")
	private String dateOfJoining;

	@NotNull(message = "Salary is required")
	@Positive(message = "Salary must be positive")
	private Double salary;
}
