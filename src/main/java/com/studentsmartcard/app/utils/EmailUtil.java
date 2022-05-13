package com.studentsmartcard.app.utils;

import java.util.regex.Pattern;

import org.springframework.util.ObjectUtils;

public class EmailUtil {
	
	private static final String RFC5322_EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

	public static boolean isEmail(String username) {
		if(!ObjectUtils.isEmpty(username)) {
			return Pattern.compile(RFC5322_EMAIL_REGEX)
					.matcher(username)
					.matches();
		}
		return false;
	}
}
