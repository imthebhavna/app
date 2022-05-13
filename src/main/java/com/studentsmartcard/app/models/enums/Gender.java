package com.studentsmartcard.app.models.enums;

public enum Gender {
	
	MALE("Male"),
	FEMALE("Female"),
	OTHER("Other");
	
	private String value;
	
	private Gender(String value) {
		this.value = value;
	}
	
	public String getValue() { return this.value; }
}
