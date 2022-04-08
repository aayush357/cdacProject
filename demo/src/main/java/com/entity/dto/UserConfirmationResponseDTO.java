package com.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserConfirmationResponseDTO {
	private String packageName;
	private String place;
	private double packageCost;
	private double roomCost;
	private double foodCost;
	private double totalCost;
	private String foodName;
	private String hotelName;
}
