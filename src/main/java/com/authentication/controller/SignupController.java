package com.authentication.controller;

import com.authentication.payload.request.SignupRequest;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.service.UserService;
import com.authentication.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api("Handle Signup related actions")
@RequestMapping("/api/auth/signup")
public class SignupController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Return signup result",response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully registered"),
            @ApiResponse(code = 500,message = "Something Went-Wrong"),
            @ApiResponse(code = 400,message = "Bad Request")
    })
    @PostMapping()
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest, HttpServletRequest httpServletRequest) {

        // Validate email format and Name
        if (!CommonUtil.isValidEmail(signupRequest.getEmail()) || !CommonUtil.isValidName(signupRequest.getName())) {
            return new ResponseEntity<String>().badRequest("Error: Invalid email or name format!");
        }
        return userService.signupUser(signupRequest, httpServletRequest);
    }


}
