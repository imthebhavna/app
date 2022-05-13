package com.studentsmartcard.app.utils;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.studentsmartcard.app.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenUtils implements Serializable {
	
	private static final long serialVersionUID = -937834143870929741L;

	@Value("${jwt.secret:secret}")
	private String secret;
	
	@Value("${jwt.expire-length}")
	private long JWT_TOKEN_VALIDITY = 1*60*60*1000; // 1h
	
	@Autowired
	private UserService userService;
	
	@PostConstruct
	protected void init() {
		// convert secret to base64 format
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
	}
	
	public String generateToken(UserDetails userDetails) {
		Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
		List<String> roles = userDetails.getAuthorities()
				.stream()
				.map(x -> x.getAuthority())
				.collect(Collectors.toList());

		log.info("generating jwt token for username: {} having roles: {}", userDetails.getUsername(), roles);

		claims.put("roles", roles);
		Date validity = new Date(new Date().getTime() + JWT_TOKEN_VALIDITY);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date())
				.setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	public Authentication getAuthentication(String token) {
		log.warn("authenticating token ...");
		UserDetails userDetails = userService.loadUserByUsername(getUsername(token));
		// principal -> aka current user, credentials, authorities
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUsername(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token).getBody().getSubject();
	}
	
	public String getToken(HttpServletRequest request) {
		// getting JSON web token from HTTP request header
		String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(!ObjectUtils.isEmpty(token) && token.startsWith("Bearer ")) {
			return token.substring(7, token.length());
		}
		return null;
	}
	
	public boolean validateToken(String token) {
		log.warn("validating token ...");
		// empty token 
		if(ObjectUtils.isEmpty(token)) {
			return false;
		}
		
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret)
					.parseClaimsJws(token);
			
			if(claims.getBody().getExpiration().before(new Date())) {
				return false;
			}
			return true;
		} catch (JwtException ex) {
			throw new RuntimeException("Expired or invalid jwt token!");
		}
	}
}
