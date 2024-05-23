package com.example.SSMS.controller;

import com.example.SSMS.dtos.EmployeeRequestDTO;
import com.example.SSMS.model.Employee;
import com.example.SSMS.service.EmployeeServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    EmployeeServiceI employeeServiceI;

    @PostMapping("")
    public ResponseEntity<Employee> saveNewEmployee(EmployeeRequestDTO req){
        return ResponseEntity.ok(employeeServiceI.createEmployee(req));
    }

    @GetMapping("")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        List<Employee> employees = employeeServiceI.getEmployees();
        if(employees.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(employees,HttpStatus.OK);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Employee> fetchEmployeeByEmail(@PathVariable String email){
        Employee employee = employeeServiceI.getEmployeeByEmail(email);
        if(employee != null){
            return new ResponseEntity<>(employee,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{email}")
    public ResponseEntity<Employee> updateEmployeeByEmail(@PathVariable String email, EmployeeRequestDTO req){
        Employee employee = employeeServiceI.updateEmployee(email, req);
        if(employee == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(employee,HttpStatus.OK);
        }
    }

    @GetMapping("/branch/{branch}")
    public ResponseEntity<List<Employee>> fetchEmployeesByBranch(@PathVariable String email){
        List<Employee> employees = employeeServiceI.getEmployeesByBranch(email);
        if(employees != null){
            return new ResponseEntity<>(employees,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
