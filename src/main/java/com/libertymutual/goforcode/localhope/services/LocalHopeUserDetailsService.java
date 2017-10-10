package com.libertymutual.goforcode.localhope.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@Service
public class LocalHopeUserDetailsService implements UserDetailsService {

	private UserRepository userRepo;

	public LocalHopeUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepo.findByUsername(username);
	}


}
