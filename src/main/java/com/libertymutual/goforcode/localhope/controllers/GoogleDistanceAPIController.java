package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;
import java.util.List;

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
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import com.google.maps.*;
import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.TravelMode;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class GoogleDistanceAPIController {

	private UserRepository userRepository;
	private NeedRepository needRepository;
	
	public GoogleDistanceAPIController(NeedRepository needRepository, UserRepository userRepository) {
		this.needRepository = needRepository;
		this.userRepository = userRepository;
	}
	
	
	@PostMapping("distance/{userid}")
	public DistanceMatrix getCharitiesByDistance(@PathVariable long userid, @RequestBody int range) {

//		setQueryRateLimit(int maxQps)
//		Sets the maximum number of queries that will be executed during a 1 second interval.
		
		final String MY_API_KEY = "AIzaSyDXUd3vSC0dj5xs1-HLoc1BFRyy69U5ZEc";
		DistanceMatrix trixA = null;
		
		UserD doGooder = userRepository.findOne(userid);
		
		
		GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);
		
	    try {
	        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
	        
	        
	        
	        DistanceMatrix trix = req.origins(doGooder.getStreetAddress(), doGooder.getCity())
	                .destinations("1001 4th Ave", "Seattle")
	                .mode(TravelMode.DRIVING)
	                .avoid(RouteRestriction.HIGHWAYS)
	                //.language("en-US")
	                .await()
	                ;
	        
	        return trix.;

	    } 
//	    catch(ApiException e){
//	        output += this.printError(e);
//	    } 
	    
	    catch(Exception e){
	        System.out.println(e.getMessage());
	    } 
	    
	    
//		Request
//		origins: Vancouver+BC|Seattle
//		destinations: San+Francisco|Victoria+BC
//		mode: driving
//		key: API_KEY
		

		
//		for(int i = 0; i < followers.size(); i++) {
//		  	UserD follower = followers.get(i);
//		  	String phone = follower.getPhone();
//			Message message = Message.creator(new PhoneNumber(phone),
//	        new PhoneNumber("+15018304032"), 
//	        needMessage).create();
//		}
		
		return trixA;
	}
}
