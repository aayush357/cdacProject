package com.enitity.dto;
import java.util.Date;

import com.entity.model.classes.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AppUserDTO{
	private String email;
	private String aadhar;
	private String lastname;
	private String firstname;
	private long mobile;
	private String gender;
	private Date DOB;
	private String password;
	private String address;
	private Role role;	
	public AppUserDTO() {
		super();
	}

}