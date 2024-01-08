package com.authentication.serviceImpl;

import com.authentication.exception.UserNotFoundException;
import com.authentication.model.OTP;
import com.authentication.model.Roles;
import com.authentication.model.Subscription;
import com.authentication.model.User;
import com.authentication.payload.request.SignupRequest;
import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.MessageResponse;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.payload.response.UserResponse;
import com.authentication.repository.OtpRepository;
import com.authentication.repository.RoleRepository;
import com.authentication.repository.SubscriptionRepository;
import com.authentication.repository.UserRepository;
import com.authentication.service.OtpService;
import com.authentication.service.UserService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Override
    public ResponseEntity<?> signupUser(SignupRequest signupRequest, HttpServletRequest httpServletRequest) {
        // Check if the user already exists
        User existingUser = userRepository.findByEmail(signupRequest.getEmail()).orElse(null);
        if (existingUser != null) {
            return new ResponseEntity<String>().badRequest("Error: User Already Exists!");
        }

        // Set remote address in the new user
        String remoteAddress = httpServletRequest.getRemoteAddr();

        // Find OTP and check if it's valid
        OTP otp = otpService.findOtpByEmailAndOtpCode(signupRequest.getEmail(), signupRequest.getOtp());
        if (otp == null) {
            return new ResponseEntity<String>().badRequest("Enter a valid OTP!");
        }

        if (isOtpExpired(otp)) {
            return new ResponseEntity<String>().badRequest("Error: OTP has expired.");
        }

        // Mark the OTP as used
        otp.setUsed(true);
        otpRepository.save(otp); // Assuming you have a method like saveOtp in your OtpService

        // Assign roles to the user
        Set<Roles> roles;

        try {
            roles = userRoleData(signupRequest.getRoleList());
        } catch (IllegalArgumentException e) {
            // Handle the case where some roles do not exist
            return new ResponseEntity<String>().badRequest(e.getMessage());
        }

        // Create a new user
        User newUser = new User();
        newUser.setUuid(UUID.randomUUID().toString()); // Generate and set UUID
        newUser.setMobile(signupRequest.getEmail());
        newUser.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        newUser.setResourceType("In-House");
        newUser.setCreatedAt(new Date());
        newUser.setDesignation("SUBSCRIBER");
        newUser.setUpdatedAt(new Date());
        newUser.setEnable(true);
        newUser.setEmail(signupRequest.getEmail());
        newUser.setRemoteAddress(remoteAddress);

        if (!roles.isEmpty()) {
            // If roles are zero, null, or empty, check if the user is signing up for the first time
            if (isFirstTimeSignup(newUser)) {
                // If first time, assign the SUBSCRIBER role by default
                Roles subscriberRole = roleRepository.findByRole("SUPER_ADMIN");

                if (subscriberRole == null) {
                    // If SUBSCRIBER role does not exist, return a bad request with an error message
                    return new ResponseEntity<String>().badRequest("Error: Default role not available!");
                }

                roles = new HashSet<>();
                roles.add(subscriberRole);

                // Save the relationship in the user_roles table
                newUser.setRoles(roles);
                userRepository.save(newUser);
            } else {
                // If not the first time, assign an empty set of roles
                roles = new HashSet<>();
            }
        }

        newUser.setRoles(roles);
        userRepository.save(newUser);

        // Create a subscription record with a 15-day trial period
        Date currentDate = new Date();
        Date trialEndDate = new Date(currentDate.getTime() + (15 * 24 * 60 * 60 * 1000)); // 15 days in milliseconds

        Subscription subscription = new Subscription(newUser, currentDate, trialEndDate);
        subscriptionRepository.save(subscription);

        return new ResponseEntity<MessageResponse>().ok(new MessageResponse("Signup Success"));
    }


    // Check if the user is signing up for the first time
    private boolean isFirstTimeSignup(User user) {
        // You can implement the logic to check if this is the first time signup for the user
        // For example, check if the user has any roles assigned
        return user.getRoles() == null || user.getRoles().isEmpty();
    }

    private boolean isOtpExpired(OTP otp) {
        if (otp == null || otp.getExpiredAt() == null) {
            return true; // If OTP or its expiration date is not available, consider it expired
        }
        Date currentDateTime = new Date();
        return currentDateTime.after(otp.getExpiredAt());
    }

    private Set<Roles> userRoleData(List<String> rolesList) {
        // Find existing roles in the role table
        Set<Roles> existingRoles = roleRepository.findRoleList(rolesList);

        // Check if all roles in rolesList exist in the role table
        if (existingRoles.size() < rolesList.size()) {
            // Handle the case where some roles do not exist
            throw new IllegalArgumentException("Error: Some roles specified do not exist in the role table");
        }

        System.out.println(existingRoles);
        return existingRoles;
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
        saveUser.setAssociated(userRequest.isAssociated());

        this.userRepository.save(saveUser);

        return new ResponseEntity<User>().ok(saveUser);
    }

    public ResponseEntity<?> checkUserExistanceAndSendOTP(String email) {
        Optional<User> userExist = this.userRepository.findByEmail(email);

        if (userExist.isPresent()) {
            User user = userExist.get();

            // Generate OTP
            String otpCode = CommonUtil.generateOTP(6);

            // Save OTP to user entity
            user.setOtp(Integer.parseInt(otpCode));
            userRepository.save(user);

            // Send OTP via email
            otpService.sendOtpOnEmail(user.getEmail(), otpCode, user.getFirstName());

            return new ResponseEntity().ok("OTP sent successfully");
        } else {
            return new ResponseEntity().badRequest("User NOt found");
        }
    }

    public boolean verifyOTP(String email, Integer otp) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Check if OTP matches
            return otp.equals(user.getOtp());
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public void resetPassword(String email, String newPassword) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Reset password
            user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
            userRepository.save(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    @Override
    public UserResponse createTeamMemberUser(UserRequest userRequest)  {

        User saveUser = new User();

//        Set<Roles> persistedRoles = userRequest.getRoles().stream()
//                .map(role -> {
//                    Roles foundRole = roleRepository.findByRole(role.getRole());
//                    if (foundRole == null) {
//                        throw new EntityNotFoundException("Role not found: " + role.getRole());
//                    }
//                    return foundRole;
//                })
//                .collect(Collectors.toSet());

//        String remoteAddress = httpServletRequest.getRemoteAddr();


//        saveUser.setRoles(persistedRoles);
        saveUser.setFirstName(userRequest.getFirstName());
        saveUser.setUuid(UUID.randomUUID().toString()); // Generate and set UUID
        saveUser.setLastName(userRequest.getLastName());
        saveUser.setEmail(userRequest.getEmail());
        saveUser.setMobile(userRequest.getMobile());
        saveUser.setPassword(CommonUtil.encodePassword(userRequest.getPassword()));
        saveUser.setDesignation(userRequest.getDesignation());
        saveUser.setResourceType(userRequest.getResourceType());
        saveUser.setCreatedAt(CommonUtil.getDate());
        saveUser.setUpdatedAt(CommonUtil.getDate());
        saveUser.setCompanyId(userRequest.getCompanyId());
        saveUser.setSubscribed(false);
//        saveUser.setRemoteAddress(remoteAddress);
//        saveUser.setRoles(userRequest.getRoles());
        saveUser.setCompanyId(userRequest.getCompanyId());
        this.userRepository.save(saveUser);


        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);


        try {
            helper.setFrom("kaushlendra.pratap@corpseed.com");
            helper.setTo(userRequest.getEmail());
            helper.setSubject("Set your Password");

            Context context = new Context();
            context.setVariable("emails", userRequest.getEmail());

            String emailContent = templateEngine.process("email-template", context);

            helper.setText(emailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        UserResponse userResponseData = new UserResponse();

        userResponseData.setFirstName(saveUser.getFirstName());
        userResponseData.setLastName(saveUser.getLastName());
        userResponseData.setUserId(saveUser.getId());


        return userResponseData;
    }

    @Override
    public User getUserById(Long userId) {
         Optional<User>u=userRepository.findById(userId);
         return u.get();
    }



    @Override
    public ResponseEntity<?> updateUser(Long userId, UserRequest updatedUserRequest) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();

            existingUser.setFirstName(updatedUserRequest.getFirstName());
            existingUser.setLastName(updatedUserRequest.getLastName());
            existingUser.setEmail(updatedUserRequest.getEmail());
            existingUser.setMobile(updatedUserRequest.getMobile());
            existingUser.setPassword(CommonUtil.encodePassword(updatedUserRequest.getPassword()));
            existingUser.setDesignation(updatedUserRequest.getDesignation());
            existingUser.setResourceType(updatedUserRequest.getResourceType());
            existingUser.setRoles(updatedUserRequest.getRoles());
            existingUser.setAssociated(updatedUserRequest.isAssociated()); // Update isAssociated field
            existingUser.setUpdatedAt(CommonUtil.getDate());

            // Save the updated user
            userRepository.save(existingUser);

            return new ResponseEntity<User>().ok(existingUser);
        } else {
            return new ResponseEntity<String>().notFound("User not found".getClass());
        }
    }

    @Override
    public ResponseEntity<?> updateIsAssociatedAndIsSubscribe(Long userId, boolean isAssociated,
                                                boolean isSubscribed) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User existingUser = userOptional.get();
            existingUser.setAssociated(isAssociated);
            existingUser.setSubscribed(isSubscribed);

            // Save the updated user with the new isAssociated value
            userRepository.save(existingUser);

            return new ResponseEntity<User>().ok(existingUser);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }
}
