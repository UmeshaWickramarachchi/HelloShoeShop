package com.example.SSMS.controller;
import com.example.SSMS.dtos.SupplierRequestDTO;
import com.example.SSMS.model.Supplier;
import com.example.SSMS.service.SuppliersServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
public class SupplierController {

    @Autowired
    SuppliersServiceI suppliersServiceI;

    @PostMapping("")
    public ResponseEntity<Supplier> saveNewSupplier(@RequestBody SupplierRequestDTO req){
        return ResponseEntity.ok(suppliersServiceI.createSupplier(req));
    }

    @GetMapping("")
    public ResponseEntity<List<Supplier>> getSupplieres(){
        List<Supplier> suppliers = suppliersServiceI.getAllSuppliers();
        if(suppliers.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(suppliers,HttpStatus.OK);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<Supplier> fetchSupplierByEmail(@PathVariable String email){
        Supplier supplier = suppliersServiceI.getSupplierByEmail(email);
        if(supplier != null){
            return new ResponseEntity<>(supplier,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{email}")
    public ResponseEntity<Supplier> updateSupplierByEmail(@PathVariable String email,@RequestBody SupplierRequestDTO req){
        Supplier supplier = suppliersServiceI.updateSupplier(email, req);
        if(supplier == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return new ResponseEntity<>(supplier,HttpStatus.OK);
        }
    }
}
