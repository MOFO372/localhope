package com.libertymutual.goforcode.localhope.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@Service
public class LocalHopeUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	public LocalHopeUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserD user = userRepository.findByUsername(username);
		UserD thisUser = userRepository.findOne(user.getId());
		return thisUser;
	}
}
