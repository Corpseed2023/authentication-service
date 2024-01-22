package com.authentication.serviceImpl.companyLogic;


import com.authentication.dto.companyDto.CompanyRequest;
import com.authentication.dto.companyDto.CompanyResponse;
import com.authentication.dto.companyDto.CompanyResponseDetails;
import com.authentication.exception.NotFoundException;
import com.authentication.exception.UnauthorizedException;
import com.authentication.model.Roles;
import com.authentication.model.User;
import com.authentication.model.businessUnitModel.BusinessUnit;
import com.authentication.model.companyModel.Company;
import com.authentication.repository.UserRepository;
import com.authentication.repository.businessRepo.BusinessUnitRepository;
import com.authentication.repository.companyRepo.CompanyRepository;
import com.authentication.repository.companyRepo.CompanyTypeRepository;
import com.authentication.repository.team.TeamMemberRepository;
import com.authentication.service.companyService.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

//    @Autowired
//    private ComplianceMap complianceMap;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

//    @Autowired
//    private AuthenticationFeignClient authenticationFeignClient;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    private Map<String, Object> handleNotFoundError(String message) {
        Map<String, Object> errorResult = new HashMap<>();
        errorResult.put("error", message);
        return errorResult;
    }

    @Override
    public CompanyResponse createCompany(CompanyRequest companyRequest, Long userId) {
        // Check if the user has SUPER_ADMIN role in the authentication service
//        UserRequest userRequest = authenticationFeignClient.getUserId(userId);

        Optional<User> userData = userRepository.findById(userId);

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }


        // Validate if the user has SUPER_ADMIN role
        Set<Roles> roles = userData.get().getRoles();

        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRole()))) {
            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN role.");
        }


        // Create a new Company instance and populate its attributes
        Company company = new Company();
        company.setFirstName(companyRequest.getFirstName());
        company.setLastName(companyRequest.getLastName());
        company.setCompanyType(companyRequest.getCompanyType());
        company.setCinNumber(companyRequest.getCompanyCINNumber());
        company.setBusinessEmailId(companyRequest.getBusinessEmailId());
        company.setCompanyName(companyRequest.getCompanyName());
        company.setDesignation(companyRequest.getDesignation());
        company.setContractEmployee(companyRequest.getContractEmployee());
        company.setCreatedAt(new Date());
        company.setUpdatedAt(new Date());
        company.setTurnover(companyRequest.getCompanyTurnover());
        company.setGstNumber(companyRequest.getGstNumber());
        company.setEnable(true);
        company.setLocatedAt(companyRequest.getLocatedAt());
        company.setBusinessActivityName(companyRequest.getBusinessActivityName());
        company.setPinCode(companyRequest.getCompanyPinCode());
        company.setPermanentEmployee(companyRequest.getPermanentEmployee());
        company.setRegistrationNumber(companyRequest.getCompanyRegistrationNumber());
        company.setRegistrationDate(companyRequest.getCompanyRegistrationDate());
        company.setRemarks(companyRequest.getCompanyRemarks());
        company.setOperationUnitAddress(companyRequest.getOperationUnitAddress());
        company.setUserId(userId);

        // Save the company information in the database
        company = companyRepository.save(company);

        // Create a new Business Unit concurrently with the company creation process
        BusinessUnit businessUnit = new BusinessUnit();
        businessUnit.setCompany(company);
        businessUnit.setStates(companyRequest.getCompanyState());
        businessUnit.setCity(companyRequest.getCompanyCity());
        businessUnit.setLocatedAt(companyRequest.getLocatedAt());
        businessUnit.setPermanentEmployee(companyRequest.getPermanentEmployee());
        businessUnit.setContractEmployee(companyRequest.getContractEmployee());
        businessUnit.setAddress(companyRequest.getOperationUnitAddress());
        businessUnit.setCreatedAt(new Date());
        businessUnit.setUpdatedAt(new Date());
        businessUnit.setDateRegistration(companyRequest.getCompanyRegistrationDate());
        businessUnit.setEnable(companyRequest.isEnable());
        businessUnit.setGstNumber(companyRequest.getGstNumber());
        businessUnit.setBusinessActivityName(companyRequest.getBusinessActivityName());


        // Save BusinessUnit information in the database
        businessUnitRepository.save(businessUnit);

        // Prepare a CompanyResponse with information saved in the database
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setFirstName(company.getFirstName());
        companyResponse.setLastName(company.getLastName());
        companyResponse.setCompanyType(company.getCompanyType());
        companyResponse.setCompanyCINNumber(company.getCinNumber());
        companyResponse.setCompanyRegistrationNumber(company.getRegistrationNumber());
        companyResponse.setCompanyRegistrationDate(company.getRegistrationDate());
        companyResponse.setCompanyRemarks(company.getRemarks());
        companyResponse.setCompanyPinCode(company.getPinCode());
        companyResponse.setLocatedAt(company.getLocatedAt());
        companyResponse.setCreatedAt(company.getCreatedAt());
        companyResponse.setUpdatedAt(company.getUpdatedAt());
        companyResponse.setEnable(company.isEnable());
        companyResponse.setPermanentEmployee(company.getPermanentEmployee());
        companyResponse.setContractEmployee(company.getContractEmployee());
        companyResponse.setGstNumber(company.getGstNumber());
        companyResponse.setCompanyName(company.getCompanyName());
        companyResponse.setBusinessEmailId(company.getBusinessEmailId());
        companyResponse.setDesignation(company.getDesignation());
        companyResponse.setCompanyType(company.getCompanyType());
        companyResponse.setCompanyRegistrationNumber(company.getRegistrationNumber());
        companyResponse.setCompanyRegistrationDate(company.getRegistrationDate());
        companyResponse.setCompanyCINNumber(company.getCinNumber());
        companyResponse.setCompanyRemarks(company.getRemarks());
        companyResponse.setUpdatedAt(company.getUpdatedAt());
        companyResponse.setOperationUnitAddress(company.getOperationUnitAddress());
        companyResponse.setCompanyState(company.getState());
        companyResponse.setCompanyCity(company.getCity());
        companyResponse.setCompanyTurnover(companyRequest.getCompanyTurnover());
        companyResponse.setCompanyId(company.getId());
        companyResponse.setUserId(userId);
        companyResponse.setBusinessActivityName(company.getBusinessActivityName());

        return companyResponse;
    }

    @Override
    public List<CompanyResponseDetails> getCompaniesByUserId(Long userId) {
        List<Company> companies = companyRepository.findByUserId(userId);

        return companies.stream()
                .filter(Company::isEnable)  // Filter out disabled companies
                .map(this::mapToCompanyResponse)
                .filter(companyResponse -> companyResponse.getCompanyId() != null
                        && companyResponse.getRegistrationNumber() != null
                        && companyResponse.getCompanyName() != null
                        && companyResponse.getBusinessUnitCount() > 0)
                .collect(Collectors.toList());
    }

    private CompanyResponseDetails mapToCompanyResponse(Company company) {
        if (!company.isEnable()) {
            // Skip disabled companies
            return null;
        }

        CompanyResponseDetails companyResponseDetails = new CompanyResponseDetails();
        companyResponseDetails.setCompanyId(company.getId());
        companyResponseDetails.setRegistrationNumber(company.getRegistrationNumber());
        companyResponseDetails.setCompanyName(company.getCompanyName());

        List<BusinessUnit> businessUnitList = company.getBusinessUnits();

        int businessUnitCount = (businessUnitList != null) ? businessUnitList.size() : 0;
        companyResponseDetails.setBusinessUnitCount(businessUnitCount);

        long gstRegistrationCount = businessUnitList.stream()
                .filter(businessUnit -> businessUnit.getGstNumber() != null
                        && !businessUnit.getGstNumber().isEmpty()).count();

        companyResponseDetails.setGstRegistrationCount((int) gstRegistrationCount);

        return companyResponseDetails;
    }



    @Override
    public CompanyResponse updateCompany(CompanyRequest companyRequest, Long companyId, Long userId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);

//        UserRequest userRequest = authenticationFeignClient.getUserId(userId);
//
//        if (userRequest == null) {
//            throw new NotFoundException("User not found with userId: " + userId);
//        }

        Optional<User> userData = userRepository.findById(userId);

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }


        // Validate if the user has SUPER_ADMIN role
        Set<Roles> roles = userData.get().getRoles();
        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRole()))) {
            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN role.");
        }

        if (companyOptional.isPresent()) {
            Company companyData = companyOptional.get();

            // Copy all fields from companyRequest to companyData
            companyData.setCompanyName(companyRequest.getCompanyName());
            companyData.setBusinessEmailId(companyRequest.getBusinessEmailId());
            companyData.setFirstName(companyRequest.getFirstName());
            companyData.setLastName(companyRequest.getLastName());
            companyData.setRegistrationNumber(companyRequest.getCompanyRegistrationNumber());
            companyData.setRegistrationDate(companyRequest.getCompanyRegistrationDate());
            companyData.setCinNumber(companyRequest.getCompanyCINNumber());
            companyData.setRemarks(companyRequest.getCompanyRemarks());
            companyData.setPinCode(companyRequest.getCompanyPinCode());
            companyData.setTurnover(companyRequest.getCompanyTurnover());
            companyData.setLocatedAt(companyRequest.getLocatedAt());
            companyData.setCreatedAt(companyRequest.getCreatedAt());
            companyData.setUpdatedAt(new Date());
            companyData.setEnable(companyRequest.isEnable());
            companyData.setPermanentEmployee(companyRequest.getPermanentEmployee());
            companyData.setContractEmployee(companyRequest.getContractEmployee());
            companyData.setGstNumber(companyRequest.getGstNumber());
            companyData.setOperationUnitAddress(companyRequest.getOperationUnitAddress());

            // Save the updated company data
            Company savedData = companyRepository.save(companyData);

            // Map the updated data to the response
            CompanyResponse companyResponse = new CompanyResponse();
            companyResponse.setCompanyId(savedData.getId());
            companyResponse.setCompanyName(savedData.getCompanyName());
            companyResponse.setBusinessEmailId(savedData.getBusinessEmailId());
            companyResponse.setFirstName(savedData.getFirstName());
            companyResponse.setLastName(savedData.getLastName());
            companyResponse.setCompanyRegistrationNumber(savedData.getRegistrationNumber());
            companyResponse.setCompanyRegistrationDate(savedData.getRegistrationDate());
            companyResponse.setCompanyCINNumber(savedData.getCinNumber());
            companyResponse.setCompanyRemarks(savedData.getRemarks());
            companyResponse.setCompanyPinCode(savedData.getPinCode());
            companyResponse.setCompanyTurnover(savedData.getTurnover());
            companyResponse.setLocatedAt(savedData.getLocatedAt());
            companyResponse.setCreatedAt(savedData.getCreatedAt());
            companyResponse.setUpdatedAt(savedData.getUpdatedAt());
            companyResponse.setEnable(savedData.isEnable());
            companyResponse.setPermanentEmployee(savedData.getPermanentEmployee());
            companyResponse.setContractEmployee(savedData.getContractEmployee());
            companyResponse.setGstNumber(savedData.getGstNumber());
            companyResponse.setOperationUnitAddress(savedData.getOperationUnitAddress());

            return companyResponse;
        }

        throw new NotFoundException("Company not found with companyId: " + companyId);
    }

    @Override
    public void disableCompany(Long id , Long userId) throws NotFoundException {
        Optional<User> userData = userRepository.findById(userId);

        if (userData == null) {
            throw new NotFoundException("User not found with userId: " + userId);
        }


        // Validate if the user has SUPER_ADMIN role
        Set<Roles> roles = userData.get().getRoles();
        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRole()))) {
            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN role.");
        }

        Optional<Company> companyData = companyRepository.findById(id);
        if (companyData.isPresent()) {
            Company company = companyData.get();
            company.disable(); // Set isEnable to false
            companyRepository.save(company); // Save the updated company
        } else {
            throw new NotFoundException("Company with ID " + id + " not found");
        }
    }

//
//    public List<Map<String, Object>> getAllCompanyDetailsV2() {
//        List<Company> companies = companyRepository.findAll();
//        Map<Long,Integer>compCount=complianceMap.getComplianceCount();
//        System.out.println(compCount);
//
//        List<Map<String,Object>>result = new ArrayList<>();
//        for(Company c:companies){
//            Map<String,Object>res = new HashMap<>();
//            res.put("id",c.getId());
//            res.put("name",c.getCompanyName());
//            res.put("businessUnit",c.getBusinessUnits()!=null?c.getBusinessUnits().stream().map(i->i.getAddress()).collect(Collectors.toSet()) : "NA");
////            res.put("businessActivityName",c.getBusinessActivityName());
////            res.put("team",c.getTeams());
//            res.put("lastUpdatedDate",c.getUpdatedAt());
//            res.put("complianceCount",compCount.get(c.getId()));
////            res.put("updatedBy",c);
//            result.add(res);
//        }
//        return result;
//    }
//
//    public List<CompanyBusinessUnitDto> getCompanyUnitComplianceDetails(Long userId) {
//
//        List<Company> companies = companyRepository.findByUserId(userId);
//
//
//        List<CompanyBusinessUnitDto> companyBusinessUnitDtos = new ArrayList<>();
//
//        List<Map<String, Object>> totalCompliance = complianceMap.getComplianceCountPerCompanyAndBusinessUnit();
//
//        for (Company company : companies) {
//            for (BusinessUnit businessUnit : company.getBusinessUnits()) {
//                CompanyBusinessUnitDto dto = new CompanyBusinessUnitDto();
//                dto.setCompanyId(company.getId());
//                dto.setCompanyName(company.getCompanyName());
//                dto.setBusinessUnitId(businessUnit.getId());
//                dto.setBusinessActivityName(businessUnit.getBusinessActivityName());
//                dto.setBusinessUnitAddress(businessUnit.getAddress());
//                dto.setLastUpdated(businessUnit.getUpdatedAt());
//
//                List<TotalComplianceDto> totalComplianceDtos = totalCompliance.stream()
//                        .filter(map -> String.valueOf(company.getId()).equals(String.valueOf(map.get("companyId")))
//                                && String.valueOf(businessUnit.getId()).equals(String.valueOf(map.get("businessUnitId"))))
//                        .map(map -> {
//                            TotalComplianceDto totalComplianceDto = new TotalComplianceDto();
//                            totalComplianceDto.setTotalCompliance((int) map.get("complianceCount"));
//                            return totalComplianceDto;
//                        })
//                        .collect(Collectors.toList());
//
//                dto.setTotalCompliance(totalComplianceDtos);
//
//
//                companyBusinessUnitDtos.add(dto);
//            }
//        }
//
//        return companyBusinessUnitDtos;
//    }
//
//    @Override
//    public CompanyResponse getCompanyData(Long companyId) {
//        Optional<Company> companyOptional = companyRepository.findById(companyId);
//        if (companyOptional.isPresent()) {
//            Company company = companyOptional.get();
//            // Convert Company entity to CompanyResponse DTO
//            CompanyResponse companyResponse = convertToCompanyResponse(company);
//            return companyResponse;
//        } else {
//            throw new NotFoundException("Company with ID " + companyId + " not found");
//        }
//    }
//
//    // Other service methods
//
//    private CompanyResponse convertToCompanyResponse(Company company) {
//
//        return new CompanyResponse(
//                company.getId(),
//                company.getUserId(),
//                company.getCompanyType(),
//                company.getCompanyName(),
//                company.getFirstName(),
//                company.getLastName(),
//                company.getBusinessEmailId(),
//                company.getDesignation(),
//                company.getState(),
//                company.getCity(),
//                company.getRegistrationNumber(),
//                company.getRegistrationDate(),
//                company.getCinNumber(),
//                company.getRemarks(),
//                company.getPinCode(),
//                company.getTurnover(),
//                company.getLocatedAt(),
//                company.getBusinessActivityName(),
//                company.getCreatedAt(),
//                company.getUpdatedAt(),
//                company.isEnable(),
//                company.getPermanentEmployee(),
//                company.getContractEmployee(),
//                company.getGstNumber(),
//                company.getOperationUnitAddress()
//        );
//    }
}



