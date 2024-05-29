package com.example.SSMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Inventory")
public class Inventory {
    @Id
    @Column(unique = true)
    private String itemCode;
    private String itemDesc;
    @Column(length = 100000)
    private String itemPic;
    private String category;
    private int size;
    private String supplierCode;
    private String supplierName;
    private double unitPriceSale;
    private double unitPriceBuy;
    private double expectedProfit;
    private double profitMargin;
    private int qty;
    private String status;
}