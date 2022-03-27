package com.entity.model.classes;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "admin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
	@Id
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name="mobile")
	private int mobNo;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Role_ID")
	private Role role;
	@OneToMany(mappedBy="adminName")
//	@LazyCollection(LazyCollectionOption.FALSE) //this means Eager fetch
	private Collection<Package> packages = new ArrayList<>();
	@OneToMany(mappedBy="adminName")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Food> foods = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy="adminName")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<Room> rooms = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy="userAdminName")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<UserPackage> userPackages = new ArrayList<>();
}
