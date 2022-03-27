package com.entity.model.classes;

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

import com.entity.model.keys.UserRoomID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="selectRoom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserRoomID.class)
public class UserRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	public UserRoom(String hotelName, String type, String size, AppUser user) {
		super();
		this.hotelName = hotelName;
		this.type = type;
		this.size = size;
		this.user = user;
	}
	@Id
	@Column(name="hotelName")
	private String hotelName;
	@Column(name="type")
	private String type;
	@Column(name="size")
	private String size;
	@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "User_aadharcard", referencedColumnName = "aadharcard"),
		@JoinColumn(name = "User_email", referencedColumnName = "email") })
	private AppUser user;
	@JsonIgnore
	@OneToOne(mappedBy="userRoom")
	private UserPackage userPackage;
}
