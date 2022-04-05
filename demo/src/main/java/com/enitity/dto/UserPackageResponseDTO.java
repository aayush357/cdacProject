package com.enitity.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPackageResponseDTO {
	private String name;
	private String place;
	private int numberOfPersons;
	private Date startDate;
	private boolean active;
	private double cost;
	private String adminEmail;
}
