package com.example.SSMS.service;

import com.example.SSMS.dtos.CustomerRequestDTO;
import com.example.SSMS.model.Customer;

import java.util.List;

public interface CustomerServiceI {
    Customer saveNewLoyalityCustomer(CustomerRequestDTO request);

    List<Customer> getAllCustomers();

    Customer getCustomerByCode(String customerCode);

    Customer updateCustomerByCode(String code,CustomerRequestDTO request);

    Customer getCustomerByContactNo(String contactNo);
}
