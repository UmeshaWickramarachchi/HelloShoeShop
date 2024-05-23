package com.example.SSMS.repository;

import com.example.SSMS.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDAO extends JpaRepository<Customer,Integer> {
    Customer findByCustomerCode(String customerCode);

    Customer findByContactNo(String contactNo);
}
