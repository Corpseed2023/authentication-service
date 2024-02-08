package com.authentication.service.companyService;


import com.authentication.dto.companyDto.CompanyBusinessUnitDto;
import com.authentication.dto.companyDto.CompanyRequest;
import com.authentication.dto.companyDto.CompanyResponse;
import com.authentication.dto.companyDto.CompanyResponseDetails;

import java.util.List;


public interface CompanyService {

    CompanyResponse createCompany(CompanyRequest companyRequest , Long userId) ;

//
    List<CompanyResponseDetails> getCompaniesByUserId(Long userId);
//
    CompanyResponse updateCompany(CompanyRequest companyRequest,Long companyId, Long userId);
//
    void disableCompany(Long id,Long userId);
//
    List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(Long userId);
//
    CompanyResponse getCompanyData(Long companyId);

}
