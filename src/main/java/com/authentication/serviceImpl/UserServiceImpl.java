package com.authentication.serviceImpl;

import com.authentication.model.OTP;
import com.authentication.model.Roles;
import com.authentication.model.User;
import com.authentication.payload.request.SignupRequest;
import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.MessageResponse;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.UserRepository;
import com.authentication.service.OtpService;
import com.authentication.service.UserService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OtpService otpService;

    @Override
    public ResponseEntity<?> signupUser(SignupRequest signupRequest) {
        OTP otp = this.otpService.findOtpByEmailAndOtpCode(signupRequest.getEmail(), signupRequest.getOtp());

        if (otp == null)
            return new ResponseEntity<String>().badRequest("Enter a valid OTP !!");

        User user = this.userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
        if (user != null)
            return new ResponseEntity<String>().badRequest("Error : User Already Exist !!");

        User newUser = new User();
        newUser.setId(0L);
        newUser.setMobile(signupRequest.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        newUser.setResourceType("In-House");
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        newUser.setEnable(true);
        newUser.setEmail(signupRequest.getEmail());

        newUser.setRoles(userRoleData(signupRequest.getRoleList()));

        this.userRepository.save(newUser);

        return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Signup Success"));
    }


    private Set<Roles> userRoleData(List<String> rolesList) {


        Set<Roles> roles = roleRepository.findRoleList(rolesList);
        System.out.println(roles);

        return new HashSet<>(roles);
    }

    @Override
    public ResponseEntity<?> createUser(UserRequest userRequest) {
        OTP otp = this.otpService.findOtpByEmailAndOtpCode(userRequest.getMobile(), userRequest.getOtp());

        if (otp == null)
            return new ResponseEntity<String>().badRequest("Enter a valid OTP !!");

        User findUser = this.userRepository.findByMobileOrEmail(userRequest.getMobile(), userRequest.getEmail()).orElse(null);
        if (findUser != null)
            return new ResponseEntity<String>().badRequest("Error : User Already Exist !!");

        User saveUser = new User();
        saveUser.setFirstName(userRequest.getFirstName());
        saveUser.setLastName(userRequest.getLastName());
        saveUser.setEmail(userRequest.getEmail());
        saveUser.setMobile(userRequest.getMobile());
        saveUser.setPassword(CommonUtil.encodePassword(userRequest.getPassword()));
        saveUser.setDesignation(userRequest.getDesignation());
        saveUser.setResourceType(userRequest.getResourceType());
        saveUser.setCreatedAt(CommonUtil.getDate());
        saveUser.setUpdatedAt(CommonUtil.getDate());
        saveUser.setEnable(true);
        saveUser.setRoles(userRequest.getRoles());

        this.userRepository.save(saveUser);

        return new ResponseEntity<User>().ok(saveUser);
    }
}
