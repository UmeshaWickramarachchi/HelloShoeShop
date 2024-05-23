package com.example.SSMS.repository;

import com.example.SSMS.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsDAO extends JpaRepository<OrderItems,Integer> {
    List<OrderItems> findByItemCode(String itemCode);
}
