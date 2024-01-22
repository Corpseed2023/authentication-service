package com.authentication.dto;

import com.authentication.model.companyModel.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class AccessTypeDto {

    @NotBlank(message = "AccessType name is required")
    private String accessTypeName;

    private Date createdAt;

    private Date updatedAt;

    private boolean isEnable;
}
