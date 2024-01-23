package com.authentication.service;

import com.authentication.model.User;
import com.authentication.payload.request.SignupRequest;
import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.payload.response.UserResponse;

import java.net.MalformedURLException;

public interface UserService {

    ResponseEntity<?> signupUser(SignupRequest signupRequest);

    ResponseEntity createUser(UserRequest userRequest);

    ResponseEntity<?> checkUserExistanceAndSendOTP(String email);

    boolean verifyOTP(String email, Integer otp);

    void resetPassword(String email, String newPassword);

    ResponseEntity<UserResponse> createTeamMemberUser(UserRequest userRequest) throws MalformedURLException;

    UserResponse getUserById(Long userId);

    ResponseEntity<?> updateUser(Long userId, UserRequest updatedUserRequest);

    ResponseEntity<?> updateIsAssociated(Long userId, boolean isAssociated);
}
