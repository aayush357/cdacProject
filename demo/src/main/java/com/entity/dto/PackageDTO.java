package com.entity.dto;

import javax.validation.constraints.Min;
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
	@Min(value = 1, message="Value Cannot be less than 1")
	private double price;	
	@Min(value=1 , message="Value Cannot be less than 1")
	private int days;
}
