package com.authentication.controller;

import com.authentication.payload.request.OtpRequest;
import com.authentication.payload.request.OtpResponse;
import com.authentication.service.OtpService;
import com.authentication.utils.CommonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("Handle otp related actions")
@RequestMapping("/api/auth/otp")
public class OtpController {

    @Autowired
    private OtpService otpService;

    @ApiOperation(value = "generate otp",response = OtpResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully otp generated"),
            @ApiResponse(code = 500,message = "Something Went-Wrong"),
            @ApiResponse(code = 400,message = "Bad Request")
    })

    @PostMapping("/generateOTP")
    public ResponseEntity<OtpResponse> generateOtp(@RequestBody OtpRequest otpRequest) {
        // Validate email and name format using static methods directly
        if (!CommonUtil.isValidEmail(otpRequest.getEmail()) || !CommonUtil.isValidName(otpRequest.getName())) {
            // Handle invalid email or name format, return a 400 Bad Request response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new OtpResponse("Invalid email or name format", null));
        }

        OtpResponse otpResponse = this.otpService.generateOtp(otpRequest.getEmail(), otpRequest.getName(),
                otpRequest.getPassword());

        if (otpResponse != null) {
            otpService.sendOtpOnEmail(otpRequest.getEmail(), otpResponse.getOtp(), otpRequest.getName());
        }

        return ResponseEntity.ok(otpResponse);
    }



}
