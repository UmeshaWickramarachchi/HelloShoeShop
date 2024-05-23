package com.example.SSMS.service;

import com.example.SSMS.dtos.SupplierRequestDTO;
import com.example.SSMS.model.Supplier;

import java.util.List;

public interface SuppliersServiceI {
    Supplier createSupplier(SupplierRequestDTO req);

    List<Supplier> getAllSuppliers();

    Supplier getSupplierByEmail(String email);

    Supplier updateSupplier(String email,SupplierRequestDTO req);

}
