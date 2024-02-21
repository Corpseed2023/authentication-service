package com.authentication.service.companyService;

import com.authentication.dto.companyDto.CompanyResponseType;
import com.authentication.dto.companyDto.CompanyTypeRequest;

public interface CompanyServiceType {
    CompanyResponseType createCompanyType(CompanyTypeRequest companyTypeRequest, Long userId);
}
