package com.authentication.controller;


import com.authentication.exception.UserNotFoundException;
import com.authentication.model.User;
import com.authentication.payload.request.UserRequest;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.payload.response.UserResponse;
import com.authentication.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
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
    public ResponseEntity<?> validateEmailAndSendOTP(@RequestParam String email) {


//        forgotPasswordService.generateAndSendOTP(email);
        return userService.checkUserExistanceAndSendOTP(email);
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOTP(@RequestParam String email, @RequestParam Integer otp) {
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
    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        try {
            userService.resetPassword(email, newPassword);
            return new ResponseEntity().ok("Password reset successfully");
        } catch (UserNotFoundException e) {
            return new ResponseEntity().notFound("User not found".getClass());
        }
    }




    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String emails, Model model) {
        model.addAttribute("emails", emails);
        return "templates/test.html";
    }


    @PostMapping("/createTeamMember")
    public ResponseEntity<UserResponse> createTeamMemberUsers(@RequestBody UserRequest userRequest)
            throws MalformedURLException {
        return this.userService.createTeamMemberUser(userRequest);
    }


    @GetMapping("/getUserId")
    public User getUserById(@RequestParam Long userId) {
        return this.userService.getUserById(userId);
    }
}
