package com.studentsmartcard.app.configs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.studentsmartcard.app.utils.JwtTokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = jwtTokenUtils.getToken(request);
		
		if(jwtTokenUtils.validateToken(token)) {
			Authentication auth = jwtTokenUtils.getAuthentication(token);
			
			// get current security context for spring IOC inversion-of-control / D.I.  
			// container, and setting authentication manually in it , basically 
			// based on json web token validation.
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		// calling next filter in the filter's chain / list 
		// after JWT filter is applied.
		filterChain.doFilter(request, response);
	}

}
