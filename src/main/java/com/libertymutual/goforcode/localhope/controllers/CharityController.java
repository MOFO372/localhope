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

	// this should save a need belonging to the logged-in charity
//	@PostMapping("charity/{userid}")
//	public Need saveNeed(@RequestBody Need need) {
//		return needRepository.save(need);
//	}
	
	@PostMapping("charity/{userid}") // this is the ID of the charity
	public List<Need> getCharityNeeds(@PathVariable long userid, @RequestBody Need need) {
		
		UserD user = userRepository.findOne(userid);
		need = needRepository.save(need);
		
//		    System.out.println("Userid = "       + userid);	
//			System.out.println("Need getId() = " + need.getId());
		
//		need = needRepository.findOne(need.getId());
		
//			System.out.println("Orig Amount = " + need.getOriginalAmount());
//			System.out.println("User EIN = "    + user.getEin());
			
		user.addNeed(need);
		
		userRepository.save(user);
		return user.getNeeds();
	}
	
	
	
	
	
	// this associates a need to the charity and displays all needs associated with the charity
//	@PostMapping("charity/{userid}") // this is the ID of the charity
//	public List<Need> getCharityNeeds(@PathVariable long userid, @RequestBody Need need) {
//		UserD user = userRepository.findOne(userid);
//		need = needRepository.findOne(need.getId());
//		user.addNeed(need);
//		userRepository.save(user);
//		return user.getNeeds();
//	}

	 @GetMapping("charity/{userid}")
	 public List<Need> addCharityNeed(@PathVariable long userid) {
	 UserD user = userRepository.findOne(userid);
	 return user.getNeeds();
	 }

}
