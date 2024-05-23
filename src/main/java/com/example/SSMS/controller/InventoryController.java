package com.example.SSMS.controller;

import com.example.SSMS.dtos.InventoryRequestDTO;
import com.example.SSMS.model.Inventory;
import com.example.SSMS.service.InventoryServiceI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    @Autowired
    private InventoryServiceI inventoryServiceI;

    @PostMapping("")
    public ResponseEntity<Inventory> saveInventory(InventoryRequestDTO request) {
        try {
            return ResponseEntity.ok(inventoryServiceI.addInventories(request));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Inventory>> getInventories() {
        List<Inventory> inventories = inventoryServiceI.getInventories();
        if (inventories != null) {
            return new ResponseEntity<>(inventories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{itemCode}")
    public ResponseEntity<Inventory> getInventoryByID(@PathVariable String itemCode){
        Inventory inventory = inventoryServiceI.getInventoryByItemCode(itemCode);
        if(itemCode == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(inventory == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else{
            return ResponseEntity.ok(inventory);
        }
    }


    @PatchMapping("/{itemCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Inventory> updateInventoryByItemCode(@PathVariable String itemCode, InventoryRequestDTO request) {
        if (itemCode == null || request == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Inventory updatedInventory = inventoryServiceI.updateInvantory(itemCode, request);
        if (updatedInventory != null) {
            return ResponseEntity.ok(updatedInventory);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{itemCode}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteInventory(@PathVariable String itemCode){
        inventoryServiceI.removeInventoryByItemCode(itemCode);
    }

}
