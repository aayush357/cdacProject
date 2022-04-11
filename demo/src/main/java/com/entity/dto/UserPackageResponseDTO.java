package com.entity.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPackageResponseDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String place;
	@NotNull
	private int numberOfPersons;
	@NotNull
	private Date startDate;
	@NotNull
	private boolean active;
	@NotNull
	private double cost;
	@NotEmpty
	private String adminEmail;
}
