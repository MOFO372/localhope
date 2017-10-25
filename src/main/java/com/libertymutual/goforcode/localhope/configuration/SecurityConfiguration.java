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
				.antMatchers("/charity/**").hasRole("Charity")
				.antMatchers("/updateneed/**").hasRole("Charity")
				.antMatchers("/message/**").hasRole("Charity")
				.antMatchers("/user/need/**").hasRole("User")
				.antMatchers("/user/followcharity/**").hasRole("User")
				.antMatchers("/user/unfollowcharity/**").hasRole("User")
				.anyRequest().authenticated()
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
