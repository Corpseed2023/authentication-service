package com.authentication.dto.companyDto;

import lombok.Data;

import java.util.Date;
import java.util.List;
/*This class is for fetching details of company , business unit and count of compliance */
@Data
public class CompanyBusinessUnitDto {
    private Long companyId;
    private String companyName;
    private Long businessUnitId;
    private String businessUnitAddress;
    private String businessActivityName;
    private List<TotalComplianceDto> totalCompliance;
    private Date lastUpdated;
}