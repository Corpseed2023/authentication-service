package com.authentication.dto.companyDto;

import lombok.Data;

import java.util.Date;

//This DTO is made for fetching company data with count of busniess unit , gst registration and states

@Data
public class CompanyResponseDetails {

    private Long companyId;

    private String companyName;

    private String registrationNumber;

    private Date registrationDate;

    private String state;

    private int businessUnitCount;

    private int numberOfStatesCount;

    private int gstRegistrationCount;


}
