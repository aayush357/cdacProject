package com.entity.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPackageDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String place;
	@NotNull
	private int numberOfPersons;
	@NotNull
	private boolean active;
	@NotEmpty
	private String adminEmail;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date date;
}
