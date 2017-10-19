package com.libertymutual.goforcode.localhope.controllers;

import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;
import com.sendgrid.*;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sendgrid")
public class SendGridController {
	
	@Value("${SENDGRID_KEY}")
	private String key;
	
	private UserRepository userRepository;
	
	public SendGridController (UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping("")
	public void main(String username) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Email from = new Email("rsoley92@gmail.com");
		String subject = "Welcome to LocalHope!";
		Email to = new Email(user.getEmail());
		Content content = new Content("text/html", " ");
		Mail mail = new Mail(from, subject, to, content);
		String dumbTemplate = "89de9d75-6e04-44d1-a11d-eaba98301eb9";
		String charityTemplate = "782b277d-a9ba-4e28-8ba5-32638d8f4f31";
		String dogooderTemplate = "68adc8d5-fe38-4f6d-9ff5-187c2a4eb775";
		System.out.println("isCharity" + user.getIsCharity());
		
		if (user.getIsCharity().equals("Charity")) {
			mail.setTemplateId(charityTemplate);
		} else if (user.getIsCharity().equals("User")) {
			mail.setTemplateId(dogooderTemplate);
		} else {
			mail.setTemplateId(dumbTemplate);
		}
		
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		personalization.addSubstitution("%first_name%", user.getFirstName());
		personalization.addSubstitution("%city%", user.getCity());
		personalization.addSubstitution("%charity_name%", user.getCharityName());
		
		mail.addPersonalization(personalization);

		SendGrid sg = new SendGrid(key);
		
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	@PostMapping("get_password")
	public void retrieve(@RequestBody String username) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Email from = new Email("rsoley92@gmail.com");
		String subject = "Your LocalHope password";
		Email to = new Email(user.getEmail());
		System.out.println("email is " + user.getEmail());
		Content content = new Content("text/html", " ");
		Mail mail = new Mail(from, subject, to, content);
		String dumbTemplate = "89de9d75-6e04-44d1-a11d-eaba98301eb9";
		mail.setTemplateId(dumbTemplate);
		
		//verification code fanciness
		Integer reset = (int) Math.round(Math.random()*99999);
		String resetCode = reset.toString().format("%05d", reset);
		user.setResetNumber(resetCode);
		System.out.println("your code is" + resetCode);
		
		// we personalize our emails because we love our users
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		personalization.addSubstitution("%first_name%", user.getFirstName());
		personalization.addSubstitution("%code%", user.getResetNumber());
		mail.addPersonalization(personalization);

		SendGrid sg = new SendGrid(key);
		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}
}
