package com.entity.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.entity.model.keys.PackageID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "package")
@ToString
@IdClass(PackageID.class)
public class Package {
	public Package(String packageName, String place, double price, int days, Admin adminName) {
		super();
		this.packageName = packageName;
		this.place = place;
		Price = price;
		this.days = days;
		this.adminName = adminName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	@Id
	@Column(name = "name")
	private String packageName;
	@Column(name = "place")
	private String place;
	@Column(name = "price")
	private double Price;
	@Column(name = "numberOfDays")
	private int days;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Admin_name")
	private Admin adminName;
}
