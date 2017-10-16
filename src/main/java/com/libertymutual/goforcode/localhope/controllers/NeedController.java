package com.libertymutual.goforcode.localhope.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	// Get a Need, by id
	@GetMapping("need/{needid}")
	public Need listANeed(@PathVariable long needid) {
		return needRepository.findOne(needid);	
	}
		
	
	// Get all unfulfilled needs 
	@GetMapping("dogooder")
	public List<Need> getAllNeeds() {
		return needRepository.findByNeedMet(false);
	}	
	
	
	// Change the needMet status of a Need to its opposite  
	@PostMapping("needstatus/{needid}") 
	public String resetNeedMetStatus(@PathVariable long needid, @RequestBody long id) {				
		Need need = needRepository.findOne(needid);		
		// To be possibly used later if we want to return a list of needs the user has filled
		UserD user = userRepository.findOne(id);
		
		need.setNeedMet(!need.getNeedMet());			
		need = needRepository.save(need);
		
		return "Ok it worked!";
	}
	
	
	// Update a Need
	@PutMapping("updateneed/{needid}")
	public Need update(@RequestBody Need need, @PathVariable long needid) {
		need.setId(needid);
		return needRepository.save(need);	
	}
	
	
	// Delete a Need
	@DeleteMapping("deleteneed/{needid}")
	public void delete(@PathVariable long needid) {
		needRepository.delete(needid);	
	}
		
}
