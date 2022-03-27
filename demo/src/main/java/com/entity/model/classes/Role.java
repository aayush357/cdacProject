//it totally works

package com.entity.model.classes;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(name="role")
@Data
public class Role {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="Name")
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Collection<AppUser> appUsers = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Collection<Admin> admins = new ArrayList<>();

	public Role() {
		super();
	}

	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
