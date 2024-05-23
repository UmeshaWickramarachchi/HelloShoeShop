package com.example.SSMS.dtos;

import com.example.SSMS.model.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.sql.Date;

@Data
public class CustomerRequestDTO {
    private String customerCode;
    private String customerName;
    private Gender gender;
    private double totalPoint;
    private Date dob;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String contactNo;
    private String email;

}
