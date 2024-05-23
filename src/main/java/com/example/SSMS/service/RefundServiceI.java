package com.example.SSMS.service;

import com.example.SSMS.dtos.RefundRequestDTO;
import com.example.SSMS.model.Refund;

import java.util.List;

public interface RefundServiceI {
    Refund sentRefundRequest(RefundRequestDTO req);

    List<Refund> getRefundRequests();

    Refund ApproveOrRejectRefundRequest(Long id);
}
