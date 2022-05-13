package com.studentsmartcard.app.models.enums;

public enum Branch {
	CSE("Computer Science & Engineering")
	;
	
	private String value;
	private Branch(String value) {
		this.value = value;
	}

	public String getValue() { return this.value; }
}
