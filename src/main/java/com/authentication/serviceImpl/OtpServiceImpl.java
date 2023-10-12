package com.authentication.serviceImpl;

import com.authentication.model.OTP;
import com.authentication.payload.request.OtpResponse;
import com.authentication.payload.response.ResponseEntity;
import com.authentication.repository.OtpRepository;
import com.authentication.service.OtpService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
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
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Value("${spring.mail.username}")
    private String fromEmail;


//    @Override
//    public OtpResponse generateOtp(String email, String name,String password) {
//        String otpCode = CommonUtil.generateOTP(6);
//
//        OTP otp = this.otpRepository.findByEmailContaining
//                (email.length() > 10 ? email.trim().substring(email.length() - 10)
//                        : email.trim()).orElse(new OTP().builder().email(email.trim())
//                .otpCode(otpCode).count(1L).isUsed(false).created_at(CommonUtil.getDate()).name(name).password(password)
//                .expiredAt(CommonUtil.getExpiryDateTime()).build());
//        System.out.println("otp====="+otp);
//
//        if(otp.getId()!=null&&otp.getId()>0){
//            otp.setCount(otp.getCount()+1);
//            otp.setOtpCode(otpCode);
//            otp.setName(name);
//            otp.setPassword(new BCryptPasswordEncoder().encode(password));
//            otp.setExpiredAt(CommonUtil.getExpiryDateTime());
//        };
//
//        OTP save = this.otpRepository.save(otp);
//
//        if(save!=null)
//            return OtpResponse.builder().email(email).otp(otpCode).build();
//        else return null;
//    }

    @Override
    public OtpResponse generateOtp(String email, String name, String password) {

        Optional<OTP> existingOtpOptional = this.otpRepository.findByEmailContaining(email);

        String otpCode = CommonUtil.generateOTP(6);

        if (existingOtpOptional.isPresent()) {

            // OTP record already exists for the email
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
                return null;
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
                return null;
            }
        }
    }


    @Override
    public OTP findOtpByEmailAndOtpCode(String email, String otp) {
        return this.otpRepository.findByEmailContainingAndOtpCode(email,otp);
    }



//    private void sendOtpOnMail(String toEmail, String otp) {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        try {
//            helper.setFrom(fromEmail);
//            helper.setTo(toEmail);
//            helper.setSubject("Law Zoom - OTP Verification");
//
//            String htmlContent = "<!DOCTYPE html>\n" +
//                    "<html lang=\"en\">\n" +
//                    "<head>\n" +
//                    "    <meta charset=\"UTF-8\">\n" +
//                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
//                    "    <title>Law Zoom - OTP Verification</title>\n" +
//                    "    <style>\n" +
//                    "        body {\n" +
//                    "            font-family: 'Arial', sans-serif;\n" +
//                    "            margin: 0;\n" +
//                    "            padding: 0;\n" +
//                    "            background-color: #f4f4f4;\n" +
//                    "        }\n" +
//                    "        .container {\n" +
//                    "            width: 100%;\n" +
//                    "            max-width: 600px;\n" +
//                    "            margin: 0 auto;\n" +
//                    "        }\n" +
//                    "        .header {\n" +
//                    "            background-color: #3498db;\n" +
//                    "            color: #ffffff;\n" +
//                    "            padding: 20px;\n" +
//                    "            text-align: center;\n" +
//                    "        }\n" +
//                    "        .content {\n" +
//                    "            background-color: #ffffff;\n" +
//                    "            padding: 20px;\n" +
//                    "            border-radius: 5px;\n" +
//                    "            margin-top: 20px;\n" +
//                    "        }\n" +
//                    "    </style>\n" +
//                    "</head>\n" +
//                    "<body>\n" +
//                    "    <div class=\"container\">\n" +
//                    "        <div class=\"header\">\n" +
//                    "            <h2>Law Zoom</h2>\n" +
//                    "            <p>OTP Verification</p>\n" +
//                    "        </div>\n" +
//                    "        <div class=\"content\">\n" +
//                    "            <p>Hello,Kaushal</p>\n" +
//                    "            <p>Thank you for choosing Law Zoom. To complete your registration, use the following OTP:</p>\n" +
//                    "            <h3>Your OTP: {{otp}}</h3>\n" +
//                    "            <p>This OTP is valid for a limited time. Please do not share it with anyone.</p>\n" +
//                    "            <p>If you did not request this OTP, please ignore this email.</p>\n" +
//                    "            <p>Best regards,<br>Law Zoom Team (Kaushal)</p>\n" +
//                    "        </div>\n" +
//                    "    </div>\n" +
//                    "</body>\n" +
//                    "</html>\n";
//
//            htmlContent = htmlContent.replace("{{otp}}", otp);
//
//            helper.setText(htmlContent, true);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//
//        javaMailSender.send(message);
//    }

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
