package com.libertymutual.goforcode.localhope.controllers;

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

//	@GetMapping("")
//	public List<UserD> getAll(){
//		return userRepository.findAll();	
//	}
		
//	@GetMapping("all")
//	public String getAll(Model model){
//		model.addAttribute("message", "List all users: Charities & DoGooders - useless ...");
//		model.addAttribute("users", userRepository.findAll(new Sort("charityType")));
//		return "list";
//	}	
//
//	@GetMapping("charity")
//	public String getCharities(Model model){
//		model.addAttribute("message", "List all Charities, sorted by type.");
//		model.addAttribute("users", userRepository.findByRole("Charity", new Sort(new Order("charityType"))));
//		return "list";
//	}
//	
//	@GetMapping("charity/{charityType}")
//	public String getCharitiesByType(Model model, @PathVariable String charityType){
//		model.addAttribute("message", "List Charities of type: " + charityType);
//		model.addAttribute("users", userRepository.findByCharityTypeEquals(charityType));
//		return "list";
//	}
	
	
	@PostMapping("{userid}/need")
	public UserD associateDogooderAndNeed(@PathVariable long userid, @RequestBody Need need){
		UserD user = userRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
//		userRepository.save(user);
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

	@PostMapping("{dogooderid}/defollowcharity")
	public UserD removeDogooderAndCharity(@PathVariable long dogooderid, @RequestBody UserD charity) 
			     throws ThisIsNotACharityException, UnableToDeFollowThisCharityException{
		UserD user = userRepository.findOne(dogooderid);			
		charity = userRepository.findOne(charity.getId());			
		user.removeFollowedCharity(charity);
		userRepository.save(user);
		return user;
	}
	
	
	@PostMapping("")
	public UserD createUser(@RequestBody UserD user) {
		return userRepository.save(user);	
	}	
}
