package com.company.requestvalidation;

import com.company.request.EmployeeRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeRequestValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testEmployeeRequest_ValidInput() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of("1234567890"));
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertTrue(violations.isEmpty(), "There should be no validation errors for valid input.");
    }

    @Test
    void testEmployeeRequest_InvalidEmployeeId() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId(""); // Invalid (empty)
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of("1234567890"));
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors for empty employeeId.");
    }

    @Test
    void testEmployeeRequest_InvalidFirstName() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName(""); // Invalid (empty)
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of("1234567890"));
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors for empty firstName.");
    }

    @Test
    void testEmployeeRequest_InvalidLastName() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName("John");
        request.setLastName(""); // Invalid (empty)
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of("1234567890"));
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors for empty lastName.");
    }

    @Test
    void testEmployeeRequest_InvalidEmail() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("invalid-email");
        request.setPhoneNumbers(List.of("1234567890"));
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors due to invalid email.");
    }

    @Test
    void testEmployeeRequest_EmptyPhoneNumber() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of()); // Empty list
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors for empty phone numbers.");
    }

    @Test
    void testEmployeeRequest_InvalidPhoneNumber() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of("12345")); // Invalid phone number (less than 10 digits)
        request.setDateOfJoining("2024-01-01");
        request.setSalary(50000.0);

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors for invalid phone number.");
    }

    @Test
    void testEmployeeRequest_NullSalary() {
        EmployeeRequest request = new EmployeeRequest();
        request.setEmployeeId("E123");
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPhoneNumbers(List.of("1234567890"));
        request.setDateOfJoining("2024-01-01");
        request.setSalary(null); // Salary is null

        Set<ConstraintViolation<EmployeeRequest>> violations = validator.validate(request);

        assertFalse(violations.isEmpty(), "There should be validation errors due to null salary.");
    }
}

