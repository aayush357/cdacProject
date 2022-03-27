package com.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PackagesResponse {
	private String packageName;
	private String place;
	private double price;	
	private int days;
	private String adminEmail;
}
