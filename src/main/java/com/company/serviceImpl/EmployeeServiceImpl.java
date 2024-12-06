package com.company.serviceImpl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.entity.Employee;
import com.company.repository.EmployeeRepository;
import com.company.request.EmployeeRequest;
import com.company.response.EmployeeTaxResponse;
import com.company.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	// ========================= Save Employee ============================

	@Override
	public Boolean addEmployee(EmployeeRequest employeeRequest) {
		Boolean isSaved = false;
		try {
			Employee employee = new Employee();

			BeanUtils.copyProperties(employeeRequest, employee);
			employeeRepository.save(employee);
				isSaved = true;
		} catch (Exception e) {
			System.err.println("Error while saving employee: " + e.getMessage());
		}
		return isSaved;
	}
	
	// ====================== Get Employee tax Response =======================

	@Override
	public EmployeeTaxResponse calculateTaxDeductions(String employeeId) {
		double taxAmount =  0.0;
		double cessAmount = 0.0;
		double yearlySalary = 0.0;
		Employee employee = null;
		
		if(employeeId != null) {
			
			employee = employeeRepository.findByempId(employeeId)
				    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));

		LocalDate doj = LocalDate.parse(employee.getDateOfJoining());
		LocalDate now = LocalDate.now();
		int monthsWorked = (int) ChronoUnit.MONTHS.between(doj.withDayOfMonth(1), now.withDayOfMonth(1)) + 1;
		yearlySalary = employee.getSalary() * Math.min(monthsWorked, 12);

		taxAmount = calculateTax(yearlySalary);
		cessAmount = calculateCess(yearlySalary);
	}

		return new EmployeeTaxResponse(employee.getEmployeeId(), employee.getFirstName(), employee.getLastName(),
				yearlySalary, taxAmount, cessAmount);
		
	}

	private double calculateTax(double yearlySalary) {
		double tax = 0.0;
		if (yearlySalary > 1_000_000) {
			tax += (yearlySalary - 1_000_000) * 0.20;
			yearlySalary = 1_000_000;
		}
		if (yearlySalary > 500_000) {
			tax += (yearlySalary - 500_000) * 0.10;
			yearlySalary = 500_000;
		}
		if (yearlySalary > 250_000) {
			tax += (yearlySalary - 250_000) * 0.05;
		}
		return tax;
	}

	private double calculateCess(double yearlySalary) {
		if (yearlySalary > 2_500_000) {
			return (yearlySalary - 2_500_000) * 0.02;
		}
		return 0.0;
	}

}
