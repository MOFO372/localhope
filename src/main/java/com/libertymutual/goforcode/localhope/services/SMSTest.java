package com.libertymutual.goforcode.localhope.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSTest {

	 public static final String ACCOUNT_SID = "AC30b2203fa2ba1ca8bbec30eb6b90f28b";
	 public static final String AUTH_TOKEN = "641d50e26cc03dba2048ec3a8cab7550";
	 
	 public static void main(String[] args) {
		    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

		    Message message = Message.creator(new PhoneNumber("+15015290281"),
		        new PhoneNumber("+15018304032"), 
		        "This is the ship that made the Kessel Run in fourteen parsecs?").create();

		    System.out.println(message.getSid());
		  }
}
