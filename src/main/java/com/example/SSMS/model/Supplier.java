package com.example.SSMS.model;

import com.example.SSMS.model.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "Suppliers")
public class Supplier {
    @Id
    private String supplierCode;
    private String supplierName;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String mobileNo;
    private String landLineNo;
    @Column(unique = true)
    private String email;
}
