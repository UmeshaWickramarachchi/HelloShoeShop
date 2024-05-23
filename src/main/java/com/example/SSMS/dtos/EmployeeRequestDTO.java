package com.example.SSMS.dtos;


import com.example.SSMS.model.enums.Gender;
import com.example.SSMS.model.enums.Roles;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class EmployeeRequestDTO {
    private String employeeName;
    private MultipartFile employeePic;
    private Gender gender;
    private String status;
    private String designation;
    private Roles accessRole;
    private Date dob;
    private Date dateOfJoin;
    private String attachedBranch;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String contactNo;
    private String emergancyInformer;
    private String emergancyContactDetails;
    private String email;
    private String branch;
}
