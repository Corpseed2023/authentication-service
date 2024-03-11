package com.authentication.dto.companyDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyTypeResponse {

    private Long id;
    private String companyTypeName;
    private Date createdAt;
    private Date updatedAt;
    private boolean isEnable;
    private Long userId;

}