package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.ThisIsNotACharityException;
import com.libertymutual.goforcode.localhope.models.UnableToDeFollowThisCharityException;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;


import io.swagger.annotations.ApiOperation;



@RestController
@RequestMapping("user")
public class UserApiController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	// private PasswordEncoder encoder;
	
	
	// add: PasswordEncoder encoder as parameter
	public UserApiController(NeedRepository needRepository, UserRepository userRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
	}

	
	@PostMapping("")
	public UserD createUser(@RequestBody UserD user) {
		return userRepository.save(user);	
	}
	
	
	@PostMapping("{userid}/need")
	public UserD associateDogooderAndNeed(@PathVariable long userid, @RequestBody Need need){
		UserD user = userRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
		needRepository.save(need);
		return user;
	}

	
	@PostMapping("{dogooderid}/followcharity")
	public UserD associateDogooderAndCharity(@PathVariable long dogooderid, @RequestBody UserD charity) throws ThisIsNotACharityException{
		UserD user = userRepository.findOne(dogooderid);			
		charity = userRepository.findOne(charity.getId());			
		user.addFollowedCharity(charity);
		userRepository.save(user);
		return user;
	}

	@PostMapping("{dogooderid}/followcharities")
	public UserD associateDogooderAndCharities(@PathVariable long dogooderid, @RequestBody UserD[] charities) throws ThisIsNotACharityException{
		UserD user = userRepository.findOne(dogooderid);
		for(UserD charity : charities) {			
			charity = userRepository.findOne(charity.getId());			
			user.addFollowedCharity(charity);
			userRepository.save(user);
		}		
		return user;
	}
	
	
	
	@PostMapping("{dogooderid}/defollowcharity")
	public UserD removeDogooderAndCharity(@PathVariable long dogooderid, @RequestBody UserD charity) 
			     throws ThisIsNotACharityException, UnableToDeFollowThisCharityException{
		UserD user = userRepository.findOne(dogooderid);			
		charity = userRepository.findOne(charity.getId());			
		user.removeFollowedCharity(charity);
		userRepository.save(user);
		return user;
	}

	@PostMapping("{dogooderid}/defollowcharities")
	public UserD removeDogooderAndCharities(@PathVariable long dogooderid, @RequestBody UserD[] charities) 
			     throws ThisIsNotACharityException, UnableToDeFollowThisCharityException{
		UserD user = userRepository.findOne(dogooderid);	
		for(UserD charity : charities) {
			charity = userRepository.findOne(charity.getId());			
			user.removeFollowedCharity(charity);
			userRepository.save(user);
		}
		return user;
	}
	
	@PostMapping("")
	public UserD createUser(@RequestBody UserD user) {
		return userRepository.save(user);	
	}	
}
