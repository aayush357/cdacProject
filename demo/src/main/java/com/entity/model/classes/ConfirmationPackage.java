package com.entity.model.classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.entity.model.keys.ConfirmationPackageID;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Confirmation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ConfirmationPackageID.class)
public class ConfirmationPackage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	@Id
	@Column(name="packageName")
	private String packageName;
	public ConfirmationPackage(String packageName, String place, Double packageCost, Double roomCost, Double foodCost,
			Double total, Double balance) {
		super();
		this.packageName = packageName;
		this.place = place;
		this.packageCost = packageCost;
		this.roomCost = roomCost;
		this.foodCost = foodCost;
		this.total = total;
		this.balance = balance;
	}
	@Column(name="place")
	private String place;
	@Column(name="packageCost")
	private Double packageCost;
	@Column(name="roomCost")
	private Double roomCost;
	@Column(name="foodCost")
	private Double foodCost;
	@Column(name="total")
	private Double total;
	@Column(name="balance")
	private Double balance;
//	@JoinColumns({
//		@JoinColumn(name="selectPackageName", referencedColumnName="packageName"),
//		@JoinColumn(name="selectPackageUser", referencedColumnName = "user"),
//		@JoinColumn(name="selectPackageAdmin", referencedColumnName = "admin_name")
//	})
	@JsonIgnore
	@OneToOne(mappedBy = "confirmationPackage")
	private UserPackage selectPackage;
}
