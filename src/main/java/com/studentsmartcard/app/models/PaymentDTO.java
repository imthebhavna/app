package com.studentsmartcard.app.models;

import com.studentsmartcard.app.models.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDTO {

	private String cardId;
	private Double amount;
	private Role updatedBy;
	
	private String byEmail;
	private String fromEmail;
	
}
