package com.authentication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@ToString
@Table(name = "otp")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Size(min = 10,max = 15,message = "Mobile length should be 10-15 digits.")
    private String mobile;

    private String email;

    @Column(name = "otp_code")
    private String otpCode;

    private String password;

    private Long count;

    @Column(name = "is_used")
    private boolean isUsed;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "expired_at")
    private Date expiredAt;



}
