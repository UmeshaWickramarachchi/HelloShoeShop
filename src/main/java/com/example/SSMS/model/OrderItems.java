package com.example.SSMS.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "OrderItems")
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "orderNo",referencedColumnName = "orderNo")
    private Sale sale;
    private String itemCode;
    private String itemDesc;
    private int size;
    private double unitPrice;
    private int qty;
    private double totalPriceOfEachItem;
    @ManyToOne
    @JoinColumn(name = "refundApproval")
    private Refund returnStatus;
}
