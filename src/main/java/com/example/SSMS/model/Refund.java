package com.example.SSMS.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Refund_Details")
public class Refund {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Sale sale;
    @OneToMany(mappedBy = "returnStatus", cascade = CascadeType.ALL)
    private List<OrderItems> orderItems;
    private double refundAmount;
}
