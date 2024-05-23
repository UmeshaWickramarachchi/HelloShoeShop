package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.SupplierRequestDTO;
import com.example.SSMS.exception.NoContentException;
import com.example.SSMS.exception.RecordAlreadyExistException;
import com.example.SSMS.exception.RecordNotFoundException;
import com.example.SSMS.model.Supplier;
import com.example.SSMS.repository.SupplierDAO;
import com.example.SSMS.service.SuppliersServiceI;
import com.example.SSMS.utill.Utills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuppliersService implements SuppliersServiceI {

    @Autowired
    SupplierDAO supplierDAO;
    @Autowired
    Utills utills;
    @Override
    public Supplier createSupplier(SupplierRequestDTO req) {
        Supplier existingSupplier = supplierDAO.findByEmail(req.getEmail());
        if(existingSupplier == null){
            Supplier supplier = new Supplier();
            supplier.setSupplierCode(utills.generateCode("SUP"));
            supplier.setSupplierName(req.getSupplierName());
            supplier.setCategory(req.getCategory());
            supplier.setAddressLine1(req.getAddressLine1());
            supplier.setAddressLine2(req.getAddressLine2());
            supplier.setCity(req.getCity());
            supplier.setState(req.getState());
            supplier.setPostalCode(req.getPostalCode());
            supplier.setCountry(req.getCountry());
            supplier.setMobileNo(req.getMobileNo());
            supplier.setLandLineNo(req.getLandLineNo());
            supplier.setEmail(req.getEmail());
            supplierDAO.save(supplier);
            return supplier;
        }
        else{
            throw new RecordAlreadyExistException("This User Already Exist");
        }
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = supplierDAO.findAll();
        if(suppliers.isEmpty()){
            throw new NoContentException("There are no any suppliers");
        }
        else{
            return suppliers;
        }
    }

    @Override
    public Supplier getSupplierByEmail(String email) {
        Supplier supplier =  supplierDAO.findByEmail(email);
        if(supplier != null){
            return supplier;
        }
        else{
            throw new RecordNotFoundException("Supplier Not Found");
        }
    }

    @Override
    public Supplier updateSupplier(String email, SupplierRequestDTO req) {
        Supplier existingSuppliier = supplierDAO.findByEmail(email);
        if (existingSuppliier != null) {
            if (!email.equals(req.getEmail()) && supplierDAO.findByEmail(req.getEmail()) != null) {
                throw new RecordAlreadyExistException("Employee with email " + req.getEmail() + " already exists.");
            }
            existingSuppliier.setSupplierName(req.getSupplierName());
            existingSuppliier.setCategory(req.getCategory());
            existingSuppliier.setAddressLine1(req.getAddressLine1());
            existingSuppliier.setAddressLine2(req.getAddressLine2());
            existingSuppliier.setCity(req.getCity());
            existingSuppliier.setState(req.getState());
            existingSuppliier.setPostalCode(req.getPostalCode());
            existingSuppliier.setCountry(req.getCountry());
            existingSuppliier.setMobileNo(req.getMobileNo());
            existingSuppliier.setLandLineNo(req.getLandLineNo());
            existingSuppliier.setEmail(req.getEmail());
            supplierDAO.save(existingSuppliier);
            return existingSuppliier;
        } else {
            throw new RecordNotFoundException("Supplier with email " + email + " not found.");
        }
    }


}
