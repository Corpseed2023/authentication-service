package com.authentication.controller.companyController;


import com.authentication.dto.companyDto.CompanyBusinessUnitDto;
import com.authentication.dto.companyDto.CompanyRequest;
import com.authentication.dto.companyDto.CompanyResponse;
import com.authentication.dto.companyDto.CompanyResponseDetails;
import com.authentication.service.companyService.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/companyServices/company")

public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @PostMapping("/addCompany")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest ,
                                                         @RequestParam Long userId)  {

        CompanyResponse companyResponse = companyService.createCompany(companyRequest,userId);
        return new ResponseEntity<>(companyResponse, HttpStatus.CREATED);
    }

    @GetMapping("getAllCompany")
    public List<CompanyResponseDetails> getCompaniesByUserId(@RequestParam Long userId) {
        return companyService.getCompaniesByUserId(userId);
    }

     @PutMapping("/updateCompany")
    public CompanyResponse updateCompany(@RequestBody CompanyRequest companyRequest,
                                         @RequestParam Long companyId, Long userId) {
        return companyService.updateCompany(companyRequest,companyId,userId);
    }
//
//
    @DeleteMapping("/removeCompany")
    public ResponseEntity<String> disableCompany(@RequestParam Long id , @RequestParam Long userId) {
        try {
            companyService.disableCompany(id , userId);
            return ResponseEntity.ok("Company with ID " + id + " disabled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while disabling company with ID " + id);
        }
    }

    @GetMapping("/getCompanyUnitComplianceDetails")
    public List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(@RequestParam Long userId) {
        return companyService.getCompanyUnitComplianceDetails(userId);
    }

    @GetMapping("/getCompanyDataForTasks")
    public CompanyResponse getCompanyData(@RequestParam Long companyId) {
        return companyService.getCompanyData(companyId);
    }

}
