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
public class PackageDTO {
	@NotEmpty
	private String packageName;
	@NotEmpty
	private String place;
	@NotNull
	private double price;	
	@NotNull
	private int days;
}
