package com.authentication.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import java.util.Map;

@FeignClient(name = "compliance-service", url = "http://localhost:8083")
@Service
public interface ComplianceMap {

//    @GetMapping("/compliance/company/getComplianceCount")
//     Map<Long, Integer> getComplianceCount();


    @GetMapping("/compliance/company/compliance-count")
    List<Map<String, Object>> getComplianceCountPerCompanyAndBusinessUnit();

}
