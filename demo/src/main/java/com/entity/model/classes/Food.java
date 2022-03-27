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

import com.entity.model.keys.FoodID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="food")
@Getter
@Setter
@NoArgsConstructor
@IdClass(FoodID.class)
public class Food {
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	@Id
	@Column(name = "name")
	private String name;
	@Column(name="type")
	private String type;
	@Column(name="cost")
	private Double cost;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="Admin_name")
	private Admin adminName;
	public Food(String name, String type, Double cost, Admin adminName) {
		super();
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.adminName = adminName;
	}
	
	
}
