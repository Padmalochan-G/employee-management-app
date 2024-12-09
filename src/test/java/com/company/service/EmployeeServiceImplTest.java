package com.company.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.company.entity.Employee;
import com.company.repository.EmployeeRepository;
import com.company.request.EmployeeRequest;
import com.company.response.EmployeeTaxResponse;
import com.company.serviceImpl.EmployeeServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private EmployeeRepository realEmployeeRepository;

    private EmployeeRequest validRequest;
    private EmployeeRequest invalidRequest;
    
    @BeforeEach
    public void setUp() {
        validRequest = new EmployeeRequest();
        validRequest.setEmployeeId("E123");
        validRequest.setFirstName("John");
        validRequest.setLastName("Doe");
        validRequest.setEmail("john.doe@example.com");
        validRequest.setPhoneNumbers(List.of("1234567890"));
        validRequest.setDateOfJoining("2024-01-01");
        validRequest.setSalary(50000.0);

        invalidRequest = new EmployeeRequest();
        invalidRequest.setEmployeeId("");  // Invalid employeeId (empty)
        invalidRequest.setFirstName("John");
        invalidRequest.setLastName("Doe");
        invalidRequest.setEmail("john.doe@example.com");
        invalidRequest.setPhoneNumbers(List.of("1234567890"));
        invalidRequest.setDateOfJoining("2024-01-01");
        invalidRequest.setSalary(50000.0);
    }

    @Test
    @Transactional
    void testAddEmployee_Success() {
        EmployeeRequest validRequest = new EmployeeRequest();
        validRequest.setEmployeeId("EMP12345");
        validRequest.setFirstName("John");
        validRequest.setLastName("Doe");
        validRequest.setEmail("john.doe@example.com");
        validRequest.setPhoneNumbers(List.of("1234567890"));
        validRequest.setDateOfJoining("2024-01-01");
        validRequest.setSalary(50000.0);

        Boolean result = employeeService.addEmployee(validRequest);

        assertTrue(result, "Employee should be added successfully");

        assertTrue(result, "Employee should be saved in the database");
    }

    // ===============================================================================================================
    
    @Test
    void testCalculateTaxDeductions_Success() {
        Employee mockEmployee = new Employee();
        mockEmployee.setEmployeeId("E123");
        mockEmployee.setFirstName("John");
        mockEmployee.setLastName("Doe");
        mockEmployee.setSalary(70000.00);
        mockEmployee.setDateOfJoining("2020-01-01");

        when(employeeRepository.findByempId("E123")).thenReturn(Optional.of(mockEmployee));

        EmployeeTaxResponse taxResponse = employeeService.calculateTaxDeductions("E123");

        assertNotNull(taxResponse);
        assertEquals("E123", taxResponse.getEmployeeId());
        assertEquals("John", taxResponse.getFirstName());
        assertEquals("Doe", taxResponse.getLastName());
        assertEquals(840000.00, taxResponse.getYearlySalary());
        assertEquals(46500.0, taxResponse.getTaxAmount());  // 10% tax on salary between 500,000 and 1,000,000
        assertEquals(0.0, taxResponse.getCessAmount());      // Cess is 0 for salaries less than 2,500,000
    }

}