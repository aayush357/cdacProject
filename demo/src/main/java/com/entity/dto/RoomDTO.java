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
public class RoomDTO {
	@NotEmpty
	String hotelName;
	@NotEmpty
	String type;
	@NotEmpty
	String size;
	@NotNull
	Double price;
}
