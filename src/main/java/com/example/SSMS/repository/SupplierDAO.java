package com.example.SSMS.repository;

import com.example.SSMS.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierDAO extends JpaRepository<Supplier,Integer> {
    Supplier findBySupplierCode(String supplierCode);

    Supplier findByEmail(String email);
}
