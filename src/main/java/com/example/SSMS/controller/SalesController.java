package com.example.SSMS.controller;

import com.example.SSMS.dtos.SalesRequestDTO;
import com.example.SSMS.model.Sale;
import com.example.SSMS.model.enums.Status;
import com.example.SSMS.service.SaleServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sale")
@RequiredArgsConstructor
public class SalesController {

    @Autowired
    private SaleServiceI saleServiceI;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("")
    public ResponseEntity<Sale> createNewSale(@RequestBody SalesRequestDTO req){
        try {
            Sale sale = saleServiceI.createOrder(req);
            if(sale == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>(sale,HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/approve/{orderNo}")
    public ResponseEntity<Sale> approveSale(@PathVariable String orderNo){
        try{
            Sale sale = saleServiceI.ApproveCardPaymentOrders(orderNo);
            if(sale == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            else{
                return new ResponseEntity<>(sale,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/non-approved-sales")
    public ResponseEntity<List<Sale>> getNonApprovedSales(){
        try{
            List<Sale> sales = saleServiceI.getSalesByStatus();
            if(sales.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(sales,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("")
    public ResponseEntity<List<Sale>> getAllSales(){
        try{
            List<Sale> sales = saleServiceI.getAllSales();
            if(sales.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else{
                return new ResponseEntity<>(sales,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{orderNo}")
    public ResponseEntity<Sale> getSaleByOrderNo(@PathVariable String orderNo){
        Sale sale = saleServiceI.getSaleByOrderNo(orderNo);
        if(orderNo == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(sale == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(sale,HttpStatus.OK);
        }
    }
}
