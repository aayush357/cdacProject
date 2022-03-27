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

import com.entity.model.keys.UserFoodID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="selectFood")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UserFoodID.class)
public class UserFood {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	public UserFood(String name, String type, int quantity, AppUser user) {
		super();
		this.name = name;
		this.type = type;
		this.quantity = quantity;
		this.user = user;
	}
	@Id
	@Column(name = "name")
	private String name;
	@Column(name="type")
	private String type;
	@Column(name="quantity")
	private int quantity;
	@JsonIgnore
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "User_aadharcard", referencedColumnName = "aadharcard"),
		@JoinColumn(name = "User_email", referencedColumnName = "email") })
	private AppUser user;
	@JsonIgnore
	@OneToOne(mappedBy = "userFood")
//	@JoinColumn(name="selectPackageName")
	private UserPackage userPackage;
}
