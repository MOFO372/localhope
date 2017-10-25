package com.libertymutual.goforcode.localhope.controllers;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.FulfillModel;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;
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
	private CharityRepository charityRepository;

	public SendGridController(UserRepository userRepository, NeedRepository needRepository,
			CharityRepository charityRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;
		this.charityRepository = charityRepository;
	}

	@PostMapping("")
	public void main(String username) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Long id = user.getId();

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

		if (user.getIsCharity().equals("Charity")) {
			Charity charity = charityRepository.findOne(id);
			personalization.addSubstitution("%charity_name%", charity.getCharityName());
		}

		mail.addPersonalization(personalization);

		SendGrid sg = new SendGrid(key);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

	@PostMapping("getpassword")
	public String retrieve(@RequestBody String username) throws IOException {
		UserD user = userRepository.findByUsername(username);
		Email from = new Email("localhope17@gmail.com");
		String subject = "Your LocalHope password";
		Email to = new Email(user.getEmail());
		Email temp = new Email("test@test");
		Content content = new Content("text/html", " ");
		Mail mail = new Mail(from, subject, temp, content);
		String resetTemplate = "89de9d75-6e04-44d1-a11d-eaba98301eb9";
		mail.setTemplateId(resetTemplate);

		// verification code fanciness
		Integer reset = (int) Math.round(Math.random() * 99999);
		String resetCode = reset.toString().format("%05d", reset);
		user.setResetNumber(resetCode);
		userRepository.save(user);

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
			return "Sent an e-mail to " + user.getEmail();
		} catch (IOException ex) {
			throw ex;
		}
	}

	public void fulfill(FulfillModel fulfill, long needid) throws IOException {
		long userId = fulfill.getUserid();
		UserD user = userRepository.findOne(userId);
		Need need = needRepository.findOne(needid);
		int amountNumber = fulfill.getReduceBy();
		String amount = String.valueOf(amountNumber);

		Charity charity = need.getUsers().get(0);

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
		personalization.addSubstitution("%user_first_name%", user.getFirstName());
		personalization.addSubstitution("%user_last_name%", user.getLastName());
		personalization.addSubstitution("%user_email%", user.getEmail());
		personalization.addSubstitution("%user_phone_number%", user.getPhone());
		personalization.addSubstitution("%need_description%", need.getDescription());
		personalization.addSubstitution("%need_amount%", amount);
		personalization.addSubstitution("%need_unit%", need.getUnits());
		personalization.addSubstitution("%city%", user.getCity());
		personalization.addSubstitution("%charity_name%", charity.getCharityName());

		mail.addPersonalization(personalization);

		SendGrid sg = new SendGrid(key);

		Request request = new Request();
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch (IOException ex) {
			throw ex;
		}
	}

}
