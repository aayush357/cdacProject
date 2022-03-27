package com.entity.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.entity.model.keys.RoomID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="room")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@IdClass(RoomID.class)
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	@Id
	@Column(name = "hotelName")
	private String hotelName;
	@Column(name="type")
	private String type;
	@Column(name="size")
	private String size;
	@Column(name="cost")
	private Double price;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="Admin_name")
	private Admin adminName;
	public Room(String hotelName, String type, String size, Double price, Admin adminName) {
		super();
		this.hotelName = hotelName;
		this.type = type;
		this.size = size;
		this.price = price;
		this.adminName = adminName;
	}
}
