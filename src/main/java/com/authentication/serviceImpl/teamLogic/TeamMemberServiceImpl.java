package com.authentication.serviceImpl.teamLogic;


import com.authentication.controller.PasswordController;
import com.authentication.controller.userController.UserController;
import com.authentication.dto.teamMemberDto.TeamMemberDetailsResponse;
import com.authentication.dto.teamMemberDto.TeamMemberRequest;
import com.authentication.dto.teamMemberDto.TeamMemberResponse;
import com.authentication.model.Roles;
import com.authentication.model.User;
import com.authentication.model.companyModel.Company;
import com.authentication.model.teamMemberModel.TeamMember;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import com.authentication.repository.companyRepo.CompanyRepository;
import com.authentication.repository.team.TeamMemberRepository;
import com.authentication.service.teamService.TeamMemberService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private PasswordController passwordController;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
     UserController userController;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Override
    public TeamMemberResponse createTeamMember(TeamMemberRequest teamMemberRequest,
                                               Long companyId, Long createdById) {

        // Check if the company exists
        Optional<Company> companySavedData = companyRepository.findById(companyId);

        if (!companySavedData.isPresent()) {
            throw new IllegalArgumentException("Company not found with ID: " + companyId);
        }

        Company companyData = companySavedData.get();

        // Check if the user exists
        Optional<User> userData = userRepository.findById(createdById);

        if (!userData.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + createdById);
        }

        BigInteger userCount = userRepository.existsByEmailOrMobileAndCompanyId(
                teamMemberRequest.getMemberMail(),
                teamMemberRequest.getMemberMobile(),
                companyId
        );

        if (userCount.intValue() > 0) {
            throw new IllegalArgumentException("User with the same email or mobile number already exists for the given company");
        }


        // Generate a random password for the user
        String randomPassword = passwordController.generateRandomPassword();

        // Create and save a new user
        User saveUser = new User();
        saveUser.setFirstName(teamMemberRequest.getMemberName());
        saveUser.setEmail(teamMemberRequest.getMemberMail());
        saveUser.setMobile(teamMemberRequest.getMemberMobile());
        saveUser.setPassword(new BCryptPasswordEncoder().encode(randomPassword));
        saveUser.setResourceType(teamMemberRequest.getTypeOfResource());
        saveUser.setCreatedAt(CommonUtil.getDate());
        saveUser.setUpdatedAt(CommonUtil.getDate());
        saveUser.setCompanyId(companyId);

        // Set user roles
        Set<Roles> roles = new HashSet<>();
        String roleName = teamMemberRequest.getAccessTypeName();
        Roles role = roleRepository.findByRole(roleName);
        roles.add(role);
        saveUser.setRoles(roles);

        // Save the user
        User savedUser = userRepository.save(saveUser);

        // Send an email to the user
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("kaushlendra.pratap@corpseed.com");
            helper.setTo(savedUser.getEmail());
            helper.setSubject("Set your Password");

            Context context = new Context();
            context.setVariable("emails", savedUser.getEmail());

            String emailContent = templateEngine.process("email-template", context);

            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        try {
            // Check if the team member request is null
            if (teamMemberRequest == null) {
                throw new NullPointerException("Please Enter Team Member Data");
            }

            // Create and save a new team member
            TeamMember teamMember = new TeamMember();
            teamMember.setMemberName(teamMemberRequest.getMemberName());
            teamMember.setMemberMail(teamMemberRequest.getMemberMail());
            teamMember.setMemberMobile(teamMemberRequest.getMemberMobile());
            teamMember.setTypeOfResource(teamMemberRequest.getTypeOfResource());
            teamMember.setCreatedAt(new Date());
            teamMember.setUpdatedAt(new Date());
            teamMember.setEnable(teamMemberRequest.isEnable());
            teamMember.setAccessTypeName(teamMemberRequest.getAccessTypeName());
            teamMember.setCompany(companyData);
            teamMember.setSuperAdminId(companyData.getUserId());
            teamMember.setReportingManagerId(companyData.getUserId());
            teamMember.setUserId(savedUser.getId());

            // Save the team member
            teamMember = teamMemberRepository.save(teamMember);

            // Create and return a TeamMemberResponse
            TeamMemberResponse teamMemberResponse = new TeamMemberResponse();
            teamMemberResponse.setMemberMail(teamMember.getMemberMail());
            teamMemberResponse.setMemberName(teamMember.getMemberName());
            teamMemberResponse.setCreatedAt(teamMember.getCreatedAt());
            teamMemberResponse.setUpdatedAt(teamMember.getUpdatedAt());
            teamMemberResponse.setEnable(teamMember.isEnable());
            teamMemberResponse.setMemberMobile(teamMember.getMemberMobile());
            teamMemberResponse.setTypeOfResource(teamMember.getTypeOfResource());
            teamMemberResponse.setAccessTypeName(teamMember.getAccessTypeName());
            teamMemberResponse.setReportingManagerId(teamMember.getId());
            teamMemberResponse.setUserId(teamMember.getUserId());

            return teamMemberResponse;

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException("Failed to create team members");
        }
    }



    @Override
    public TeamMemberResponse updateTeamMember(Long id, TeamMemberRequest teamMemberRequest) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(id);

        if (!optionalTeamMember.isPresent()) {

            String errorMessage = "TeamMember with ID " + id + " not found";
            throw new EntityNotFoundException(errorMessage);
        }

        TeamMember teamMember = optionalTeamMember.get();

        teamMember.setMemberName(teamMemberRequest.getMemberName());
        teamMember.setMemberMail(teamMemberRequest.getMemberMail());
        teamMember.setMemberMobile(teamMemberRequest.getMemberMobile());
        teamMember.setCreatedAt(new Date());
        teamMember.setUpdatedAt(new Date());
        teamMember.setEnable(teamMemberRequest.isEnable());
        teamMember.setTypeOfResource(teamMemberRequest.getTypeOfResource());


        teamMember = teamMemberRepository.save(teamMember);
//        System.out.println("kaushal");

        TeamMemberResponse teamMemberResponse = new TeamMemberResponse();

        teamMemberResponse.setCreatedAt(teamMember.getCreatedAt());
        teamMemberResponse.setUpdatedAt(teamMember.getUpdatedAt());
        teamMemberResponse.setEnable(teamMember.isEnable());
        teamMemberResponse.setMemberMail(teamMember.getMemberMail());
        teamMemberResponse.setMemberMobile(teamMember.getMemberMobile());
//        teamMemberResponse.setAccessType(teamMember.getAccessType());
        teamMemberResponse.setMemberName(teamMember.getMemberName());


        System.out.println("kaushal");

        return teamMemberResponse;
    }


    @Override
    public List<TeamMemberResponse> getAllTeamMembers(Long companyId) {
        List<TeamMember> teamMembers = teamMemberRepository.findAllByCompanyId(companyId);
        List<TeamMemberResponse> teamMemberResponses = new ArrayList<>();
        System.out.println("kaushal");


        for (TeamMember teamMember : teamMembers) {
            TeamMemberResponse teamMemberResponse = new TeamMemberResponse();
            System.out.println("kaushal");

            teamMemberResponse.setCreatedAt(teamMember.getCreatedAt());
            teamMemberResponse.setUpdatedAt(teamMember.getUpdatedAt());
            teamMemberResponse.setEnable(teamMember.isEnable());
            teamMemberResponse.setMemberName(teamMember.getMemberName());
            teamMemberResponse.setMemberMobile(teamMember.getMemberMobile());
            teamMemberResponse.setMemberMail(teamMember.getMemberMail());
            teamMemberResponse.setEnable(teamMember.isEnable());
//            teamMemberResponse.setCompanyId(teamMember.getCompanyId());
            teamMemberResponse.setTypeOfResource(teamMember.getTypeOfResource());
//            teamMemberResponse.setAccessType(teamMember.getAccessType());


            teamMemberResponses.add(teamMemberResponse);
        }

        return teamMemberResponses;
    }

    @Override
    public TeamMemberResponse getTeamMemberById(Long id) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(id);

        if (!optionalTeamMember.isPresent()) {
            System.out.println("kaushal");
            throw new EntityNotFoundException("TeamMember with ID " + id + " not found");
        }

        TeamMember teamMember = optionalTeamMember.get();
        TeamMemberResponse teamMemberResponse = new TeamMemberResponse();

        teamMemberResponse.setMemberName(teamMember.getMemberName());
        teamMemberResponse.setMemberMobile(teamMember.getMemberMobile());
        teamMemberResponse.setMemberMail(teamMember.getMemberMail());

        System.out.println("kaushal");
        teamMemberResponse.setCreatedAt(teamMember.getCreatedAt());
        teamMemberResponse.setUpdatedAt(teamMember.getUpdatedAt());
        teamMemberResponse.setEnable(teamMember.isEnable());


        return teamMemberResponse;
    }

    @Override
    public void removeTeamMember(Long memberId) {

        Optional<TeamMember> memberData = teamMemberRepository.findById(memberId);

        if (memberData.isPresent()) {
            TeamMember membertoDelete = memberData.get();

            teamMemberRepository.delete(membertoDelete);

        } else {
            throw new EntityNotFoundException("Member Not present in database");
        }

    }



    public TeamMemberDetailsResponse getTeamMemberDetailsByMail(String memberMail) {
        List<TeamMember> teamMembers = teamMemberRepository.findByMemberMail(memberMail);

        if (!teamMembers.isEmpty()) {
            TeamMember teamMember = teamMembers.get(0);
            Company company = companyRepository.findById(teamMember.getCompany().getId()).orElse(null);

            if (company != null) {
                return new TeamMemberDetailsResponse(
                        teamMember.getMemberName(),
                        company.getCompanyName(),
                        teamMember.getAccessTypeName(),
                        company.getId(),teamMember.getUserId()
                );
            }
        }
        return null;
    }

    public List<HashMap<String, Object>> getTeamMembersWithIdAndName(Long companyId) {
        Optional<Company> companyOptional = companyRepository.findById(companyId);

        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();

            // Use the repository method to get team members by companyId
            List<TeamMember> teamMembers = teamMemberRepository.findAllByCompanyId(company.getId());

            // Convert TeamMember objects to HashMaps with id and memberName
            return teamMembers.stream()
                    .map(teamMember -> {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("id", teamMember.getId());
                        map.put("memberName", teamMember.getMemberName());
                        //user id which store in  user table
                        map.put("userId",teamMember.getUserId());
                        return map;
                    })
                    .collect(Collectors.toList());
        } else {
            throw new EntityNotFoundException("Company with ID " + companyId + " not found");
        }
    }

}






