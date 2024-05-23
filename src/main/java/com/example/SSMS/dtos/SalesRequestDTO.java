package com.example.SSMS.dtos;

import com.example.SSMS.model.Customer;
import com.example.SSMS.model.OrderItems;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SalesRequestDTO {
    private String customerName;
    private String paymentMethod;
    private boolean hasLoyalityCard;
    private double addedPoints;
    private String cashierName;
    private ArrayList<OrderItems> items;
    private String customerCode;
    private String cardNo;
    private String exDate;
    private String cnn;
}
