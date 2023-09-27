package com.authentication.service;

import com.authentication.model.OTP;
import com.authentication.payload.request.OtpResponse;

public interface OtpService {


    OtpResponse generateOtp(String mobile, String name,String password);

    OTP findOtpByMobileAndOtpCode(String mobile, String otp);
}
