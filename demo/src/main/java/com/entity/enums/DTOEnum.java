package com.entity.enums;

public enum DTOEnum {
	ROOMDTONULL("Room Data Not Present"),
	FOODDTONULL("Food Data Not Present"),
	PACKAGEDTONULL("Package Data Not Present");
	String text;
	
	private DTOEnum(String text) {
		this.text= text;
	}
	@Override
	public String toString() {
		return this.text;
	}
}
