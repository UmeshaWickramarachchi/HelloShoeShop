package com.example.SSMS.dtos;

import com.example.SSMS.model.enums.Category;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SupplierRequestDTO {
    private String supplierName;
    private String addressLine1;
    private String addressLine2;
    private Category category;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String mobileNo;
    private String landLineNo;
    private String email;
}
