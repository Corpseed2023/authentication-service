package com.authentication.model.companyModel;


import com.authentication.model.businessUnitModel.BusinessUnit;
import com.authentication.model.teamMemberModel.TeamMember;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Data
@Table(name = "company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;

	@Column(name = "user_id")
	private Long userId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "business_email_id")
	@NotNull
	private String businessEmailId;

	@Column(name = "designation")
	@NotNull
	private String designation;
	
	@Column(name = "company_type")
	private String companyType;

//	@ManyToOne
//	private CompanyType companyType;

	@Column(name = "companyName")
	private String companyName;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "registration_number")
	private String registrationNumber;

	@Column(name = "registration_date")
	@Temporal(TemporalType.DATE)
	private Date registrationDate;

	@Column(name = "cin_number")
	private String cinNumber;

	@Column(columnDefinition = "text",name="remarks")
	private String remarks;

	@Column(name = "pin_code")
	private String pinCode;

	@Column(name = "turnover")
	private long turnover;

	@Column(name = "located_at")
	private String locatedAt;

	private String businessActivityName;

	@Column(name = "permanent_employee")
	private int permanentEmployee;

	@Column(name = "contract_employee")
	private int contractEmployee;

	@Column(name="gst_no")
	private String gstNumber;

	private String operationUnitAddress;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at")
	private Date updatedAt;

	@Column(length = 1, name = "is_enable", columnDefinition = "tinyint(1) default 1")
	@Comment(value = "1 : Active, 0 : Inactive")
	private boolean isEnable = true;


	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<TeamMember> teamMembers;

	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	private List<BusinessUnit> businessUnits;

	public void disable() {
		this.isEnable = false;
	}


}
