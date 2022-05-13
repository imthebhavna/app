package com.studentsmartcard.app.models.enums;

public enum Subject {

	DESIGN_AND_ANALYSIS_OF_ALGORITHM("Design and Analysis of Algorithms"),
	DATABASE_MANAGEMENT_SYSTEM("Database Management System"),
	COMPUTER_NETWORKS("Computer Networks"),
	COMPILER_DESIGN("Compiler Design"),
	COMPUTER_ARCHITECTURE("Computer Architecture"),
	WEB_TECHNOLOGY("Web Technology"),
	HUMAN_VALUES("Human Values"),
	MACHINE_LEARNING("Machine Learning"),
	THEORY_OF_AUTOMATA("Theory of Automata"),
	SOFTWARE_ENGINEERING("Software Engineering")
	;
	
	private String value;
	private Subject(String value) {
		this.value = value;
	}

	public String getValue() { return this.value; }
}
