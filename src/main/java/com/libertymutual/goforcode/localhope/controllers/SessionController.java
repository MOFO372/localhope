package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class SessionController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	private PasswordEncoder encoder;
	

	// add: PasswordEncoder encoder as parameter
	public SessionController(NeedRepository needRepository, UserRepository userRepository, PasswordEncoder encoder) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
		this.encoder = encoder;
		
	}

	
	@GetMapping("registration")
	public ModelAndView registration() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("");
		return mv;
	}


	@GetMapping("dogooder")
	public List<Need> getAllNeeds() {
		return needRepository.findAll();
	}

	@PostMapping("registration")

	public UserD register(@RequestBody UserD user, HttpServletResponse response) {


		String password = user.getPassword();
		String encryptedPassword = encoder.encode(password);
		user.setPassword(encryptedPassword);

		ModelAndView mv = new ModelAndView();
		try {
			userRepository.save(user);
			System.out.println("user got saved");
		} catch (DataIntegrityViolationException dive) {
			System.out.println("there was an error");
			mv.addObject("errorMessage", "Cannot register that username");
		}

		return user;
	}
	
	//PLAYING WITH LOGIN METHOD
	@GetMapping("login")
	public String login() {
		UserD user = new UserD(); 
		String username = user.getUsername(); 
		String password = user.getPassword(); 
		
		userRepository.findByUsername(username); 
		
//		if(user != null && BCrypt.checkpw(password, user.getPassword())) { 
//			req.session().attribute("currentUser", user);
//		}
//		
		return ""; 
	}
	
	
	
	
}
