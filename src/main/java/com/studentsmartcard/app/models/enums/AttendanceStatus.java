package com.studentsmartcard.app.models.enums;

public enum AttendanceStatus {
	
	PRESENT("Present"),
	ABSENT("Absent"),
	HOLIDAY("Holiday");
	
	private String value;
	
	private AttendanceStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
