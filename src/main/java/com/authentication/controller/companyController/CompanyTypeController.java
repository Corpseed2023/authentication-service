package com.authentication.controller.companyController;

import com.authentication.dto.companyDto.CompanyTypeResponse;
import com.authentication.dto.companyDto.CompanyTypeRequest;
import com.authentication.service.companyService.CompanyServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/companyServices/company")
public class CompanyTypeController {

    @Autowired
    private CompanyServiceType companyServiceType;


    @PostMapping("/createCompanyType")
    public ResponseEntity<CompanyTypeResponse> createCompanyType(@RequestBody CompanyTypeRequest companyTypeRequest, @RequestParam Long userId) {

        CompanyTypeResponse companyResponseType = companyServiceType.createCompanyType(companyTypeRequest,userId);

        return new ResponseEntity<>(companyResponseType, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCompanyTypeNames")
    public ResponseEntity<List<String>> getAllCompanyTypeNames() {
        List<String> companyTypeNames = companyServiceType.getAllCompanyTypeNames();
        return new ResponseEntity<>(companyTypeNames, HttpStatus.OK);
    }

}
