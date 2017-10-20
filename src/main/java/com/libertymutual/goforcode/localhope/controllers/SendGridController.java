package com.libertymutual.goforcode.localhope.controllers;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
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
	private NeedRepository needRepository;
	
	public SendGridController (UserRepository userRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;
	}
	
	@PostMapping("")
	public void main(String username) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Email from = new Email("localhope17@gmail.com");
		String subject = "Welcome to LocalHope!";
		Email to = new Email(user.getEmail());
		Content content = new Content("text/html", " ");
		Email temp = new Email("test@test");
		Mail mail = new Mail(from, subject, temp, content);
		String charityTemplate = "782b277d-a9ba-4e28-8ba5-32638d8f4f31";
		String dogooderTemplate = "68adc8d5-fe38-4f6d-9ff5-187c2a4eb775";
		
		if (user.getIsCharity().equals("Charity")) {
			mail.setTemplateId(charityTemplate);
		} else if (user.getIsCharity().equals("User")) {
			mail.setTemplateId(dogooderTemplate);
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
//			System.out.println(response.getStatusCode());
//			System.out.println(response.getBody());
//			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}
	
	@PostMapping("getpassword")
	public String retrieve(@RequestBody String username) throws IOException {
		System.out.println("username is " + username);
		UserD user = userRepository.findByUsername(username);
		Email from = new Email("localhope17@gmail.com");
		String subject = "Your LocalHope password";
		Email to = new Email(user.getEmail());
		Email temp = new Email("test@test");
		System.out.println("email is " + user.getEmail());
		Content content = new Content("text/html", " ");
		Mail mail = new Mail(from, subject, temp, content);
		String resetTemplate = "89de9d75-6e04-44d1-a11d-eaba98301eb9";
		mail.setTemplateId(resetTemplate);
		
		//verification code fanciness
		Integer reset = (int) Math.round(Math.random()*99999);
		String resetCode = reset.toString().format("%05d", reset);
		user.setResetNumber(resetCode);
		userRepository.save(user);
		System.out.println("your code is " + resetCode);
		System.out.println("your name is " + user.getFirstName());
		System.out.println("your charity name is " + user.getCharityName());
		
		// we personalize our emails because we love our users
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		personalization.addSubstitution("%first_name%", user.getFirstName());
		personalization.addSubstitution("%city%", user.getCity());
		personalization.addSubstitution("%code%", resetCode);
		
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
			return "Sent an e-mail to " + user.getEmail();
		} catch (IOException ex) {
			throw ex;
		}
	}

	
	public void fulfill(String username, long needid) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Need need = needRepository.findOne(needid);
		
		UserD charity = need.getUsers().get(0); 
		
		Email from = new Email("localhope17@gmail.com");
		String subject = "A need has been fulfilled!";
		Email to = new Email(charity.getEmail());
		Content content = new Content("text/html", " ");
		Email temp = new Email("test@test");
		Mail mail = new Mail(from, subject, temp, content);
		String fulfillTemplate = "14e6588d-6c18-48e4-a2cd-fb779677c90c";
		mail.setTemplateId(fulfillTemplate);
		
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
//			System.out.println(response.getStatusCode());
//			System.out.println(response.getBody());
//			System.out.println(response.getHeaders());
		} catch (IOException ex) {
			throw ex;
		}
	}
	
}
