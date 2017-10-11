package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	
	// add: PasswordEncoder encoder as parameter
	public HomeController(NeedRepository needRepository, UserRepository userRepository, PasswordEncoder encoder) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
		this.encoder = encoder;
	}


	@GetMapping("")
	public List<UserD> getAll(){
		return userRepository.findByIsCharity("Charity");
		//return userRepository.findAll(); 
	}	

//	@GetMapping("charity")
//	public String getCharities(Model model){
//		model.addAttribute("message", "List all Charities, sorted by type.");
//		model.addAttribute("users", userRepository.findByIsCharity(true, new Sort(new Order("charityType"))));
//		return "list";
//	}
//	
//	@GetMapping("charity/{charityType}")
//	public String getCharitiesByType(Model model, @PathVariable String charityType){
//		model.addAttribute("message", "List Charities of type: " + charityType);
//		model.addAttribute("users", userRepository.findByCharityTypeEquals(charityType));
//		return "list";
//	} 
	
	
	
	
//	@GetMapping("needs")
//	public String getAllNeeds(Model model){
//		model.addAttribute("message", "List of charity needs");
//		model.addAttribute("needs", needRepository.findAll(new Sort("type")));
//		return "list";
//	}
	
}
