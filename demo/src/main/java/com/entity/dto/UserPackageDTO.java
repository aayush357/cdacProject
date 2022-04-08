package com.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPackageDTO {
	private String name;
	private String place;
	private int numberOfPersons;
	private boolean active;
	private String adminEmail;
}
