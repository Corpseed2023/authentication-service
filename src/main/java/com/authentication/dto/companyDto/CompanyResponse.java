package com.authentication.dto.companyDto;


import com.authentication.model.companyModel.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyResponse {

    private Long companyId;

    private Long userId;

    private String companyType;

    private String companyName;

    private String firstName;

    private String lastName;

    private String businessEmailId;

    private String designation;

    private String companyState;

    private String companyCity;

    private String companyRegistrationNumber;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date companyRegistrationDate;

    private String companyCINNumber;

    private String companyRemarks;

    private String companyPinCode;

    private long companyTurnover;

    private String locatedAt;

    private String businessActivityName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    private boolean isEnable;

    private int permanentEmployee;

    private int contractEmployee;

    private String gstNumber;

    private String operationUnitAddress;


}
