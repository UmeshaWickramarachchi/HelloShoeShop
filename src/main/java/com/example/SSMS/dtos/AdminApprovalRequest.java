package com.example.SSMS.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminApprovalRequest {
    private String orderNo;
    private String cashierName;
    private String customerName;
    private double totalPrice;
    private String cardNo;
    private String exDate;
    private String cnn;
}
