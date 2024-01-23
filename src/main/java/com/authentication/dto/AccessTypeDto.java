package com.authentication.dto;


import lombok.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class AccessTypeDto {

    @NotBlank(message = "AccessType name is required")
    private String accessTypeName;

    private Date createdAt;

    private Date updatedAt;

    private boolean isEnable;
}
