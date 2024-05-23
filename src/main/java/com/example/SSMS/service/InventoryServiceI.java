package com.example.SSMS.service;

import com.example.SSMS.dtos.InventoryRequestDTO;
import com.example.SSMS.model.Inventory;

import java.util.List;

public interface InventoryServiceI {
    Inventory addInventories(InventoryRequestDTO request);

    List<Inventory> getInventories();

    Inventory getInventoryByItemCode(String itemCode);

    Inventory updateInvantory(String itemCode, InventoryRequestDTO updatedInvetory);

    void removeInventoryByItemCode(String itemCode);
}
