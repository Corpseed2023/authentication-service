package com.authentication.service.businessService;



import com.authentication.dto.businessUnitDto.BusinessUnitRequest;
import com.authentication.dto.businessUnitDto.BusinessUnitResponse;

import java.util.List;

public interface BusinessUnitService {
    BusinessUnitResponse createBusinessUnit(BusinessUnitRequest businessUnitRequest , Long companyId);
    BusinessUnitResponse updateBusinessUnit(Long companyId, Long businessUnitId, BusinessUnitRequest businessUnitRequest);
    List<BusinessUnitResponse> getAllBusinessUnits(Long companyId);

    BusinessUnitResponse getBusinessUnitData(Long businessUnitId);
//    List<BusinessUnitResponse> getAllBusinessUnitsWithAllCompany();

//    List<BusinessUnitResponse> getAllBusinessUnitsWithCompany(Long businessUnitId);
}
