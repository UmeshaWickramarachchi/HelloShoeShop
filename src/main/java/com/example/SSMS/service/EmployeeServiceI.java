package com.example.SSMS.service;

import com.example.SSMS.dtos.EmployeeRequestDTO;
import com.example.SSMS.model.Employee;

import java.util.List;

public interface EmployeeServiceI {
    Employee createEmployee(EmployeeRequestDTO req);

    List<Employee> getEmployees();

    Employee getEmployeeByEmail(String email);

    Employee updateEmployee(String email,EmployeeRequestDTO req);

    List<Employee> getEmployeesByBranch(String branch);
}
