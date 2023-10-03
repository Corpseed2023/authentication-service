package com.authentication.repository;

import com.authentication.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP,Long> {

    Optional<OTP> findByEmailContaining(String email);

    OTP findByEmailContainingAndOtpCode(String email, String otp);
}
