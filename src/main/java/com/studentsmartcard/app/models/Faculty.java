package com.studentsmartcard.app.models;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.studentsmartcard.app.models.enums.Gender;
import com.studentsmartcard.app.models.enums.Role;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name="Faculty")
@Table(name="faculty")
@ToString
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Faculty extends BaseEntity {

	private Faculty(Builder builder) {
		super(UUID.randomUUID().toString(), builder.firstName, builder.middleName, builder.lastName, 
				builder.role, builder.gender, builder.email, builder.mobile);
	}
	
	public static class Builder {
		private String firstName;
		private String middleName;
		private String lastName;
		
		@Enumerated(EnumType.STRING)
		private Role role;
		
		@Enumerated(EnumType.STRING)
		private Gender gender;
		
		private String email;
		private String mobile;
		
		public Builder(String email, Gender gender) {
			this.email = email;
			this.gender = gender;
			this.role =  Role.ROLE_FACULTY;
		}
		
		public Builder setMobile(String mobile) {
			this.mobile = mobile;
			return this;
		}
		
		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder setMiddleName(String middleName) {
			this.middleName = middleName;
			return this;
		}
		
		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Faculty build() {
			return new Faculty(this);
		}
	}
}
