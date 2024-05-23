package com.example.SSMS.repository;

import com.example.SSMS.model.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundDAO extends JpaRepository<Refund,Integer> {
    Refund findById(Long id);
}
