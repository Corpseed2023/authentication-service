package com.authentication.serviceImpl.companyLogic;


import com.authentication.dto.companyDto.CompanyResponseType;
import com.authentication.dto.companyDto.CompanyTypeRequest;
import com.authentication.service.companyService.CompanyServiceType;
import org.springframework.stereotype.Service;

@Service
public class CompanyTypeServiceImpl implements CompanyServiceType {

    @Override
    public CompanyResponseType createCompanyType(CompanyTypeRequest companyTypeRequest, Long userId) {
        return null;
    }
}
