package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

import com.google.maps.*;

import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.LatLng;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class GoogleCurrentLocationDistanceAPIController {

	private UserRepository userRepository;
	private NeedRepository needRepository;

	public GoogleCurrentLocationDistanceAPIController(UserRepository userRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;
	}

	public double milesToKm(double range) {
		range = range * 1.60934;
		return range;
	}

	
	
	
	@PostMapping("distancecurrent/{userid}")
	public List<Need> getCharitiesByDistanceFromCurrentLocation(@PathVariable long userid, @RequestBody double range) {

		range = milesToKm(range);

		
		final String MY_API_KEY = "AIzaSyDwJj-37b8SUeAdf1FBhqwObKCGroVhBdk";
		GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);
		
		// UserD doGooder = userRepository.findOne(userid);

		int repoSize 						= (int) userRepository.count();
		ArrayList<UserD> nearbyCharities 	= new ArrayList<UserD>();
		ArrayList<Need> nearbyNeeds 		= new ArrayList<Need>();
		List<UserD> allCharities 			= userRepository.findAll();
		List<Need> allNeeds 				= needRepository.findAll();
		UserD charity;
		
		
		LatLng coordOr = new LatLng(47.7,-122.4);
//	  LatLng coordDs = new LatLng(47.5,-122.3);
		
		
		for (int i = 0; i < repoSize; i++) {

			charity = allCharities.get(i);

			if (charity == null || charity.getStreetAddress().isEmpty() || charity.getCity().isEmpty() || 
		       !charity.getIsCharity().equals("Charity")) {
				continue;
			}

						
			try {
				DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
				
					System.out.println(" TRY BLOCK ----------------------> " + charity.getStreetAddress());
					
				DistanceMatrix trix = 
						req.origins(coordOr)
						.destinations(charity.getStreetAddress(), charity.getCity())
						.awaitIgnoreError();
								
				System.out.println(" Dist Object ---------------------> " + trix);
								
				String s = trix.rows[0].elements[0].distance.humanReadable;	
					System.out.println(" --------------------------> " + s);
				double distance = Double.parseDouble(s.substring(0, s.indexOf(" ")));
					System.out.println(" --------------------------> " + distance);
				
				
				if (distance <= range) {
					nearbyCharities.add(charity);

					for (int j = 0; j < needRepository.count(); j++) {

						Need thisNeed = allNeeds.get(j);

						if (thisNeed.getUsers().get(0).getId() == charity.getId()) {
							nearbyNeeds.add(thisNeed);
						}
					}
				}

			}

			catch (Exception e) {
				System.out.println("CATCH");
			}

		}

		return nearbyNeeds;
	}
}