package com.authentication.model.teamMemberModel;


import com.authentication.model.companyModel.Company;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "team_member")
public class TeamMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NonNull
	@NotBlank
	private String memberName;

	private String accessTypeName;

	@NonNull
	@NotBlank
	private String memberMail;

	@NonNull
	@NotBlank
	private String memberMobile;

	private String typeOfResource;

	@Column(name = "created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Column(name = "updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Column(length = 1,name="is_enable",columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	private Long reportingManagerId;

	private Long subscriptionId;

	private Long createdBy;

	//super admin is who created company so that user id will save in teamMember table as superAdmin
	private Long superAdminId;

	//User ID from User Table
	private Long userId;


	public TeamMember() {

	}

}
