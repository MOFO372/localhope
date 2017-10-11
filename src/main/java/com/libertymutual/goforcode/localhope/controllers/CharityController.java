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
	
	//this should save a need belonging to the logged-in charity
	@PostMapping("charity")
	public Need saveNeed(@RequestBody Need need) {
		return needRepository.save(need);	
	}	

	
	//this associates a need to the charity and displays all needs associated with the charity
	@PostMapping("{userid}/charity")
	public List<Need> getCharityNeeds(@PathVariable long userid, @RequestBody Need need){
		UserD user = userRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
		userRepository.save(user);		
		return user.getNeeds();	
	}


//	@GetMapping("{userid}/charity")
//	public List<Need> addCharityNeed(@PathVariable long userid) {
//		UserD user = userRepository.findOne(userid);
//		return user.getNeeds();	 
//	}
	

}
