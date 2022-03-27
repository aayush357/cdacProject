package com.enitity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoomDTO {
	private String hotelName;
	private String type;
	private String size;
	private String adminEmail;
}