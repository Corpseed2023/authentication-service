package com.authentication.payload.request;

import com.authentication.model.Roles;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class  UserRequest {

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

}