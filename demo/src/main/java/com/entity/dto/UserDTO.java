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
public class UserDTO {
	@NotEmpty
	private String email;
	@NotEmpty
	private String aadhar;
	@NotEmpty
	private String lastname;
	@NotEmpty
	private String firstname;
	@NotNull
	private long mobile;
	@NotEmpty
	private String gender;
	@NotEmpty
	private String password;
	@NotEmpty
	private String address;
}
