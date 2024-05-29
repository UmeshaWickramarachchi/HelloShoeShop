package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.AdminPanelRequestDTO;
import com.example.SSMS.model.AdminPanel;
import com.example.SSMS.model.Inventory;
import com.example.SSMS.model.OrderItems;
import com.example.SSMS.model.Sale;
import com.example.SSMS.model.enums.Status;
import com.example.SSMS.repository.InventoryDAO;
import com.example.SSMS.repository.SalesDAO;
import com.example.SSMS.service.AdminPanelServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class AdminPanelService implements AdminPanelServiceI {
    @Autowired
    SalesDAO salesDAO;
    @Autowired
    InventoryDAO inventoryDAO;

    @Override
    public AdminPanel getSalesSummary(AdminPanelRequestDTO req) {
        List<Sale> salesList = salesDAO.findAll();
        AdminPanel adminPanel = new AdminPanel();
        List<Sale> activeSales = new ArrayList<>();
        Map<String, Integer> items = new HashMap<>();
        double totalProfit = 0;
        for(Sale sale : salesList){
            if(!sale.isRefundStatus() && sale.getStatus().equals(Status.APPROVED)){
                LocalDate saleDate = sale.getPurchaseDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate reqDate = req.getDate().toLocalDate();
                if (saleDate.equals(reqDate)) {
                    activeSales.add(sale);
                }
            }
        }
        double totalSales = activeSales.size();
        for(Sale sale:activeSales){
            totalProfit += sale.getProfit();
            for (OrderItems item : sale.getItems()) {
                String itemCode = item.getItemCode();
                int quantity = item.getQty();
                items.put(itemCode, items.getOrDefault(itemCode, 0) + quantity);
            }
        }
        String mostSoldItem = null;
        int maxQuantity = 0;
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            if (entry.getValue() > maxQuantity) {
                mostSoldItem = entry.getKey();
                maxQuantity = entry.getValue();
            }
        }
        Inventory mostSalesitem = inventoryDAO.findByItemCode(mostSoldItem);
        String mostSaleItemPic = mostSalesitem.getItemPic();
        int mostSoldItemQuantity = maxQuantity;
        adminPanel.setDate(new Date());
        adminPanel.setTotalSales(totalSales);
        adminPanel.setTotalProfit(totalProfit);
        adminPanel.setMostSaleItem(mostSalesitem.getItemCode());
        adminPanel.setMostSaleItemQty(mostSoldItemQuantity);
        adminPanel.setMostSaleItemPic(mostSaleItemPic);
        return adminPanel;
    }
}
