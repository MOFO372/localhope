package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.DoGooder;
import com.libertymutual.goforcode.localhope.models.FollowUniqueCharitiesOnlyException;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.ThisIsNotACharityException;
import com.libertymutual.goforcode.localhope.models.ThisIsNotADogooderException;
import com.libertymutual.goforcode.localhope.models.ThisIsNotAUserException;
import com.libertymutual.goforcode.localhope.models.UnableToDeFollowThisCharityException;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;
import com.libertymutual.goforcode.localhope.repositories.DoGooderRepository;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@RestController
@RequestMapping("user")
public class UserApiController {

	private NeedRepository needRepository;
	private UserRepository userRepository;
	private CharityRepository charityRepository;
	private DoGooderRepository dogooderRepository;

	public UserApiController(NeedRepository needRepository, UserRepository userRepository,
			CharityRepository charityRepository, DoGooderRepository dogooderRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
		this.dogooderRepository = dogooderRepository;
		this.charityRepository = charityRepository;
	}

	//finds one user based on user ID
	@GetMapping("{userid}")
	public UserD getOneUser(@PathVariable long userid) {
		return userRepository.findOne(userid);
	}

	// Associates a Need with a DoGooder
	@PostMapping("need/{userid}")
	public UserD associateDogooderAndNeed(@PathVariable long userid, @RequestBody Need need) {
		UserD user = userRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
		needRepository.save(need);
		return user;
	}

	// Associates the DoGooder with a Charity (by placing the EIN in DoGooder
	// followCharity property)
	@PostMapping("followcharity/{dogooderid}")
	public UserD associateDogooderAndCharity(@PathVariable long dogooderid, @RequestBody long charityid)
			throws ThisIsNotACharityException, ThisIsNotAUserException, FollowUniqueCharitiesOnlyException {
		DoGooder user = dogooderRepository.findOne(dogooderid);
		Charity charity = charityRepository.findOne(charityid);
		user.addFollowedCharity(charity);
		charity.addFollowers(user);
		userRepository.save(user);
		return user;
	}

	// Dis-associates the DoGooder from a Charity (by removing the EIN in DoGooder
	// followCharity property)
	@PostMapping("unfollowcharity/{dogooderid}")
	public UserD removeDogooderAndCharity(@PathVariable long dogooderid, @RequestBody long charityid)
			throws ThisIsNotACharityException, UnableToDeFollowThisCharityException, ThisIsNotAUserException {
		DoGooder user = dogooderRepository.findOne(dogooderid);
		Charity charity = charityRepository.findOne(charityid);

		user.removeFollowedCharity(charity);
		charity.removeFollowers(user);
		userRepository.save(user);
		return user;
	}

	//creates a user in the user repo
	@PostMapping("")
	public UserD createUser(@RequestBody UserD user) {
		return userRepository.save(user);
	}

	//shows the dogooder's followed charities 
	@GetMapping("followedcharities/{dogooderid}")
	public List<Charity> displayAssociatedCharitiesForDoGooder(@PathVariable long dogooderid)
			throws ThisIsNotACharityException {
		DoGooder user = dogooderRepository.findOne(dogooderid);
		List<Charity> followedCharities = user.listFollowedCharities(charityRepository);
		return followedCharities;
	}

	// Compare ZIP of a DoGooder and a Charity
	@PostMapping("zip/{dogooderid}")
	public boolean compareZips(@PathVariable long dogooderid, @RequestBody long charityid)
			throws ThisIsNotACharityException, ThisIsNotADogooderException {

		UserD user = userRepository.findOne(dogooderid);
		UserD charity = userRepository.findOne(charityid);

		if (!charity.getIsCharity().equals("Charity")) {
			throw new ThisIsNotACharityException();
		}
		if (user.getIsCharity().equals("Charity")) {
			throw new ThisIsNotADogooderException();
		}
		return user.getZipCode().equals(charity.getZipCode());
	}

	// Return Charities with the same ZIP that a DoGooder has
	@GetMapping("zipall/{dogooderid}")
	public List<UserD> extractZips(@PathVariable long dogooderid)
			throws ThisIsNotACharityException, ThisIsNotADogooderException {

		UserD user = userRepository.findOne(dogooderid);
		if (user.getIsCharity().equals("Charity")) {
			throw new ThisIsNotADogooderException();
		}

		List<UserD> zipCharities = userRepository.findByZipCodeStartingWithAndIsCharity(user.getZipCode(), "Charity");
		return zipCharities;
	}

}
