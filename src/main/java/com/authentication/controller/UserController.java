package com.authentication.controller;

import com.authentication.exception.UserNotFoundException;
import com.authentication.model.User;
import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@Api("Handle User related actions")
@RequestMapping("/api/auth/user")
public class UserController {

    @Autowired
    private UserService userService;



    @ApiOperation(value = "Return signup result", response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully registered"),
            @ApiResponse(code = 500, message = "Something Went-Wrong"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    @PostMapping("/save")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        return this.userService.createUser(userRequest);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> validateEmailAndSendOTP(@RequestBody String email) {

//        forgotPasswordService.generateAndSendOTP(email);
        return userService.checkUserExistanceAndSendOTP(email);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestBody String email, @RequestBody Integer otp) {
        try {
            boolean isOTPValid = userService.verifyOTP(email, otp);
            if (isOTPValid) {
                return new ResponseEntity().ok("OTP verified successfully");
            } else {
                return new ResponseEntity<>().fail("Invalid OTP", String.valueOf(HttpStatus.UNAUTHORIZED));
            }
        } catch (UserNotFoundException e) {
            return new ResponseEntity().notFound("User not found".getClass());
        }

    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody String email, @RequestBody String newPassword) {
        try {
            userService.resetPassword(email, newPassword);
            return new ResponseEntity().ok("Password reset successfully");
        } catch (UserNotFoundException e) {
            return new ResponseEntity().notFound("User not found".getClass());
        }
    }

    @PostMapping("/createTeamMember")
    public ResponseEntity<?> createTeamMemberUsers(@RequestBody UserRequest userRequest) throws MalformedURLException {
        return this.userService.createTeamMemberUser(userRequest);
    }

}
