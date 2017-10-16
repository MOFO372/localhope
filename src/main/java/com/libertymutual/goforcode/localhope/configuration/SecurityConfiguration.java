package com.libertymutual.goforcode.localhope.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.localhope.services.LocalHopeUserDetailsService;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public LocalHopeUserDetailsService userDetailsService;
	
	public SecurityConfiguration(LocalHopeUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/charity/**").hasRole("CHARITY")
				.antMatchers("/deleteneed/**").hasRole("CHARITY")
				.antMatchers("/updateneed/**").hasRole("CHARITY")
				.antMatchers("/message/**").hasRole("CHARITY")
				.antMatchers("/user/need/**").hasRole("USER")
				.antMatchers("/user/followcharity/**").hasRole("USER")
				.antMatchers("/user/unfollowcharity/**").hasRole("USER")
				.anyRequest().authenticated()
//			.and()
//			.formLogin()
				// this forces the /login path to go into UserDetailsService and try to find the user that you entered
			.and()
			.csrf()
			.disable();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public UserDetailsService userDetailsService() {
		return userDetailsService;
	}
}
