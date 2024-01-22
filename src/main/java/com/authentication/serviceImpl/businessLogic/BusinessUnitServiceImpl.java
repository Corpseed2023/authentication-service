package com.authentication.serviceImpl.businessLogic;



import com.authentication.dto.businessUnitDto.BusinessUnitRequest;
import com.authentication.dto.businessUnitDto.BusinessUnitResponse;
import com.authentication.exception.NotFoundException;
import com.authentication.model.businessUnitModel.BusinessUnit;
import com.authentication.model.companyModel.Company;
import com.authentication.repository.businessRepo.BusinessUnitRepository;
import com.authentication.repository.companyRepo.CompanyRepository;
import com.authentication.service.businessService.BusinessUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BusinessUnitServiceImpl implements BusinessUnitService {


    @Autowired
    private BusinessUnitRepository businessUnitRepository;

    @Autowired
    private CompanyRepository companyRepository;

//    @Autowired
//    private TeamRepository teamRepository;


    @Override
    public BusinessUnitResponse createBusinessUnit(BusinessUnitRequest businessUnitRequest, Long companyId) {

        Optional<Company> companyData = companyRepository.findById(companyId);

        if (companyData.isPresent()) {
            BusinessUnit existingBusinessUnit = businessUnitRepository.findByAddress(businessUnitRequest.getAddress());

            if (existingBusinessUnit == null) {

//                Set<Team> teams = new HashSet<>();

//                if (businessUnitRequest.getTeamIds() != null && !businessUnitRequest.getTeamIds().isEmpty()) {
//                    for (Long teamId : businessUnitRequest.getTeamIds()) {
//                        Optional<Team> teamOptional = teamRepository.findById(teamId);
//                        if (teamOptional.isPresent()) {
//                            teams.add(teamOptional.get());
//                        } else {
//                            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with ID " + teamId + " not found");
//                        }
//                    }
//                }

                BusinessUnit newBusinessUnit = new BusinessUnit();

//
                newBusinessUnit.setAddress(businessUnitRequest.getAddress());
                newBusinessUnit.setCompany(companyData.get()); // Set the Company association
                newBusinessUnit.setBusinessActivityName(businessUnitRequest.getBusinessActivity());
                newBusinessUnit.setCity(businessUnitRequest.getCity());
                newBusinessUnit.setLocatedAt(businessUnitRequest.getLocatedAt());
                newBusinessUnit.setPermanentEmployee(businessUnitRequest.getPermanentEmployee());
                newBusinessUnit.setContractEmployee(businessUnitRequest.getContractEmployee());
                newBusinessUnit.setCreatedAt(businessUnitRequest.getCreatedAt());
                newBusinessUnit.setUpdatedAt(businessUnitRequest.getUpdatedAt());
                newBusinessUnit.setEnable(businessUnitRequest.isEnable());
                newBusinessUnit.setDateRegistration(businessUnitRequest.getDateRegistration());
                newBusinessUnit.setStates(businessUnitRequest.getStates());
                newBusinessUnit.setGstNumber(businessUnitRequest.getGstNumber());
//                newBusinessUnit.setTeams(teams);

                BusinessUnit savedBusinessUnit = businessUnitRepository.save(newBusinessUnit);

//                List<Long> savedTeamIds = savedBusinessUnit.getTeams().stream()
//                        .map(Team::getId)
//                        .collect(Collectors.toList());

                // Create and return a response
                BusinessUnitResponse response = new BusinessUnitResponse();
                response.setId(savedBusinessUnit.getId());
                response.setBusinessActivity(savedBusinessUnit.getBusinessActivityName());
                response.setCity(savedBusinessUnit.getCity());
                response.setLocatedAt(savedBusinessUnit.getLocatedAt());
                response.setPermanentEmployee(savedBusinessUnit.getPermanentEmployee());
                response.setContractEmployee(savedBusinessUnit.getContractEmployee());
                response.setAddress(savedBusinessUnit.getAddress());
                response.setCreatedAt(savedBusinessUnit.getCreatedAt());
                response.setUpdatedAt(savedBusinessUnit.getUpdatedAt());
                response.setEnable(savedBusinessUnit.isEnable());
                response.setDateRegistration(savedBusinessUnit.getDateRegistration());
                response.setStates(savedBusinessUnit.getStates());
                response.setGstNumber(savedBusinessUnit.getGstNumber());
                response.setCompanyId(savedBusinessUnit.getCompany().getId());
//                response.setTeamIds(savedTeamIds);

                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Business unit with the same address already exists");
            }
        }

        return null;
    }



    @Override
    public BusinessUnitResponse updateBusinessUnit(Long companyId, Long businessUnitId, BusinessUnitRequest businessUnitRequest) {
        // Check if the GST data exists
        Optional<Company> companyData = companyRepository.findById(companyId);

        if (!companyData.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found with ID: " + companyId);
        }

        // Check if the business unit exists
        Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.findById(businessUnitId);
        if (!businessUnitOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Business Unit not found with ID: " + businessUnitId);
        }

        BusinessUnit existingBusinessUnit = businessUnitOptional.get();

        // Update the business unit data
        existingBusinessUnit.setBusinessActivityName(businessUnitRequest.getBusinessActivity());
        existingBusinessUnit.setCity(businessUnitRequest.getCity());
        existingBusinessUnit.setLocatedAt(businessUnitRequest.getLocatedAt());
        existingBusinessUnit.setPermanentEmployee(businessUnitRequest.getPermanentEmployee());
        existingBusinessUnit.setContractEmployee(businessUnitRequest.getContractEmployee());
        existingBusinessUnit.setAddress(businessUnitRequest.getAddress());
        existingBusinessUnit.setGstNumber(businessUnitRequest.getGstNumber());
        existingBusinessUnit.setDateRegistration(businessUnitRequest.getDateRegistration());

//        Set<Team> teams = new HashSet<>();
//        if (businessUnitRequest.getTeamIds() != null && !businessUnitRequest.getTeamIds().isEmpty()) {
//            for (Long teamId : businessUnitRequest.getTeamIds()) {
//                Optional<Team> teamOptional = teamRepository.findById(teamId);
//                if (teamOptional.isPresent()) {
//                    teams.add(teamOptional.get());
//                } else {
//                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Team with ID " + teamId + " not found");
//                }
//            }
//        }

//        existingBusinessUnit.setTeams(teams);

        BusinessUnit savedBusinessUnit = businessUnitRepository.save(existingBusinessUnit);

        // Create a response manually
        BusinessUnitResponse response = new BusinessUnitResponse();
        response.setId(savedBusinessUnit.getId());
        response.setBusinessActivity(savedBusinessUnit.getBusinessActivityName());
        response.setCity(savedBusinessUnit.getCity());
        response.setLocatedAt(savedBusinessUnit.getLocatedAt());
        response.setPermanentEmployee(savedBusinessUnit.getPermanentEmployee());
        response.setContractEmployee(savedBusinessUnit.getContractEmployee());
        response.setAddress(savedBusinessUnit.getAddress());
        response.setCreatedAt(savedBusinessUnit.getCreatedAt());
        response.setUpdatedAt(savedBusinessUnit.getUpdatedAt());
        response.setEnable(savedBusinessUnit.isEnable());
        response.setGstNumber(savedBusinessUnit.getGstNumber());
        response.setDateRegistration(savedBusinessUnit.getDateRegistration());
//        response.setTeamIds(savedBusinessUnit.getTeams().stream().map(Team::getId).collect(Collectors.toList()));

        return response;
    }


//
//    @Override
//    public BusinessUnitResponse getBusinessUnit(Long gstId, Long businessUnitId) {
//        // Check if the GST data exists
//        Optional<Company> companyData = companyRepository.findById(gstId);
//
//        // Check if the business unit exists
//        Optional<BusinessUnit> businessUnit = businessUnitRepository.findById(businessUnitId);
//        if (!companyData.isPresent() || !businessUnit.isPresent()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GST or Business Unit not found");
//        }
//
//        // Create a response for the found business unit
//        BusinessUnit savedBusinessUnit = businessUnit.get();
//        BusinessUnitResponse response = new BusinessUnitResponse();
//        response.setId(savedBusinessUnit.getId());
//        response.setBusinessActivity(savedBusinessUnit.getBusinessActivity());
//        response.setCity(savedBusinessUnit.getCity());
//        response.setLocatedAt(savedBusinessUnit.getLocatedAt());
//        response.setPermanentEmployee(savedBusinessUnit.getPermanentEmployee());
//        response.setContractEmployee(savedBusinessUnit.getContractEmployee());
//        response.setAddress(savedBusinessUnit.getAddress());
//        response.setCreatedAt(savedBusinessUnit.getCreatedAt());
//        response.setUpdatedAt(savedBusinessUnit.getUpdatedAt());
//        response.setEnable(savedBusinessUnit.isEnable());
////        response.setGstId(savedBusinessUnit.getGst().getId());
////        response.setGstNumber(savedBusinessUnit.getGst().getGstNumber());
//////        response.setGstState(savedBusinessUnit.getGst().getState());
//
//        return response;
//    }
//
//

    @Override
    public List<BusinessUnitResponse> getAllBusinessUnits(Long companyId) {
        List<BusinessUnit> businessUnits = businessUnitRepository.findByCompanyId(companyId);
        List<BusinessUnitResponse> businessUnitResponses = new ArrayList<>();

        for (BusinessUnit businessUnit : businessUnits) {
            BusinessUnitResponse response = new BusinessUnitResponse();
            response.setId(businessUnit.getId());
            response.setBusinessActivity(businessUnit.getBusinessActivityName());
            response.setCity(businessUnit.getCity());
            response.setLocatedAt(businessUnit.getLocatedAt());
            response.setPermanentEmployee(businessUnit.getPermanentEmployee());
            response.setContractEmployee(businessUnit.getContractEmployee());
            response.setAddress(businessUnit.getAddress());
            response.setCreatedAt(businessUnit.getCreatedAt());
            response.setUpdatedAt(businessUnit.getUpdatedAt());
            response.setEnable(businessUnit.isEnable());
            response.setGstNumber(businessUnit.getGstNumber());
            response.setDateRegistration(businessUnit.getDateRegistration());
            response.setStates(businessUnit.getStates());

//            List<TeamResponse> teams = new ArrayList<>();
////
////            for (Team team : businessUnit.getTeams()) {
////                teams.add(new TeamResponse(team.getId(), team.getTeamName(), team.getCreatedAt(), team.getUpdatedAt(),
////                        team.isEnable(), team.getTeamLeadName(), team.getLeadDesignation(), team.getTeamType()));
////            }
//
//            response.setTeams(teams);

            businessUnitResponses.add(response);
        }

        return businessUnitResponses;
    }

//    @Override
//    public List<BusinessUnitResponse> getAllBusinessUnitsWithCompany(Long companyId) {
//        List<BusinessUnit> businessUnits = businessUnitRepository.findByCompanyId(companyId);
//
//        // Use a stream to map BusinessUnit objects to BusinessUnitResponse using the builder
//        List<BusinessUnitResponse> response = businessUnits.stream()
//                .map(this::mapBusinessUnitToResponse)
//                .collect(Collectors.toList());
//
//        return response;
//    }
//
//    public List<BusinessUnit> findByCompanyId(Long companyId) {
//        return businessUnitRepository.findByCompanyId(companyId);
//    }
//
//
//    private BusinessUnitResponse mapBusinessUnitToResponse(BusinessUnit businessUnit) {
//        return BusinessUnitResponse.builder()
//                .id(businessUnit.getId())
//                .companyId(businessUnit.getCompany().getId())
//                .businessActivity(businessUnit.getBusinessActivityName())
//                .city(businessUnit.getCity())
//                .locatedAt(businessUnit.getLocatedAt())
//                .permanentEmployee(businessUnit.getPermanentEmployee())
//                .contractEmployee(businessUnit.getContractEmployee())
//                .address(businessUnit.getAddress())
//                .createdAt(businessUnit.getCreatedAt())
//                .updatedAt(businessUnit.getUpdatedAt())
//                .dateRegistration(businessUnit.getDateRegistration())
//                .isEnable(businessUnit.isEnable())
//                .gstNumber(businessUnit.getGstNumber())
//                .states(businessUnit.getStates())
//                .build();
//    }


//    @Override
//    public List<BusinessUnitResponse> getAllBusinessUnitsWithAllCompany() {
//        return null;
//    }


    @Override
    public BusinessUnitResponse getBusinessUnitData(Long businessUnitId) {
        Optional<BusinessUnit> businessUnitOptional = businessUnitRepository.findById(businessUnitId);

        BusinessUnit businessUnit = businessUnitOptional.orElseThrow(() ->
                new NotFoundException("Business Unit not found for ID: " + businessUnitId));
        return mapBusinessUnitToResponse(businessUnit);
    }

    private BusinessUnitResponse mapBusinessUnitToResponse(BusinessUnit businessUnit) {
        BusinessUnitResponse response = new BusinessUnitResponse();
        if (businessUnit != null) {
            response.setId(businessUnit.getId());
            response.setCompanyId(businessUnit.getCompany().getId());
            response.setBusinessActivity(businessUnit.getBusinessActivityName());
            response.setCity(businessUnit.getCity());
            response.setLocatedAt(businessUnit.getLocatedAt());
            response.setPermanentEmployee(businessUnit.getPermanentEmployee());
            response.setContractEmployee(businessUnit.getContractEmployee());
            response.setAddress(businessUnit.getAddress());
            response.setCreatedAt(businessUnit.getCreatedAt());
            response.setUpdatedAt(businessUnit.getUpdatedAt());
            response.setDateRegistration(businessUnit.getDateRegistration());
            response.setEnable(businessUnit.isEnable());
            response.setGstNumber(businessUnit.getGstNumber());
            response.setStates(businessUnit.getStates());
        }
        return response;
    }

}
