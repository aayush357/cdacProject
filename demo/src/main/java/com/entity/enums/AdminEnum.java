package com.entity.enums;

public enum AdminEnum {
	ADMINNOTFOUND("Admin with This email not Found"),
	ADMINALREADYPRESENT("Admin with This email is Already Present in Database"),
	ROOMNOTFOUND("No Rooms Present"),
	ROOMSNOTPRESENT("No Rooms with this HotelName Are registered by The Admin"),
	PACKAGESNOTPRESENT("No Packages Are registered by The Admin"),
	FOODSNOTPRESENT("No Foods Are registered by The Admin"),
	PACKAGENOTFOUND("No Package Present with This Name"),
	FOODNOTFOUND("No Food Present with This Name"),
	PACKAGEALREADYPRESENT("Package with This Name is Already Present"),
	ROOMALREADYPRESENT("Room with This Hotel Name is Already Present"),
	PACKAGESNOTSELECTED("No User Have Selected Your Packages"),
	PACKAGESTILLACTIVE("The Package Is Still Active with User"),
	FOODSTILLACTIVE("The Food Is Still Active with User"),
	ROOMSTILLACTIVE("The Room Is Still Active with User"),
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
