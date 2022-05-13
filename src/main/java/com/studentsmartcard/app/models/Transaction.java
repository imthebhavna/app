package com.studentsmartcard.app.models;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.studentsmartcard.app.models.enums.Role;
import com.studentsmartcard.app.models.enums.TransactionType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@Data
@NoArgsConstructor
public class Transaction {

	@Id
	private String id;
	
	private String cardId;
	private String studentId;
	private Double amount;
	
	private String byEmail;
	private String fromEmail;	
	private Role byRole;
	
	private Date date;

	private Transaction(Builder builder) {
		this.id = UUID.randomUUID().toString();
		this.cardId = builder.cardId;
		this.amount = builder.amount;
		this.byRole = builder.byRole;
		this.byEmail = builder.byEmail;
		this.fromEmail = builder.fromEmail;
		this.studentId = builder.studentId;
		this.type = builder.type;
		this.date = new Date();
	}
	
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	
	public static class Builder {
		
		private String cardId;
		private String studentId;
		private Double amount;
		
		private String byEmail;
		private String fromEmail;	
		private Role byRole;
		
		private TransactionType type;
		
		public Builder(String cardId, Double amount) {
			this.cardId = cardId;
			this.amount = amount;
		}
		
		public Builder setByEmail(String byEmail) {
			this.byEmail = byEmail;
			return this;
		}
		
		public Builder setFromEmail(String fromEmail) {
			this.fromEmail = fromEmail;
			return this;
		}
		
		public Builder setByRole(Role role) {
			this.byRole = role;
			return this;
		}
		
		public Builder setStudentId(String studentId) {
			this.studentId = studentId;
			return this;
		}
		
		public Builder setType(TransactionType type) {
			this.type = type;
			return this;
		}
		
		public Transaction build() {
			Transaction transaction = new Transaction(this);
			return transaction;
		}
	}
}
