package com.libertymutual.goforcode.localhope.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

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


	@GetMapping("all")
	public String getAll(Model model){
		model.addAttribute("message", "List all users: Charities & DoGooders - useless ...");
		model.addAttribute("users", userRepository.findAll(new Sort("charityType")));
		return "list";
	}	

	@GetMapping("charity")
	public String getCharities(Model model){
		model.addAttribute("message", "List all Charities, sorted by type.");
		model.addAttribute("users", userRepository.findByIsCharity(true, new Sort(new Order("charityType"))));
		return "list";
	}
	
	@GetMapping("charity/{charityType}")
	public String getCharitiesByType(Model model, @PathVariable String charityType){
		model.addAttribute("message", "List Charities of type: " + charityType);
		model.addAttribute("users", userRepository.findByCharityTypeEquals(charityType));
		return "list";
	}
	
	@GetMapping("registration")
	public String registration() {
		return "registration";
	}
	
	@PostMapping("registration")
	public ModelAndView register(@RequestBody UserD user) {
		
		String password = user.getPassword();
		String encryptedPassword = encoder.encode(password);
		user.setPassword(encryptedPassword);

		ModelAndView mv = new ModelAndView();
		try { 
			userRepository.save(user);
			mv.setViewName("");
		} catch (DataIntegrityViolationException dive) {
			mv.setViewName("/registration");
			mv.addObject("errorMessage", "Cannot register that username");
		}
		return mv;
	}
}
