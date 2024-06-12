package com.example.buildings.service;

import com.example.buildings.dto.AccountantDto;
import com.example.buildings.entity.Employee;
import com.example.buildings.entity.Salary;
import com.example.buildings.repository.EmployeeRepository;
import com.example.buildings.repository.SalaryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountantService {

    private final EmployeeRepository employeeRepository;
    private final SalaryRepository salaryRepository;

    public boolean edit(AccountantDto accountantDto){
        Optional<Employee> optionalEmployee = employeeRepository.findById(accountantDto.getEmployeeId());
        if (optionalEmployee.isEmpty())
            return false;
        Employee employee = optionalEmployee.get();
        Salary salary = employee.getSalary();
        salary.setContractStartDate(accountantDto.getContractStartDate());
        salary.setContractEndDate(accountantDto.getContractEndDate());
        salary.setSalary(accountantDto.getSalary());
        Salary savedSalary = salaryRepository.save(salary);
        employee.setSalary(savedSalary);
        return true;
    }
}
