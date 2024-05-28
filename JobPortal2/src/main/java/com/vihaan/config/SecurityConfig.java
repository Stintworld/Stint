package com.vihaan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration{

	protected void configure(HttpSecurity http) {
		 http.authorizeRequests()
		 .requestMatchers( new AntPathRequestMatcher("applicants/signup")).permitAll().anyRequest().authenticated().and().oauth2Login();
         
	}
}
