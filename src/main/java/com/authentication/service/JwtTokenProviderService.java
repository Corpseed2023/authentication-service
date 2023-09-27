package com.authentication.service;


import org.springframework.security.core.Authentication;

public interface JwtTokenProviderService {

    String generateJwtToken(Authentication authentication);
}
