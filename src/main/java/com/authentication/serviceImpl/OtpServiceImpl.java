package com.authentication.serviceImpl;

import com.authentication.model.OTP;
import com.authentication.payload.request.OtpResponse;
import com.authentication.repository.OtpRepository;
import com.authentication.service.OtpService;
import com.authentication.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    public OtpResponse generateOtp(String email, String name,String password) {
        String otpCode = CommonUtil.generateOTP(6);

        OTP otp = this.otpRepository.findByEmailContaining
                (email.length() > 10 ? email.trim().substring(email.length() - 10)
                        : email.trim()).orElse(new OTP().builder().email(email.trim())
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

        sendOtpOnMail(email,otp.getOtpCode());

        if(save!=null)
            return OtpResponse.builder().email(email).otp(otpCode).build();
        else return null;
    }

    @Override
    public OTP findOtpByEmailAndOtpCode(String email, String otp) {
        return this.otpRepository.findByEmailContainingAndOtpCode(email,otp);
    }

    private void sendOtpOnMail(String toEmail, String otp) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Law Zoom - OTP Verification");

            String htmlContent = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Law Zoom - OTP Verification</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: 'Arial', sans-serif;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            background-color: #f4f4f4;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            width: 100%;\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 0 auto;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #3498db;\n" +
                    "            color: #ffffff;\n" +
                    "            padding: 20px;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            background-color: #ffffff;\n" +
                    "            padding: 20px;\n" +
                    "            border-radius: 5px;\n" +
                    "            margin-top: 20px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <h2>Law Zoom</h2>\n" +
                    "            <p>OTP Verification</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <p>Hello,Kaushal</p>\n" +
                    "            <p>Thank you for choosing Law Zoom. To complete your registration, use the following OTP:</p>\n" +
                    "            <h3>Your OTP: {{otp}}</h3>\n" +
                    "            <p>This OTP is valid for a limited time. Please do not share it with anyone.</p>\n" +
                    "            <p>If you did not request this OTP, please ignore this email.</p>\n" +
                    "            <p>Best regards,<br>Law Zoom Team (kaushal)</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";

            htmlContent = htmlContent.replace("{{otp}}", otp);

            helper.setText(htmlContent, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(message);
    }


}
