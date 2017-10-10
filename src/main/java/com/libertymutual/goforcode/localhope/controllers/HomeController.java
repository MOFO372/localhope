package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;



@Controller
@RequestMapping("")
public class HomeController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	// private PasswordEncoder encoder;
	
	
	// add: PasswordEncoder encoder as parameter
	public HomeController(NeedRepository needRepository, UserRepository userRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
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
		model.addAttribute("users", userRepository.findByRole("Charity", new Sort(new Order("charityType"))));
		return "list";
	}
	
	@GetMapping("charity/{charityType}")
	public String getCharitiesByType(Model model, @PathVariable String charityType){
		model.addAttribute("message", "List Charities of type: " + charityType);
		model.addAttribute("users", userRepository.findByCharityTypeEquals(charityType));
		return "list";
	}
	
}
