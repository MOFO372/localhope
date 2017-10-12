package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("")
public class SessionController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	private PasswordEncoder encoder;

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
			return user;
		} catch (DataIntegrityViolationException dive) {
			System.out.println("there was an error");
			mv.addObject("errorMessage", "Cannot register that username");
			return null;
		}
	}

	@PostMapping("sessions")
	public UserD login(@RequestBody LoginModel userLogin, HttpServletResponse response) {
		UserD user = userRepository.findByUsername(userLogin.getUsername());
		String username = userLogin.getUsername();
		String password = userLogin.getPassword();

		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			System.out.println("User is " + user);
			System.out.println("Username is " + username);
			System.out.println("Password is " + password);
			return user;
		} else {
			return null;
		}
	}

}
