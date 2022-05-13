package com.studentsmartcard.app.models.enums;

public enum Role {

	ROLE_ADMIN("Admin"), 
	ROLE_FACULTY("Faculty"),
	ROLE_CANTEEN("Canteen"),
	ROLE_LIBRARY("Library"),
	ROLE_STATIONARY("Stationary"),
	ROLE_STUDENT("Student");
	
	private String value;
	
	private Role(String value) {
		this.value = value;
	}
	
	public String getValue() { return this.value; }
}
