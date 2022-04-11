package com.entity.dto;

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
public class ConfirmationDTO {
	@NotEmpty
	private String packageName;
	@NotNull
	private Double balance;
//	private String adminEmail;
}
