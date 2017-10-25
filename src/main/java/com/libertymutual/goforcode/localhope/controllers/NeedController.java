package com.libertymutual.goforcode.localhope.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.FulfillModel;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.models.YouCannotDeleteThisNeedException;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class NeedController {

	private UserRepository userRepository;
	private NeedRepository needRepository;
	private SendGridController sendGridController;

	private NeedController(UserRepository userRepository, NeedRepository needRepository,
			SendGridController sendGridController) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;
		this.sendGridController = sendGridController;

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
		// To be possibly used later if we want to return a list of needs the user has
		// filled
		UserD user = userRepository.findOne(id);

		need.setNeedMet(!need.getNeedMet());
		need = needRepository.save(need);

		return "Ok it worked!";
	}

	// Decrements the need quantity when someone donates time/money/stuff
	@PostMapping("needreduce/{needid}")
	public void reduceNeedAmount(@PathVariable long needid, @RequestBody FulfillModel fulfill) throws IOException {
		Need need = needRepository.findOne(needid);
		UserD user = userRepository.findOne(fulfill.getUserid());
		String username = user.getUsername();

		// decrement need count
		need.setOriginalAmount(Math.max(need.getOriginalAmount() - fulfill.getReduceBy(), 0));
		if (need.getOriginalAmount() == 0)
			need.setNeedMet(true);
		need = needRepository.save(need);

		// send email
		sendGridController.fulfill(fulfill, needid);

	}

	// Update a Need
	@PutMapping("updateneed/{needid}")
	public Need update(@RequestBody Need need, @PathVariable long needid) {
		Need thisNeed = needRepository.findOne(needid);
		List<Charity> thisUser = thisNeed.getUsers();
		need.setId(needid);
		need.setUsers(thisUser);
		return needRepository.save(need);
	}

	// Delete a Need
	// Changed to a Put mapping so that front-end can send a message body
	@PutMapping("deleteneed/{needid}")
	public String deleteANeed(@PathVariable long needid, @RequestBody long userid)
			throws YouCannotDeleteThisNeedException {
		Need need = needRepository.findOne(needid);

		if (userid != need.getUsers().get(0).getId()) {
			throw new YouCannotDeleteThisNeedException();
		}
		;

		try {
			needRepository.delete(needid);
		} catch (EmptyResultDataAccessException err) {
			System.out.println("You cannot delete a Need you did not create.");
		}

		return "Your need has been deleted.  Have a nice day!";
	}

}
