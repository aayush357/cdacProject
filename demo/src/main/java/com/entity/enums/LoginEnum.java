package com.entity.enums;

public enum LoginEnum {
	ROLENOTFOUND("No Role Present with This Name"),
	TOKENNOTFOUND("Token is missing"),
	LOGGEDOUT("User has successfully logged out from the system!");
	String text;
	LoginEnum(String text){
		this.text = text;
	}
	@Override
	public String toString() {
		return this.text;
	}
}
