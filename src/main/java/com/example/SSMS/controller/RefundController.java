package com.example.SSMS.controller;

import com.example.SSMS.dtos.RefundRequestDTO;
import com.example.SSMS.model.Refund;
import com.example.SSMS.service.RefundServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refund")
public class RefundController {

    @Autowired
    RefundServiceI refundServiceI;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("")
    public ResponseEntity<Refund> sentRefundRequest(@RequestBody RefundRequestDTO req){
        Refund refund = refundServiceI.sentRefundRequest(req);
        if(refund != null){
            return new ResponseEntity<>(refund, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<Refund>> getRefundRequests(){
        List<Refund> requests = refundServiceI.getRefundRequests();
        if(requests.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(requests,HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Refund> approveRequest(@PathVariable Long id){
        Refund refund = refundServiceI.ApproveOrRejectRefundRequest(id);
        if(refund != null){
            return new ResponseEntity<>(refund, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
