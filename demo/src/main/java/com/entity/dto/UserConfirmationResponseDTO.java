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
public class UserConfirmationResponseDTO {
	@NotEmpty
	private String packageName;
	@NotEmpty
	private String place;
	@NotNull
	private double packageCost;
	@NotNull
	private double roomCost;
	@NotNull
	private double foodCost;
	@NotNull
	private double totalCost;
	@NotEmpty
	private String foodName;
	@NotEmpty
	private String hotelName;
}
