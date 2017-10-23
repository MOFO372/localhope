package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.Need;

import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class HomeController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	private CharityRepository charityRepository; 

	public HomeController(NeedRepository needRepository, UserRepository userRepository, CharityRepository charityRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
		this.charityRepository = charityRepository;
	}

	
	// show all charities
	@GetMapping("")
	public List<UserD> getAll() {
		System.out.println("in mappint");
		return userRepository.findAll();
	}

	
	@PostMapping("need/{userid}")
	public UserD associateDogooderAndNeed(@PathVariable long userid, @RequestBody Need need) {
		Charity user = charityRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
		userRepository.save(user);
		return user;
	}

	
	@GetMapping("charity")
	public List<Charity> getCharities() {
		return charityRepository.findAll(); 
	}
}
