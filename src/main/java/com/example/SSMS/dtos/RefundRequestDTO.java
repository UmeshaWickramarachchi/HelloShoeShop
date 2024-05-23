package com.example.SSMS.dtos;

import com.example.SSMS.model.OrderItems;
import com.example.SSMS.model.Sale;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class RefundRequestDTO {
    private String orderNo;
    private List<String> itemCodes;
    private boolean refundApproval = false;
}
