package com.authentication.dto.companyDto;

import com.authentication.model.companyModel.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CompanyRequest {


    private String companyType;

    private String firstName;

    private String lastName;

    @Email(message = "Please provide a valid email address")
    private String businessEmailId;

    private String designation;

    private String companyName;

    private String companyState;

    private String companyCity;

    private String companyRegistrationNumber;

    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date companyRegistrationDate;

    private String companyCINNumber;

    private String companyRemarks;

    private String companyPinCode;

    private String companyAddress;

    private long companyTurnover;

    private String locatedAt;

    private String businessActivityName;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date updatedAt;

    private boolean isEnable;

    private int permanentEmployee;

    private int contractEmployee;

    private String gstNumber;

    private String operationUnitAddress;


}
