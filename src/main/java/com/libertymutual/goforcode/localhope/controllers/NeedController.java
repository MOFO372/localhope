package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class NeedController {

	private UserRepository userRepository;
	private NeedRepository needRepository;

	private NeedController(UserRepository userRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;

	}


	// Change the needMet status of a Need to the opposite  
	@PostMapping("needstatus/{needid}") 
	public List<Need> resetNeedMetStatus(@PathVariable long needid, @RequestBody UserD user) {				
		Need need = needRepository.findOne(needid);		
		user = userRepository.findOne(user.getId());
		
		need.setNeedMet(!need.getNeedMet());			
		need = needRepository.save(need);

		return user.getNeeds();
	}
}