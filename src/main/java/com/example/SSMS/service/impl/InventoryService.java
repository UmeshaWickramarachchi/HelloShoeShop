package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.InventoryRequestDTO;
import com.example.SSMS.model.Inventory;
import com.example.SSMS.model.Supplier;
import com.example.SSMS.repository.InventoryDAO;
import com.example.SSMS.repository.SupplierDAO;
import com.example.SSMS.service.InventoryServiceI;
import com.example.SSMS.utill.Utills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService implements InventoryServiceI {

    @Autowired
    InventoryDAO inventoryDAO;
    @Autowired
    SupplierDAO supplierDAO;
    @Autowired
    private Utills utill;

    @Override
    public Inventory addInventories(InventoryRequestDTO request) {
        Inventory inventory = new Inventory();
        inventory.setItemCode(request.getItemCode());
        inventory.setItemDesc(request.getItemDesc());
        inventory.setQty(request.getQty());
        String image = utill.convertToBase64(request.getItemPic());
        inventory.setItemPic(image);
        inventory.setCategory(request.getCategory());
        inventory.setSize(request.getSize());
        Supplier supplier = supplierDAO.findBySupplierCode(request.getSupplierCode());
        if(supplier != null){
            inventory.setSupplierCode(supplier.getSupplierCode());
            inventory.setSupplierName(supplier.getSupplierName());
        }else{
            throw new RuntimeException("Supplier Not Found");
        }
        inventory.setUnitPriceBuy(request.getUnitPriceBuy());
        inventory.setUnitPriceSale(request.getUnitPriceSale());
        double expectedProfit = request.getUnitPriceSale() - request.getUnitPriceBuy();
        inventory.setExpectedProfit(expectedProfit);
        double profitMargin = (expectedProfit * 100)/ request.getUnitPriceBuy();
        inventory.setProfitMargin(profitMargin);
        inventory.setStatus("Available");
        return inventoryDAO.save(inventory);
    }

    @Override
    public List<Inventory> getInventories() {
        return inventoryDAO.findAll();
    }

    @Override
    public Inventory getInventoryByItemCode(String itemCode) {
        return inventoryDAO.findByItemCode(itemCode);
    }

    @Override
    public Inventory updateInvantory(String itemCode,InventoryRequestDTO req) {
        Inventory existInventory = inventoryDAO.findByItemCode(itemCode);
        if(existInventory != null){
            existInventory.setItemDesc(req.getItemDesc());
            String image = utill.convertToBase64(req.getItemPic());
            existInventory.setItemPic(image);
            existInventory.setSize(req.getSize());
            existInventory.setQty(req.getQty());
            Supplier supplier = supplierDAO.findBySupplierCode(req.getSupplierCode());
            if(supplier != null){
                existInventory.setSupplierCode(supplier.getSupplierCode());
                existInventory.setSupplierName(supplier.getSupplierName());
            }else{
                throw new RuntimeException("Supplier Not Found");
            }
            existInventory.setCategory(req.getCategory());
            existInventory.setUnitPriceSale(req.getUnitPriceSale());
            existInventory.setUnitPriceBuy(req.getUnitPriceBuy());
            double expectedProfit = req.getUnitPriceSale() - req.getUnitPriceBuy();
            existInventory.setExpectedProfit(expectedProfit);
            double profitMargin = (expectedProfit * 100)/ req.getUnitPriceBuy();
            existInventory.setProfitMargin(profitMargin);
            return inventoryDAO.save(existInventory);
        }else {
            throw new RuntimeException("Inventory with item code " + itemCode + " not found.");
        }
    }

    @Override
    public void removeInventoryByItemCode(String itemCode) {
        inventoryDAO.deleteByItemCode(itemCode);
    }


}
