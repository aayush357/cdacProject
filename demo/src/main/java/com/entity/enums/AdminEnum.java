package com.entity.enums;

public enum AdminEnum {
	ADMINNOTFOUND("Admin with This email not Found"),
	ROOMNOTFOUND("No Rooms Present"),
	ROOMSNOTPRESENT("No Rooms with this HotelName Are registered by The Admin"),
	PACKAGESNOTPRESENT("No Packages Are registered by The Admin"),
	FOODSNOTPRESENT("No Foods Are registered by The Admin"),
	PACKAGENOTFOUND("No Package Present with This Name"),
	FOODNOTFOUND("No Food Present with This Name"),
	PACKAGEALREADYPRESENT("Package with This Name is Already Present"),
	ROOMALREADYPRESENT("Room with This Hotel Name is Already Present"),
	FOODALREADYPRESENT("Room with This Hotel Name is Already Present");
	
	String text;
	AdminEnum(String s){
		text=s;
	}

	@Override
	public String toString() {
		return this.text;
	}
	
}   
