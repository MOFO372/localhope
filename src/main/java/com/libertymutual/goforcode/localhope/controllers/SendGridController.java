package com.libertymutual.goforcode.localhope.controllers;

import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;
import com.sendgrid.*;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sendgrid")
public class SendGridController {
	
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
		mail.setTemplateId(dumbTemplate);
		
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		personalization.addSubstitution("%first_name%", user.getFirstName());
		
		mail.addPersonalization(personalization);

		SendGrid sg = new SendGrid("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");
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
	public void retrieve(String username) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Email from = new Email("rsoley92@gmail.com");
		String subject = "Your LocalHope password";
		Email to = new Email(user.getEmail());
		Content content = new Content("text/html", " ");
		Mail mail = new Mail(from, subject, to, content);
		String dumbTemplate = "89de9d75-6e04-44d1-a11d-eaba98301eb9";
		mail.setTemplateId(dumbTemplate);
		
		Personalization personalization = new Personalization();
		personalization.addTo(to);
		personalization.addSubstitution("%first_name%", user.getFirstName());
		
		mail.addPersonalization(personalization);

		SendGrid sg = new SendGrid("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");
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
