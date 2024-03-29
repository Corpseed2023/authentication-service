package com.authentication.serviceImpl;

import com.authentication.model.OTP;
import com.authentication.payload.request.OtpResponse;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.repository.OtpRepository;
import com.authentication.repository.UserRepository;
import com.authentication.service.OtpService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Optional;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public OtpResponse generateOtp(String email, String name, String password) {

        // Check if user with the given email already exists
        if (userRepository.findByEmail(email).isPresent()) {
            // User already exists, throw 403 error
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User with email " + email + " already exists");
        }

        Optional<OTP> existingOtpOptional = this.otpRepository.findByEmailContaining(email);

        String otpCode = CommonUtil.generateOTP(6);

        if (existingOtpOptional.isPresent()) {
            // Existing OTP record for the email
            OTP existingOtp = existingOtpOptional.get();
            existingOtp.setCount(existingOtp.getCount() + 1);
            existingOtp.setOtpCode(otpCode);
            existingOtp.setName(name);
            existingOtp.setPassword(new BCryptPasswordEncoder().encode(password));
            existingOtp.setExpiredAt(CommonUtil.getExpiryDateTime());

            OTP updatedOtp = this.otpRepository.save(existingOtp);

            if (updatedOtp != null) {
                return new OtpResponse(email, otpCode);
            } else {
                // Handle update failure
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update OTP record");
            }
        } else {
            // No existing OTP record, create a new one
            OTP newOtp = new OTP();
            newOtp.setEmail(email.trim());
            newOtp.setOtpCode(otpCode);
            newOtp.setCount(1L);
            newOtp.setUsed(false);
            newOtp.setCreated_at(CommonUtil.getDate());
            newOtp.setName(name);
            newOtp.setPassword(new BCryptPasswordEncoder().encode(password));
            newOtp.setExpiredAt(CommonUtil.getExpiryDateTime());

            OTP savedOtp = this.otpRepository.save(newOtp);

            if (savedOtp != null) {
                return new OtpResponse(email, otpCode);
            } else {
                // Handle save failure
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save new OTP record");
            }
        }
    }


    @Override
    public OTP findOtpByEmailAndOtpCode(String email, String otp) {
        return this.otpRepository.findByEmailContainingAndOtpCode(email,otp);
    }



    public void sendOtpOnEmail(String toEmail, String otp, String name) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Law Zoom - OTP Verification");

            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("name", name);

            String emailContent = templateEngine.process("otp-email-template", context);

            helper.setText(emailContent, true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }


    public void sendLoginUserDetails(String username, String name,
                                     String systemName,
                                     String networkIPAddress,
                                     String systemIPAddress) {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(username);
            helper.setSubject("Law Zoom - Sign In Status");

            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("systemIPAddress", systemIPAddress);
            context.setVariable("systemName", systemName);
            context.setVariable("networkIPAddress", networkIPAddress);

            String emailContent = templateEngine.process("login-user-ip-status", context);

            helper.setText(emailContent, true);

        } catch (MessagingException e) {
            // Handle the exception appropriately, e.g., log it
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }

}
