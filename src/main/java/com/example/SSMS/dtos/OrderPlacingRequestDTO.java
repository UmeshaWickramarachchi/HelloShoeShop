package com.example.SSMS.dtos;

import lombok.Data;

@Data
public class OrderPlacingRequestDTO {
    private String paymentMethod;
    private String orderNo;
    private String cardNo;
    private String exDate;
    private String cnn;
}
