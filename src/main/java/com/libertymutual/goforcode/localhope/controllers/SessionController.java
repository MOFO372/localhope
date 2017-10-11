package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
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

	// JSON
	@GetMapping("registration")
	public ModelAndView registration() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("");
		return mv;
	}

	// JSON

	@GetMapping("dogooder")
	public List<Need> getAllNeeds() {
		return needRepository.findAll();
	}

	@PostMapping("registration")
	public String register(@RequestBody UserD user, HttpServletResponse response) {

		String password = user.getPassword();
		String encryptedPassword = encoder.encode(password);
		user.setPassword(encryptedPassword);

		ModelAndView mv = new ModelAndView();
		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException dive) {
			System.out.println("there was an error");
			mv.addObject("errorMessage", "Cannot register that username");
		}
//		mv.setViewName("");
		return "redirect:/doogooder";
	}
}
