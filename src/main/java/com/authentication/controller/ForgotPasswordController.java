//package com.authentication.controller;
//
//import com.authentication.payload.response.ResponseEntity;
//import com.authentication.serviceImpl.ForgotPasswordService;
//import com.authentication.serviceImpl.OtpServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class ForgotPasswordController {
//
//    @Autowired
//    private ForgotPasswordService forgotPasswordService;
//
//    @Autowired
//    private OtpServiceImpl otpService;
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> generateOTPAndSendOTP(@RequestParam String email) {
//
////        forgotPasswordService.generateAndSendOTP(email);
//        return otpService.sendOtpOnEmail(email);
//    }
//
//    @PostMapping("/verify-otp")
//    public ResponseEntity<?> verifyOTP(@RequestParam String email, @RequestParam String otp) {
//        boolean isOTPValid = forgotPasswordService.verifyOTP(email, otp);
//        if (isOTPValid) {
//            return ResponseEntity.ok("OTP is valid!");
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
//        }
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
//        forgotPasswordService.resetPassword(email, newPassword);
//        return ResponseEntity.ok("Password reset successfully!");
//    }
//}
