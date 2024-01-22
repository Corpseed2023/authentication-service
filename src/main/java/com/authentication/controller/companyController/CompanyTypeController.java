//package com.authentication.controller.companyController;
//
//import com.lawzoom.companyservice.model.companyModel.CompanyType;
//import com.lawzoom.companyservice.serviceImpl.CompanyTypeImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin
//@RestController
////@RequestMapping("/companyServices/company")
//@RequestMapping("/companyServices/company")
//public class CompanyTypeController {
//
//    @Autowired
//    private CompanyTypeImpl companyTypeService;
//
//
//    @PostMapping("/createCompanyType")
//    public ResponseEntity<CompanyType> createCompanyType(@RequestBody CompanyType companyType) {
//        CompanyType createdCompanyType = companyTypeService.createCompanyType(companyType);
//        return new ResponseEntity<>(createdCompanyType, HttpStatus.CREATED);
//    }
//}
