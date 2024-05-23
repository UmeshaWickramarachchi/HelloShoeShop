package com.example.SSMS.model;

import com.example.SSMS.model.enums.Gender;
import com.example.SSMS.model.enums.Roles;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "Employee")
public class Employee{
    @Id
    private String employeeCode;
    private String employeeName;
    @Column(length = 100000)
    private String employeePic;
    @Enumerated(EnumType.STRING)
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
    private String branch;
    @Column(unique = true)
    private String email;
    @OneToOne(mappedBy = "employee",cascade = CascadeType.ALL)
    @JsonManagedReference
    private AppUser appUser;
}
