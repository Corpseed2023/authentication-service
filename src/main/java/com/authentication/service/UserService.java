package com.authentication.service;

import com.authentication.payload.request.SignupRequest;
import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.ResponseEntity;

public interface UserService {

    ResponseEntity<?> signupUser(SignupRequest signupRequest);

    ResponseEntity createUser(UserRequest userRequest);
}
