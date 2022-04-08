package com.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFoodResponseDTO {
	private String name;
	private String type;
	private int quantity;
	private String adminEmail;
	private boolean active;
}
