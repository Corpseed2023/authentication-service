package com.authentication.controller;



import org.springframework.web.bind.annotation.RestController;
import java.util.Random;

@RestController
public class PasswordController {

    public String generateRandomPassword() {
        String charKey = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random rand = new Random();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            password.append(charKey.charAt(rand.nextInt(charKey.length())));
        }

        return password.toString();
    }
}
