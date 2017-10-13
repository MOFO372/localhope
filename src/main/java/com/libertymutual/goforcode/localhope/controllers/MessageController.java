package com.libertymutual.goforcode.localhope.controllers;

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
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class MessageController {

	private UserRepository userRepository;
	private NeedRepository needRepository;
	
	public MessageController(NeedRepository needRepository, UserRepository userRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
	}
	
	@PostMapping("message/{userid}")
	public String sendMessage(@PathVariable long userid, @RequestBody Need need) {
		
		UserD user = userRepository.findOne(userid);
		
		String needMessage = "What we need: " + need.getDescription();
		String userPhone = user.getPhone();

		
		String ACCOUNT_SID = "AC30b2203fa2ba1ca8bbec30eb6b90f28b";
		String AUTH_TOKEN = "641d50e26cc03dba2048ec3a8cab7550";
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message message = Message.creator(new PhoneNumber(userPhone), new PhoneNumber("+15018304032"), needMessage).create();
		
		return "message sent";
	}
}
