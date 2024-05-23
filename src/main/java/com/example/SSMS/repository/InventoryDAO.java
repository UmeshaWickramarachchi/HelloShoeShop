package com.example.SSMS.repository;

import com.example.SSMS.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryDAO extends JpaRepository<Inventory, Integer> {
    Inventory findByItemCode(String itemCode);

    void deleteByItemCode(String itemCode);
}
