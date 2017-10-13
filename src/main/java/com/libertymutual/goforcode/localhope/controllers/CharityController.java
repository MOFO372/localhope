package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class CharityController {

	private UserRepository userRepository;
	private NeedRepository needRepository;

	private CharityController(UserRepository userRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;

	}


	// Associate a newly-created Need with a Charity & display all Needs for this Charity 
	@PostMapping("charity/{userid}") // this is the ID of the charity
	public List<Need> getCharityNeeds(@PathVariable long userid, @RequestBody Need need) {
		
		UserD user = userRepository.findOne(userid);
		need = needRepository.save(need);
				
		
		user.addNeed(need);		
		userRepository.save(user);
		return user.getNeeds();
	}
	
	
	

	 @GetMapping("charity/{userid}")
	 public List<Need> addCharityNeed(@PathVariable long userid) {
	 UserD user = userRepository.findOne(userid);
	 return user.getNeeds();
	 }

}
