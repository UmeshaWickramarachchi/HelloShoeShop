package com.example.SSMS.repository;

import com.example.SSMS.model.Sale;
import com.example.SSMS.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesDAO extends JpaRepository<Sale,Integer> {
    Sale findByOrderNo(String orderNo);

    List<Sale> findByStatus(Status status);
}
