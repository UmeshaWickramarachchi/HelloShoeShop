package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.CustomerRequestDTO;
import com.example.SSMS.model.Customer;
import com.example.SSMS.model.enums.Level;
import com.example.SSMS.repository.CustomerDAO;
import com.example.SSMS.service.CustomerServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements CustomerServiceI {

    @Autowired
    CustomerDAO customerDAO;

    @Override
    public Customer saveNewLoyalityCustomer(CustomerRequestDTO request) {
        Customer customer = new Customer();
        customer.setCustomerCode(request.getCustomerCode());
        customer.setCustomerName(request.getCustomerName());
        customer.setGender(request.getGender());
        customer.setDob(request.getDob());
        customer.setLevel(Level.NEW);
        customer.setAddressLine1(request.getAddressLine1());
        customer.setAddressLine2(request.getAddressLine2());
        customer.setCity(request.getCity());
        customer.setState(request.getState());
        customer.setPostalCode(request.getPostalCode());
        customer.setJoinDateAsALoyality(new Date());
        customer.setContactNo(request.getContactNo());
        customer.setEmail(request.getEmail());
        customer.setRecentlyPurchaseTimeStamp(new Date());
        return customerDAO.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }

    @Override
    public Customer getCustomerByCode(String customerCode) {
        return customerDAO.findByCustomerCode(customerCode);
    }

    @Override
    public Customer getCustomerByContactNo(String contactNo){return customerDAO.findByContactNo(contactNo);}

    @Override
    public Customer updateCustomerByCode(String code, CustomerRequestDTO updateRequest) {
        // Fetch the existing customer from the database
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerDAO.findByCustomerCode(code));

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.setCustomerName(updateRequest.getCustomerName());
            customer.setGender(updateRequest.getGender());
            customer.setDob(updateRequest.getDob());
            return customerDAO.save(customer);
        } else {
            throw new RuntimeException("User Not Found");
        }
    }



}
