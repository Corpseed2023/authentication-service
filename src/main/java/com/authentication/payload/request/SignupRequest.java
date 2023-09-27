package com.authentication.payload.request;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SignupRequest {

    private String name;
    private String mobile;
    private String otp;
    private String password;

    List<String> roleList;

}
