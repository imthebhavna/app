package com.studentsmartcard.app.configs;

import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.studentsmartcard.app.models.enums.Role;
import com.studentsmartcard.app.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigs extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
	@Bean
	public AuthenticationEntryPoint unauthenticationEntryPoint() {
		return (request, response, authException) -> 
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized request");
	}
	
	// providing access for authentication manager bean ,
	// so that it can be used in authenticating users  
	// via login API
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean()
			throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http)
			throws Exception {
		http
			.httpBasic().disable()
			.cors().and()
			.csrf().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/register", "/login", "h2/*")
			.permitAll()
			.antMatchers("/pay").hasAnyAuthority(Role.ROLE_CANTEEN.name(), Role.ROLE_LIBRARY.name(), Role.ROLE_STATIONARY.name())
			.antMatchers("/api/students/{cardId}", "/transactions/{cardId}").hasAnyAuthority(Role.ROLE_STUDENT.name(), Role.ROLE_FACULTY.name(), Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.GET, "/api/students/", "/api/students/{cardIdOremail}").hasAnyAuthority(Role.ROLE_FACULTY.name(), Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.GET, "/api/students/configurations").hasAnyAuthority(Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.POST, "/api/students/*", "/recharge").hasAnyAuthority(Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.PUT, "/api/students/*").hasAnyAuthority(Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.DELETE, "/api/students/*").hasAnyAuthority(Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.GET, "/api/faculties/{param}").hasAnyAuthority(Role.ROLE_FACULTY.name(), Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.GET, "/api/faculties/").hasAnyAuthority(Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.GET, "/api/attendances/configurations").hasAnyAuthority(Role.ROLE_FACULTY.name(),Role.ROLE_ADMIN.name())
			.antMatchers(HttpMethod.POST, "/api/attendances/*").hasAnyAuthority(Role.ROLE_FACULTY.name(),Role.ROLE_ADMIN.name())
			.anyRequest().authenticated()
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(unauthenticationEntryPoint())
			;
		
			http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
			
			http.headers().frameOptions().disable();
			
	}

	// setting cors to allow frontent to be able to call backend
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
}
