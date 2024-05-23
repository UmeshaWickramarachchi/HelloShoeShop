package com.example.SSMS.repository;

import com.example.SSMS.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeDAO extends JpaRepository<Employee,Integer> {
    Employee findByEmail(String email);

    List<Employee> findByBranch(String branch);
}
