package com.example.SSMS.controller;

import com.example.SSMS.dtos.CustomerRequestDTO;
import com.example.SSMS.model.Customer;
import com.example.SSMS.service.CustomerServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    @Autowired
    CustomerServiceI customerServiceI;

    @PostMapping("")
    public ResponseEntity<Customer> saveNewCustomer(@RequestBody CustomerRequestDTO req){
        return ResponseEntity.ok(customerServiceI.saveNewLoyalityCustomer(req));
    }

    @GetMapping("")
    public ResponseEntity<List<Customer>> getCustomers(){
        List<Customer> customers = customerServiceI.getAllCustomers();
        if(customers == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(customers,HttpStatus.OK);
        }
    }

    @GetMapping("/code/{customerCode}")
    public ResponseEntity<Customer> getCustomerByCustomerCode(@PathVariable String customerCode){
        Customer customer = customerServiceI.getCustomerByCode(customerCode);
        if(customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(customer,HttpStatus.OK);
        }
    }

    @GetMapping("/contact/{contactNo}")
    public ResponseEntity<Customer> getCustomerByContact(@PathVariable String contactNo){
        Customer customer = customerServiceI.getCustomerByContactNo(contactNo);
        if(customer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(customer,HttpStatus.OK);
        }
    }

}
