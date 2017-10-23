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
public class CharityController {

	private UserRepository userRepository;
	private NeedRepository needRepository;
	private CharityRepository charityRepository; 

	private CharityController(UserRepository userRepository, CharityRepository charityRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.charityRepository = charityRepository;
		this.needRepository = needRepository;

	}

	// Gets needs for a charity
	@GetMapping("charity/{userid}")
	public List<Need> addCharityNeed(@PathVariable long userid) {
		Charity charity = charityRepository.findOne(userid);
		List<Need> needs = charity.getNeeds();
		int sizeFollowers = charity.getFollowers().length();
		if (sizeFollowers == 0) {
			for (int i = 0; i < needs.size(); i++) {
				needs.get(i).setHasFollowers(false);
			}
		} else {
			for (int i = 0; i < needs.size(); i++) {
				needs.get(i).setHasFollowers(true);
			}
		}

		return charity.getNeeds();
	}

	// Provide a list of all users who have followed the charity
	@GetMapping("followers/{charityid}")
	public List<UserD> listFollowers(@PathVariable long charityid) {
		System.out.println("tried to list followers");
		Charity charity = charityRepository.findOne(charityid);
		System.out.println(charity.getUsername());
		List<UserD> followers = charity.listFollowers(userRepository);
		return followers;
	}

	// Associate a newly-created Need with a Charity & display all Needs for this
	@PostMapping("charity/{userid}") // this is the ID of the charity
	public List<Need> getCharityNeeds(@PathVariable long userid, @RequestBody Need need) {
		Charity user = charityRepository.findOne(userid);
		
		int sizeFollowers = user.getFollowers().length();
		if (sizeFollowers == 0) {
			need.setHasFollowers(false);
			}
		else {
			need.setHasFollowers(true);
			}
		
		need = needRepository.save(need);
		user.addNeed(need);
		userRepository.save(user);
		return user.getNeeds();
	}
}
