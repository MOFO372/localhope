package com.libertymutual.goforcode.localhope.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.libertymutual.goforcode.localhope.models.FollowUniqueCharitiesOnlyException;
import com.libertymutual.goforcode.localhope.models.LoginModel;
import com.libertymutual.goforcode.localhope.models.RegistrationDto;
import com.libertymutual.goforcode.localhope.models.ThisIsNotAUserException;
import com.libertymutual.goforcode.localhope.models.UniqueEinForCharitiesException;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = "*")
@Api(description = "Logins, registrations, and password resets.")
@RequestMapping("")
public class SessionController {

	private UserRepository userRepository;
	private PasswordEncoder encoder;
	private SendGridController sendGridController;
	private CharityRepository charityRepository;

	public SessionController(UserRepository userRepository, PasswordEncoder encoder,
			SendGridController sendGridController, CharityRepository charityRepository) {
		this.userRepository = userRepository;
		this.encoder = encoder;
		this.sendGridController = sendGridController;
		this.charityRepository = charityRepository;

	}

	//home page for registering
	@GetMapping("registration")
	public ModelAndView registration() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("");
		return mv;
	}

	//allows a user to register
	@PostMapping("registration")
	public UserD register(@RequestBody RegistrationDto dto, HttpServletResponse response)
			throws FollowUniqueCharitiesOnlyException, UniqueEinForCharitiesException, IOException {

		String password = dto.getPassword();
		String encryptedPassword = encoder.encode(password);
		dto.setPassword(encryptedPassword);
		UserD user = dto.createUser();

		try {
			if (dto.getEin() != null && !dto.getEin().isEmpty() && charityRepository.findByEin(dto.getEin()) != null) {
				throw new UniqueEinForCharitiesException();
			}
			userRepository.save(user);
			sendGridController.main(dto.getUsername());
		} catch (DataIntegrityViolationException dive) {
			return null;
		}
		return user;
	}

	//allows the user to log in
	@PostMapping("sessions")
	public UserD login(@RequestBody LoginModel userLogin, HttpServletResponse response) throws ThisIsNotAUserException {
		UserD user = userRepository.findByUsername(userLogin.getUsername());
		String password = userLogin.getPassword();

		if (user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		} else {
			throw new ThisIsNotAUserException();
		}
	}

	//allows to user to reset password after receiving reset code email
	@PutMapping("resetpassword")
	public UserD getPassword(@RequestBody LoginModel userLogin) throws IOException {
		UserD user = userRepository.findByUsername(userLogin.getUsername());
		String encryptedPassword = encoder.encode(userLogin.getPassword());
		String sentCode = userLogin.getResetNumber();

		if (sentCode.equals(user.getResetNumber())) {
			user.setPassword(encryptedPassword);
			user.setResetNumber(null);
			userRepository.save(user);
			return user;
		} else {
			return null;
		}
	}

}
