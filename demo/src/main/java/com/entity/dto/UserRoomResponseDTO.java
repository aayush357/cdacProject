package com.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoomResponseDTO {
	private String hotelName;
	private String type;
	private String size;
	private String adminEmail;
	private boolean active;
}
