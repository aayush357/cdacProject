package com.entity.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDTO {
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
	@NotNull
	private int mobNo;
	@NotEmpty
	private String role;
}
