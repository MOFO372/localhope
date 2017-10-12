package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

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
public class HomeController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	private PasswordEncoder encoder;

	
	public HomeController(NeedRepository needRepository, UserRepository userRepository, PasswordEncoder encoder) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	//show all charities
	@GetMapping("")
	public List<UserD> getAll(){
		return userRepository.findAll();	
	}


	
	@PostMapping("need/{userid}")
	public UserD associateDogooderAndNeed(@PathVariable long userid, @RequestBody Need need){
		UserD user = userRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
		userRepository.save(user);
		return user;
	}

		
	@PostMapping("")
	public UserD createUser(@RequestBody UserD user) {
		return userRepository.save(user);	
	}	

	

	@GetMapping("charity")
	public List<UserD> getCharities(){
		return userRepository.findByIsCharity("Charity");
	}
//	
//	@GetMapping("charity/{charityType}")
//	public String getCharitiesByType(Model model, @PathVariable String charityType){
//		model.addAttribute("message", "List Charities of type: " + charityType);
//		model.addAttribute("users", userRepository.findByCharityTypeEquals(charityType));
//		return "list";
//	} 
	


}
