package com.example.SSMS.model;

import lombok.Data;

@Data
public class Payment {
    private String cardNo;
    private String exDate;
    private String cnn;
    private String orderNo;
    private double totalBillAmount;
    private String customerName;
    private String cashierName;
}
