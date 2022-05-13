package com.studentsmartcard.app.models.enums;

public enum Course {

	BTECH("Bachelor of Technology");
	
	private String value;
	
	private Course(String value) { 
		this.value = value;
	}
	
	public String getValue() { return this.value; }
}
