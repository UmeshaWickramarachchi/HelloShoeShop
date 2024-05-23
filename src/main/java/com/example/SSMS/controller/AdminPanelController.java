package com.example.SSMS.controller;

import com.example.SSMS.dtos.AdminPanelRequestDTO;
import com.example.SSMS.model.AdminPanel;
import com.example.SSMS.model.Refund;
import com.example.SSMS.service.impl.AdminPanelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/adminpanel")
public class AdminPanelController {
    @Autowired
    AdminPanelService adminPanelService;

    @PostMapping("")
    public ResponseEntity<AdminPanel> getSalesSummaryOfADay(@RequestBody AdminPanelRequestDTO req) throws ParseException {
        AdminPanel adminPanel = adminPanelService.getSalesSummary(req);
        if(adminPanel == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else{
            return new ResponseEntity<>(adminPanel,HttpStatus.OK);
        }
    }

}
