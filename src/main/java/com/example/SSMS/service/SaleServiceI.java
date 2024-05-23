package com.example.SSMS.service;

import com.example.SSMS.dtos.SalesRequestDTO;
import com.example.SSMS.model.Refund;
import com.example.SSMS.model.Sale;
import com.example.SSMS.model.enums.Status;

import java.util.List;

public interface SaleServiceI {
    Sale createOrder(SalesRequestDTO saleRequest);

    List<Sale> getAllSales();

    Sale getSaleByOrderNo(String orderNo);

    Sale updateSaleByOrderNo(String orderNo,SalesRequestDTO updateSaleRequest);

    Sale ApproveCardPaymentOrders(String orderNo);

    List<Sale> getSalesByStatus();
}
