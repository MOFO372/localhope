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
	public ArrayList<UserD> getCharitiesByDistance(@PathVariable long userid, @RequestBody int range) {

		
		final String MY_API_KEY = "AIzaSyDXUd3vSC0dj5xs1-HLoc1BFRyy69U5ZEc";
		GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);
		// DistanceMatrix trixA = null;     
		
		UserD doGooder = userRepository.findOne(userid);
		UserD charity = null;
		double distance;
		int repoSize = (int) userRepository.count();
		ArrayList<UserD> nearbyCharities = new ArrayList<UserD>();
		
		
		for(long i = 1; i < 20; i++) {
			
					System.out.println(" -------------------------->" + i);
			
			charity = userRepository.findOne(i);
			
			if( charity == null || charity.getStreetAddress().isEmpty() ||  charity.getCity().isEmpty() ||
				i == userid     || !charity.getIsCharity().equals("Charity") ) {
					System.out.println(" Ignore this User -------------------------->" + i);
				continue;
			}
			
					System.out.println(" Update Charity -------------------------->" + charity.getStreetAddress());
			
		    try {
		        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);	        
		        DistanceMatrix trix = 
		        	 req.origins(doGooder.getStreetAddress(), doGooder.getCity())
		                .destinations(charity.getStreetAddress(), charity.getCity())
		                //.mode(TravelMode.DRIVING)
		                //.avoid(RouteRestriction.HIGHWAYS)
		                //.language("en-US")
		                .await()
		                ;
		        
		        String s = trix.rows[0].elements[0].distance.humanReadable;
		        distance = Double.parseDouble(s.substring(0, s.indexOf(" ")));
		                System.out.println(" Distance: ----------------->" + distance);
		        
		        if(distance <= (double)range) {
		        	nearbyCharities.add(charity);
		        	    System.out.println("Charity is in range: =======>" + charity.getCharityName() + " " + charity.getFirstName());
		        }
		                                                  
		        		System.out.println(" -------------------------->" + charity.getId());
		        		System.out.println(" -------------------------->" + s);
		        		System.out.println(" -------------------------->" + distance);
		        	
			}    
		    
		    
	         catch(Exception e){
			        System.out.println(" ERRPR: Cannot determine distance to this object (not a charity, no address, etc.");
			 } 	    
		}		
		return nearbyCharities;
	}
}
