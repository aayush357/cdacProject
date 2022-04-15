package com.entity.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.entity.model.classes.ConfirmationPackage;

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
	private String firstName;
	private String email;
	private Date date;
	private double foodPrice;
	private int quantity;
	public UserConfirmationResponseDTO(ConfirmationPackage cfg){
		this.packageName = cfg.getPackageName();
		this.place = cfg.getPlace();
		this.foodCost = cfg.getFoodCost();
		this.totalCost = cfg.getTotal();
		this.roomCost = cfg.getRoomCost();
		this.packageCost = cfg.getPackageCost();
	}
	public UserConfirmationResponseDTO(@NotEmpty String packageName, @NotEmpty String place,
			@NotNull double packageCost, @NotNull double roomCost, @NotNull double foodCost, @NotNull double totalCost,
			@NotEmpty String foodName, @NotEmpty String hotelName) {
		super();
		this.packageName = packageName;
		this.place = place;
		this.packageCost = packageCost;
		this.roomCost = roomCost;
		this.foodCost = foodCost;
		this.totalCost = totalCost;
		this.foodName = foodName;
		this.hotelName = hotelName;
	}
}
