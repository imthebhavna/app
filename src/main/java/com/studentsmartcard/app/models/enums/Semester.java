package com.studentsmartcard.app.models.enums;

public enum Semester {
	FIRST("First"),
	Second("Second"),
	THIRD("Third"),
	FOURTH("Fourth"),
	FIFTH("Fifth"),
	SIXTH("Sixth"),
	SEVENTH("Seventh"),
	EIGHTH("Eighth")
	;
	
	private String value;
	private Semester(String value) {
		this.value = value;
	}

	public String getValue() { return this.value; }
}
