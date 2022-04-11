package com.entity.dto;

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
public class UserFoodDTO {
	@NotEmpty
	private String name;
	@NotEmpty
	private String type;
	@NotNull
	private int quantity;
	@NotEmpty
	private String adminEmail;
}
