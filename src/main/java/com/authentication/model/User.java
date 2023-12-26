package com.authentication.model;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@ToString
@Table(name = "user")
public class  User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "uuid", unique = true, nullable = false)
	private String uuid;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty(message = "Email must not be empty")
	@NotBlank(message = "Email must not be blank")
	@NotNull(message = "Email must not be null")
	private String email;

//	@Size(min = 10, max = 13, message = "Mobile length should be 10 to 13 digits")
	private String mobile;
	
	@Size(min = 6,message = "Password length should be minimum 6.")
	@NotNull(message = "Password You Can't be null !!")
	@NotBlank(message = "Please enter user password !!")
	private String password;
	
	private String designation;

	@Column(name = "resource_type")
	private String resourceType;
	

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	private int otp;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(  name = "user_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Roles> roles;

	@Comment(value = "1 : User have company, 0 : User do not have Company ")
	private boolean isAssociated = false;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "subscription")
	private Subscription subscription;

	private Long companyId;

	private boolean emailSent;

	@Comment(value = "1 : User is Subscriber, 0 : User do not have subscription ")
	private boolean isSubscribed = false;


	public Set<Roles> getRoles() {
		return roles;
	}
}
