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

import com.google.maps.*;

import com.google.maps.model.DistanceMatrix;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class GoogleDistanceAPIController {

	private UserRepository userRepository;
<<<<<<< HEAD
	private NeedRepository needRepository;

	public GoogleDistanceAPIController (UserRepository userRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;
=======
	private NeedRepository needRepository; 

	public GoogleDistanceAPIController (UserRepository userRepository, NeedRepository needRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository; 
>>>>>>> f97ffeaf2fe05dce123aa3131841e9486acb28b0
	}

	@PostMapping("distance/{userid}")
	public List<Need> getCharitiesByDistance(@PathVariable long userid, @RequestBody double range) {

		final String MY_API_KEY = "AIzaSyAxpehF6uSYc8LfvOnN83rYvIUwVbK5pyw";

		UserD doGooder = userRepository.findOne(userid);

		GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);

		int repoSize = (int) userRepository.count();
		ArrayList<UserD> nearbyCharities = new ArrayList<UserD>();
		ArrayList<Need> nearbyNeeds = new ArrayList<Need>(); 
		List<UserD> allCharities = userRepository.findAll();
<<<<<<< HEAD
		List<Need> nearbyNeeds = new ArrayList<Need>();
=======
		List<Need> allNeeds = needRepository.findAll();
>>>>>>> f97ffeaf2fe05dce123aa3131841e9486acb28b0
		UserD charity;

		for (int i = 0; i < repoSize; i++) {

			charity = allCharities.get(i);

			if (charity == null || charity.getStreetAddress().isEmpty() || charity.getCity().isEmpty() || i == userid
					|| !charity.getIsCharity().equals("Charity")) {
				continue;
			}

			try {
				DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);

				DistanceMatrix trix = req.origins(doGooder.getStreetAddress(), doGooder.getCity())
						.destinations(charity.getStreetAddress(), charity.getCity())
						// .mode(TravelMode.DRIVING)
						// .avoid(RouteRestriction.HIGHWAYS)
						// .language("en-US")
						.await();

				String s = trix.rows[0].elements[0].distance.humanReadable;
				double distance = Double.parseDouble(s.substring(0, s.indexOf(" ")));

				if (distance <= range) {
					nearbyCharities.add(charity);
					
					for(int j=0; j < needRepository.count(); j++) {
					
						Need thisNeed = allNeeds.get(j); 
						
						if(thisNeed.getUsers().get(0).getId() == charity.getId()) {
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
