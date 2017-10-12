package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.ThisIsNotACharityException;
import com.libertymutual.goforcode.localhope.models.UnableToDeFollowThisCharityException;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

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

	// associates a need with a dogooder once button is clicked
	@PostMapping("need/{userid}")
	public UserD associateDogooderAndNeed(@PathVariable long userid, @RequestBody Need need) {
		UserD user = userRepository.findOne(userid);
		need = needRepository.findOne(need.getId());
		user.addNeed(need);
		// userRepository.save(user);
		needRepository.save(need);
		return user;
	}

	// associates the dogooder with a charity
	@PostMapping("followcharity/{dogooderid}")
	public UserD associateDogooderAndCharity(@PathVariable long dogooderid, @RequestBody UserD charity)
			throws ThisIsNotACharityException {
		UserD user = userRepository.findOne(dogooderid);
		charity = userRepository.findOne(charity.getId());
		user.addFollowedCharity(charity);
		userRepository.save(user);
		return user;
	}

	// allows a user to unfollow a charity after being associated with it - will
	// remove the association
	@PostMapping("unfollowcharity/{dogooderid}")
	public UserD removeDogooderAndCharity(@PathVariable long dogooderid, @RequestBody UserD charity)
			throws ThisIsNotACharityException, UnableToDeFollowThisCharityException {
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

	//to use for my charities link on a dogooder page
	@GetMapping("followedcharities/{dogooderid}")
    public List<UserD> displayAssociatedCharitiesForDoGooder(@PathVariable long dogooderid)
            throws ThisIsNotACharityException {
        UserD user = userRepository.findOne(dogooderid);
        List<UserD> followedCharities = user.listFollowedCharities(userRepository);
        return followedCharities;
    }
}
