//package com.authentication.controller;
//
//
//
//import com.authentication.serviceImpl.MailSendSerivceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/auth")
//public class EmailController {
//
//    @Autowired
//    private MailSendSerivceImpl mailSendSerivce;
//
//    @PostMapping("/send")
//    public String sendEmail(String[] ccPersons, @RequestParam String[] emailTo)  {
////        String emailTo = "kaushlendra.pratap@corpseed.com";
////        String[] ccPersons = {"kaushlendra.pratap@corpseed.com", "rahul.jain@corpseed.com"};
//        String[] bccPersons = {"kaushlendra.pratap@corpseed.com", "kaushlendra.pratap@corpseed.com"};
//
//        mailSendSerivce.sendEmail(emailTo, ccPersons, bccPersons);
//
//        return "Email sent successfully!";
//    }
//}