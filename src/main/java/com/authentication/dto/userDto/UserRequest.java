package com.authentication.dto.userDto;


import com.authentication.model.Roles;
import com.authentication.model.companyModel.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequest {

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    @Size(min = 10,max = 13,message = "Mobile length should be 10 to 13 digits..")

    private String mobile;

    private String otp;

    @Size(min = 6,message = "Password length should be minimum 6.")

    private String password;

    private String designation;

    private String resourceType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Comment(value = "1 : Active, 0 : Inactive")
    private boolean isEnable;

    private Set<Roles> roles;

    private boolean isAssociated;

    private Long companyId;

    private boolean isSubscribed;


    @Override
    public String toString() {
        return "UserRequest{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", otp='" + otp + '\'' +
                ", password='" + password + '\'' +
                ", designation='" + designation + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", isEnable=" + isEnable +
                ", roles=" + roles +
                ", isAssociated=" + isAssociated +
                ", companyId=" + companyId +
                '}';
    }
}
