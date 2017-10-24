package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

import com.google.maps.*;

import com.google.maps.model.DistanceMatrix;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class GoogleDistanceAPIController {
	
	@Value("${GOOGLE_KEY}")
	private String key;

	private UserRepository userRepository;
	private NeedRepository needRepository;
	private CharityRepository charityRepository;

	public GoogleDistanceAPIController(UserRepository userRepository, NeedRepository needRepository,
			                           CharityRepository charityRepository) {
		this.userRepository = userRepository;
		this.needRepository = needRepository;
		this.charityRepository = charityRepository;
	}

	public double milesToKm(double range) {
		range = range * 1.60934;
		return range;
	}

	// Returns nearby needs for a given do-gooder within a given range
	@PostMapping("distance/{userid}")
	public List<Need> getCharitiesByDistance(@PathVariable long userid, @RequestBody double range) {

		range = milesToKm(range);
		System.out.println(range);

		final String MY_API_KEY = key;

		UserD doGooder = userRepository.findOne(userid);

		GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);

		int repoSize = (int) charityRepository.count();					// user
		ArrayList<Charity> nearbyCharities = new ArrayList<Charity>();   // UserD
		ArrayList<Need> nearbyNeeds = new ArrayList<Need>();
		List<Charity> allCharities 	= charityRepository.findAll();  // UserD
		List<Need> allNeeds 		= needRepository.findAll();
		Charity charity;											// UserD

		
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
				
				System.out.println(" --------------->" + distance);

				if (distance <= range) {
					nearbyCharities.add(charity);
					System.out.println(" Charity --------------->" + charity);
					for (int j = 0; j < needRepository.count(); j++) {

						Need thisNeed = allNeeds.get(j);

						if (thisNeed.getUsers().get(0).getId() == charity.getId() && !thisNeed.getNeedMet()) {
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