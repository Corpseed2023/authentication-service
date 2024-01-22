package com.authentication.dto.setComplianceMapDto;

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
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SetComplianceMapResponse {

	private Long companyId;

	private String companyName;

	private Long businessUnitId;

	private String businessUnitCity;

	private String stateJurisdiction;

	private String activity;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updatedAt;

	private boolean isEnable;

	private Long complianceCount;

	private String teamName;

}
