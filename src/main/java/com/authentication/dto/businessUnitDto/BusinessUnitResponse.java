package com.authentication.dto.businessUnitDto;

import com.authentication.model.companyModel.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import lombok.*;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusinessUnitResponse {

	private Long id;

	private Long companyId;

	private String businessActivity;

	private String city;

	private String locatedAt;

	private int permanentEmployee;

	private int contractEmployee;

	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_registration")
	private Date dateRegistration;

	private boolean isEnable;

//	private List<Long> teamIds;

//	private List<TeamResponse> teams;

	private String gstNumber;

	private String states;



}
