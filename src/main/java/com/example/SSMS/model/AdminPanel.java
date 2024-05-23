package com.example.SSMS.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class AdminPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double totalSales;
    private double totalProfit;
    private String mostSaleItem;
    @Column(length = 100000)
    private String mostSaleItemPic;
    private int mostSaleItemQty;
    private Date date;
}
