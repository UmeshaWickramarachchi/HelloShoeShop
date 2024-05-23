package com.example.SSMS.model;

import com.example.SSMS.model.enums.Gender;
import com.example.SSMS.model.enums.Level;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "Customer")
public class Customer{
    @Id
    @Column(unique = true, nullable = false)
    private String customerCode;
    private String customerName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private Date joinDateAsALoyality;
    @Enumerated(EnumType.STRING)
    private Level level;
    private double totalPoint;
    private Date dob;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String contactNo;
    @Column(unique = true)
    private String email;
    private Date recentlyPurchaseTimeStamp;
}
