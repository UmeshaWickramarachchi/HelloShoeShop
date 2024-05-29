package com.example.SSMS.service.impl;

import com.example.SSMS.dtos.OrderPlacingRequestDTO;
import com.example.SSMS.dtos.SalesRequestDTO;
import com.example.SSMS.exception.RecordNotFoundException;
import com.example.SSMS.model.*;
import com.example.SSMS.model.enums.Status;
import com.example.SSMS.repository.CustomerDAO;
import com.example.SSMS.repository.InventoryDAO;
import com.example.SSMS.repository.OrderItemsDAO;
import com.example.SSMS.repository.SalesDAO;
import com.example.SSMS.service.SaleServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class SaleService implements SaleServiceI {

    @Autowired
    SalesDAO salesDAO;
    @Autowired
    InventoryDAO inventoryDAO;
    @Autowired
    OrderItemsDAO itemsDAO;
    @Autowired
    CustomerDAO customerDAO;
    Payment payment;

    @Override
    @Transactional
    public Sale createOrder(SalesRequestDTO saleRequest) {
        Sale sale = new Sale();
        double totalBillAmount = 0;
        sale.setOrderNo(saleRequest.getOrderNo());
        sale.setCashierName(saleRequest.getCashierName());
        sale.setCustomerName(saleRequest.getCustomerName());
        sale.setPurchaseDate(new Date());
        ArrayList<OrderItems> items = new ArrayList<>();
        double totalProfit = 0;
        for (OrderItems item : saleRequest.getItems()) {
            Inventory inventory = inventoryDAO.findByItemCode(item.getItemCode());
            if (inventory != null) {
                inventory.setQty(inventory.getQty() - item.getQty());
                if(inventory.getQty() ==0 ){
                    inventory.setStatus("Not Available");
                }
                OrderItems newItem = new OrderItems();
                newItem.setItemCode(item.getItemCode());
                newItem.setItemDesc(inventory.getItemDesc());
                newItem.setUnitPrice(inventory.getUnitPriceSale());
                newItem.setSize(item.getSize());
                newItem.setQty(item.getQty());
                double totalPrice = newItem.getQty() * newItem.getUnitPrice();
                newItem.setTotalPriceOfEachItem(totalPrice);
                newItem.setSale(sale);
                totalBillAmount += totalPrice;
                totalProfit += inventory.getExpectedProfit();
                items.add(newItem);
                inventoryDAO.save(inventory);
            } else {
                throw new RuntimeException("Item is Not Found");
            }
        }
        sale.setTotalPrice(totalBillAmount);
        sale.setItems(items);
        sale.setProfit(totalProfit);
        if (saleRequest.isHasLoyalityCard()) {
            sale.setAddedPoints(saleRequest.getAddedPoints());
            Customer customer = customerDAO.findByContactNo(saleRequest.getContactNo());
            if(customer != null){
                customer.setRecentlyPurchaseTimeStamp(new Date());
                if(sale.getTotalPrice() > 800){
                    int points = (int) (sale.getTotalPrice()/800);
                    customer.setTotalPoint(points);
                }
                sale.setCustomer(customer);
            }
        }
        sale.setStatus(Status.NOT_APPROVED);
        sale = salesDAO.save(sale);
        for (OrderItems item : items) {
            itemsDAO.save(item);
        }
        return sale;
    }



    @Override
    public Sale ApproveCardPaymentOrders(String orderNo){
            Sale sale = salesDAO.findByOrderNo(orderNo);
            if(sale == null){
                throw new RecordNotFoundException("Sale does not exist");
            }
            else{
                sale.setStatus(Status.APPROVED);
                salesDAO.save(sale);
                return sale;
            }
    }

    @Override
    public List<Sale> getSalesByStatus(){
        return salesDAO.findByStatus(Status.NOT_APPROVED);
    }

    @Override
    public Sale placeOrder(OrderPlacingRequestDTO orderReq) {
        payment = new Payment();
        Sale sale = salesDAO.findByOrderNo(orderReq.getOrderNo());
        if(sale != null){
            if(orderReq.getPaymentMethod().equals("Card")){
                payment.setCnn(orderReq.getCnn());
                payment.setExDate(orderReq.getExDate());
                payment.setCardNo(orderReq.getCardNo());
                payment.setOrderNo(sale.getOrderNo());
                payment.setTotalBillAmount(sale.getTotalPrice());
                payment.setCashierName(sale.getCashierName());
                payment.setCustomerName(sale.getCustomerName());
                sale.setStatus(Status.APPROVED);
                return salesDAO.save(sale);
            }else{
                sale.setStatus(Status.APPROVED);
                return salesDAO.save(sale);
            }
        }
        else{
            throw new RecordNotFoundException("Sale not found");
        }
    }

    @Override
    public List<Sale> getAllSales() {
        return salesDAO.findAll();
    }

    @Override
    public Sale getSaleByOrderNo(String orderNo) {
        return salesDAO.findByOrderNo(orderNo);
    }

    @Override
    public Sale updateSaleByOrderNo(String orderNo, SalesRequestDTO updateSaleRequest) {
        return null;
    }

}
