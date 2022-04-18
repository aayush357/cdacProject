package com.entity.model.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.entity.model.keys.AppUserID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "all_users")
@Data
@AllArgsConstructor
@IdClass(AppUserID.class)
public class AppUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "email", length = 64)
	private String email;
	@Id
	@Column(name="aadharcard", length = 64)
	private String aadhar;
	@Column(name = "lastname")
	private String lastname;
	@Column(name = "firstname")
	private String firstname;
	@Column(name = "phonenumber")
	private long mobile;
	@Column(name = "gender")
	private String gender;
	@Column(name = "password")
	private String password;
	@Column(name = "address")
	private String address;
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Role_ID")
	private Role role;	
	@JsonIgnore
	@OneToMany(mappedBy="user")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<UserPackage> userPackage = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy="user")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<UserFood> userFood = new ArrayList<>();
	@JsonIgnore
	@OneToMany(mappedBy="user")
//	@LazyCollection(LazyCollectionOption.FALSE)
	private Collection<UserRoom> userRoom = new ArrayList<>();
	public AppUser() {
		super();
	}

}
