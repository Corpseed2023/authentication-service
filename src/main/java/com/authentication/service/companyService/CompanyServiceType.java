package com.authentication.service.companyService;


import com.authentication.dto.companyDto.CompanyTypeResponse;
import com.authentication.dto.companyDto.CompanyTypeRequest;
import java.util.List;

public interface CompanyServiceType {

    CompanyTypeResponse createCompanyType(CompanyTypeRequest companyTypeRequest, Long userId);

    List<String> getAllCompanyTypeNames();
}
