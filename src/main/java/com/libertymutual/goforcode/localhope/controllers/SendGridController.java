package com.libertymutual.goforcode.localhope.controllers;

import com.sendgrid.*;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sendgrid")
public class SendGridController {
	
	@PostMapping("")
	public void main(String[] args) throws IOException {
		Email from = new Email("rsoley92@gmail.com");
		String subject = "Sending with SendGrid is Fun";
		Email to = new Email("jasminestotts@gmail.com");
		Content content = new Content("text/html", "and easy to do anywhere, even with Java");
		Mail mail = new Mail(from, subject, to, content);
		String dumbTemplate = "89de9d75-6e04-44d1-a11d-eaba98301eb9";
		mail.setTemplateId(dumbTemplate);

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
