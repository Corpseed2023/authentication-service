package com.authentication.serviceImpl;

import com.authentication.model.OTP;
import com.authentication.payload.request.OtpResponse;
import com.authentication.repository.OtpRepository;
import com.authentication.service.OtpService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;


    @Override
    public OtpResponse generateOtp(String mobile, String name,String password) {
        String otpCode = CommonUtil.generateOTP(6);

        OTP otp = this.otpRepository.findByMobileContaining
                (mobile.length() > 10 ? mobile.trim().substring(mobile.length() - 10)
                        : mobile.trim()).orElse(new OTP().builder().mobile(mobile.trim())
                .otpCode(otpCode).count(1L).isUsed(false).created_at(CommonUtil.getDate()).name(name).password(password)
                .expiredAt(CommonUtil.getExpiryDateTime()).build());
        System.out.println("otp====="+otp);

        if(otp.getId()!=null&&otp.getId()>0){
            otp.setCount(otp.getCount()+1);
            otp.setOtpCode(otpCode);
            otp.setName(name);
            otp.setPassword(password);
            otp.setExpiredAt(CommonUtil.getExpiryDateTime());
        };

        OTP save = this.otpRepository.save(otp);
        if(save!=null)
            return OtpResponse.builder().mobile(mobile).otp(otpCode).build();
        else return null;
    }

    @Override
    public OTP findOtpByMobileAndOtpCode(String mobile, String otp) {
        return this.otpRepository.findByMobileContainingAndOtpCode(mobile,otp);
    }
}
