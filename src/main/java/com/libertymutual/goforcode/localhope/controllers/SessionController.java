package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;
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

import com.libertymutual.goforcode.localhope.models.FollowUniqueCharitiesOnlyException;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UniqueEinForCharitiesException;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.models.UserRole;
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
		return needRepository.findByNeedMet(false); 
	}

	@PostMapping("registration")
	public UserD register(@RequestBody UserD user, HttpServletResponse response) throws FollowUniqueCharitiesOnlyException, UniqueEinForCharitiesException {

		String password = user.getPassword();
		String encryptedPassword = encoder.encode(password);
		user.setPassword(encryptedPassword);	
		// Uniqueness request		
		ModelAndView mv = new ModelAndView();
		try {
			if (user.getEin() != null && !user.getEin().isEmpty() && userRepository.findByEin(user.getEin()) != null) {
				throw new UniqueEinForCharitiesException();
			}
			userRepository.save(user);
			return user;
		} catch (DataIntegrityViolationException dive) {
			mv.addObject("errorMessage", "Cannot register that username");
			return null;
		}
		
		
	}

	@PostMapping("sessions")
	public UserD login(@RequestBody LoginModel userLogin, HttpServletResponse response) {
		UserD user = userRepository.findByUsername(userLogin.getUsername());
		String password = userLogin.getPassword();

		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}

}
