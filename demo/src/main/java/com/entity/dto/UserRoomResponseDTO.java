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
public class UserRoomResponseDTO {
	@NotEmpty
	private String hotelName;
	@NotEmpty
	private String type;
	@NotEmpty
	private String size;
	@NotEmpty
	private String adminEmail;
	@NotNull
	private boolean active;
}
