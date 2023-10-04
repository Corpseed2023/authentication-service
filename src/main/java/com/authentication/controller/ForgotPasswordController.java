package com.authentication.controller;

import com.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForgotPasswordController  {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @GetMapping("/forgot_password")
    public String showForgotPasswordForm() {

        return null;
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword() {
        return null;
    }

    public void sendEmail(){

    }


    @GetMapping("/reset_password")
    public String showResetPasswordForm() {

        return null;
    }

    @PostMapping("/reset_password")
    public String processResetPassword() {

        return null;
    }

}
