package com.enitity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String email;
	private String aadhar;
	private String lastname;
	private String firstname;
	private long mobile;
	private String gender;
	private String password;
	private String address;
}
