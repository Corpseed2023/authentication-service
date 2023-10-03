package com.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class AuthenticationServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AuthenticationServiceApplication.class, args);
	}

	@GetMapping("/")
	public Principal getPrincipal(Principal principal){
		return principal;
	}


}
