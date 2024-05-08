package com.app;

import java.time.Duration;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.app.services.UserService;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userService);
	
		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService) 
				.passwordEncoder(passwordEncoder()); 
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.cors().configurationSource(request -> {
//			CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
//			configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//			return configuration;
//		}).and().csrf().and().authorizeRequests()
//        
//				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll().antMatchers("/api/**","/doctor/getDoctor","/api/signup","/api/login").permitAll()
//				.antMatchers("/confirm/agree","/confirm/allConfirm").hasRole("DOCTOR")
//				.anyRequest().authenticated();
//		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http
	        .cors().configurationSource(request -> {
	            CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
	            configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
	            return configuration;
	        })
	        .and()
	        .csrf()
	            .requireCsrfProtectionMatcher(new RequestMatcher() {
	                private AntPathRequestMatcher requestMatcher = new AntPathRequestMatcher("/api/login", "POST");

	                @Override
	                public boolean matches(HttpServletRequest request) {
	                    return !requestMatcher.matches(request);
	                }
	            }).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
	        .and()
	        .authorizeRequests()
	            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
	            .antMatchers("/api/**", "/doctor/getDoctor", "/api/signup", "/api/login").permitAll()
	            .antMatchers("/confirm/agree", "/confirm/allConfirm").hasRole("DOCTOR")
	            .anyRequest().authenticated();
	    
	    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

}
