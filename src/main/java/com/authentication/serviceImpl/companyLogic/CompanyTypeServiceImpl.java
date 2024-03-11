package com.authentication.serviceImpl.companyLogic;


import com.authentication.dto.companyDto.CompanyTypeResponse;
import com.authentication.dto.companyDto.CompanyTypeRequest;
import com.authentication.model.companyModel.CompanyType;
import com.authentication.repository.UserRepository;
import com.authentication.repository.companyRepo.CompanyTypeRepository;
import com.authentication.service.companyService.CompanyServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyTypeServiceImpl implements CompanyServiceType {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @Override
    public CompanyTypeResponse createCompanyType(CompanyTypeRequest companyTypeRequest, Long userId) {

//        Optional<User> userData = userRepository.findById(userId);

//        if (userData.isEmpty()) {
//            throw new NotFoundException("User not found with userId: " + userId);
//        }
//
//        // Validate if the user has SUPER_ADMIN role
//        Set<Roles> roles = userData.get().getRoles();
//
//        if (roles == null || roles.stream().noneMatch(role -> "SUPER_ADMIN".equals(role.getRole()))) {
//            throw new UnauthorizedException("User with userId: " + userId + " does not have SUPER_ADMIN role.");
//        }

        // Create a new CompanyType entity
        CompanyType companyType = new CompanyType();
        companyType.setCompanyTypeName(companyTypeRequest.getCompanyTypeName());
        companyType.setCreatedAt(new Date());
        companyType.setUpdatedAt(new Date());
        companyType.setEnable(companyTypeRequest.isEnable());
        companyType.setUserId(userId);

        // Save the CompanyType entity
        CompanyType savedCompanyType = companyTypeRepository.save(companyType);

        // Create and return the CompanyTypeResponse
        CompanyTypeResponse companyResponseType = new CompanyTypeResponse(
                savedCompanyType.getId(),
                savedCompanyType.getCompanyTypeName(),
                savedCompanyType.getCreatedAt(),
                savedCompanyType.getUpdatedAt(),
                savedCompanyType.isEnable(),
                savedCompanyType.getUserId()
        );

        return companyResponseType;
    }

    @Override
    public List<String> getAllCompanyTypeNames() {
        return companyTypeRepository.findAllCompanyTypeNames();
    }

}
