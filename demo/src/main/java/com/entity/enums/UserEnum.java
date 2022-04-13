package com.entity.enums;

public enum UserEnum {
	USERNOTFOUND("User with This email not Found"),
	PACKAGENOTSELECTED("User Have not Selected any Package"),
	ACTIVEPACKAGENOTFOUND("No Active Package Found for User"),
	ACTIVEPACKAGEALREADYPRESENT("Active Package Already Present"),
	ROOMNOTSELECTED("User Have not Selected any Room"),
	CONFIRMATIONNOTFOUND("Confirmation cannot be Verified"),
	FOODNOTSELECTED("User Have not Selected any Food");
	String text;
	UserEnum(String text){
		this.text=text;
	}
	
	@Override
	public String toString() {
		return this.text;
	}
}
