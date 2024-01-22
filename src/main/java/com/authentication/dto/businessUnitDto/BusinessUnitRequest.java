package com.authentication.dto.businessUnitDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BusinessUnitRequest {

//	private Long id;
	
	private String businessActivity;
	
	private String city;
	
	private String locatedAt;
	
	private int permanentEmployee;
	
	private int contractEmployee;
	
	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date updatedAt;

	private boolean isEnable;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_registration")
	private Date dateRegistration;

	private String gstNumber;

	private String states;

//	private List<Long> teamIds;



}
