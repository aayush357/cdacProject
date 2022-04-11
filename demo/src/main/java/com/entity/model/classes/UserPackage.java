package com.entity.model.classes;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.entity.model.keys.UserPackageID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "selectPackage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserPackageID.class)
public class UserPackage {
	public UserPackage(String name, String place, Date date, int numberOfPersons, boolean active, AppUser user,
			Admin userAdminName, ConfirmationPackage confirmationPackage, UserFood userFood, UserRoom userRoom) {
		super();
		this.name = name;
		this.place = place;
		this.date = date;
		this.numberOfPersons = numberOfPersons;
		this.active = active;
		this.user = user;
		this.userAdminName = userAdminName;
		this.confirmationPackage = confirmationPackage;
		this.userFood = userFood;
		this.userRoom = userRoom;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	@Id
	@Column(name = "packageName")
	private String name;
	@Column(name = "place")
	private String place;
	@Column(name = "startDate")
	private Date date;
	@Column(name = "noOfPersons")
	private int numberOfPersons;
	@Column(name = "active")
	private boolean active;
	@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "User_aadharcard", referencedColumnName = "aadharcard"),
			@JoinColumn(name = "User_email", referencedColumnName = "email") })
	private AppUser user;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "Admin_name")
	private Admin userAdminName;
	@JsonIgnore
	@OneToOne(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "selectConfirmationId", referencedColumnName = "id"),
		@JoinColumn(name = "selectConfirmationName", referencedColumnName = "packageName") })
	private ConfirmationPackage confirmationPackage;
	@JsonIgnore
	@OneToOne(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "selectFoodId", referencedColumnName = "id"),
		@JoinColumn(name = "selectFoodName", referencedColumnName = "name") })
	private UserFood userFood;
	@JsonIgnore
	@OneToOne(cascade = javax.persistence.CascadeType.ALL)
	@JoinColumns({ @JoinColumn(name = "selectRoomId", referencedColumnName = "id"),
		@JoinColumn(name = "selectHotelName", referencedColumnName = "hotelName") })
	private UserRoom userRoom;
}
