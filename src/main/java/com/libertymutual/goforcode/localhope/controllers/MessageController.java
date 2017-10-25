package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class MessageController {

	@Value("${TWILIO_KEY}")
	private String key;

	@Value("${TWILIO_AUTH}")
	private String auth;

	private UserRepository userRepository;
	private NeedRepository needRepository;
	private CharityRepository charityRepository;

	public MessageController(NeedRepository needRepository, UserRepository userRepository,
			CharityRepository charityRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
		this.charityRepository = charityRepository;
	}

	@PostMapping("message/{charityid}")
	public String sendMessage(@PathVariable long charityid, @RequestBody long needid) {

		String ACCOUNT_SID = auth;
		String AUTH_TOKEN = key;
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		Need need = needRepository.findOne(needid);
		Charity charity = charityRepository.findOne(charityid);
		String needMessage = "What we need: " + need.getOriginalAmount() + " " + need.getUnits() + " for "
				+ need.getDescription() + " by " + need.getDateNeeded();

		ArrayList<UserD> followers = charity.listFollowers(userRepository);

		if (followers.get(0) == null) {
			return "You can't send any messages because you don't have any followers.  How sad.  :(";
		}

		for (int i = 0; i < followers.size(); i++) {
			UserD follower = followers.get(i);
			String phone = follower.getPhone();
			Message message = Message.creator(new PhoneNumber(phone), new PhoneNumber("+15018304032"), needMessage)
					.create();
		}

		return "message sent";
	}
}
