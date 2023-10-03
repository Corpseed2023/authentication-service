package com.authentication.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class OtpRequest {

    private String email;

    private String name;

    private String password;


}
