package com.authentication.controller;

import com.authentication.model.SignupUser;
import com.authentication.payload.request.OtpRequest;
import com.authentication.payload.request.OtpResponse;
import com.authentication.service.OtpService;
import com.authentication.service.SignupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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

    @Autowired
    private SignupService signupService;

    @ApiOperation(value = "generate otp",response = OtpResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Successfully otp generated"),
            @ApiResponse(code = 500,message = "Something Went-Wrong"),
            @ApiResponse(code = 400,message = "Bad Request")
    })
//    @PostMapping("/generateOTP")
//    public OtpResponse generateOtp(@RequestBody OtpRequest otpRequest){
//
////        SignupUser signupUser1 = this.signupService.createUserDetail(signupUser);
//
//        return this.otpService.generateOtp(otpRequest.getEmail(),otpRequest.getName(),otpRequest.getPassword());
//    }

    @PostMapping("/generateOTP")
    public OtpResponse generateOtp(@RequestBody OtpRequest otpRequest, Model model) {
        OtpResponse otpResponse = this.otpService.generateOtp(otpRequest.getEmail(), otpRequest.getName(), otpRequest.getPassword());

        if (otpResponse != null) {
//            model.addAttribute("otp", otpResponse.getOtp());
//            model.addAttribute("name", otpRequest.getName());
            otpService.sendOtpOnEmail(otpRequest.getEmail(), otpResponse.getOtp(), otpRequest.getName());
        }

        return otpResponse;
    }

}
