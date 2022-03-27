package com.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomsResponse {
	String hotelName;
	String type;
	String size;
	Double price;
	String adminEmail;
}
