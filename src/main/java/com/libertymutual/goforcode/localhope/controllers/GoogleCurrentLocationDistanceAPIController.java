package com.libertymutual.goforcode.localhope.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.localhope.models.Coordinate;
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

	//converts Google's default KM measurements into miles
	public double milesToKm(Double range) {
		range = range * 1.60934;
		return range;
	}

	//finds charities near a user's current location
	@PostMapping("distancecurrent/{range}")
	public List<Need> getCharitiesByDistanceFromCurrentLocation(@PathVariable double range,
			@RequestBody Coordinate coordinate) {

		range = milesToKm(range);
		double latCurrent = coordinate.getLatitude();
		double longCurrent = coordinate.getLongitude();

		final String MY_API_KEY = "AIzaSyDwJj-37b8SUeAdf1FBhqwObKCGroVhBdk";
		GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);

		int repoSize = (int) userRepository.count();
		ArrayList<UserD> nearbyCharities = new ArrayList<UserD>();
		ArrayList<Need> nearbyNeeds = new ArrayList<Need>();
		List<UserD> allCharities = userRepository.findAll();
		List<Need> allNeeds = needRepository.findAll();
		UserD charity;

		LatLng coordOr = new LatLng(latCurrent, longCurrent);

		for (int i = 0; i < repoSize; i++) {
			charity = allCharities.get(i);

			if (charity == null || charity.getStreetAddress().isEmpty() || charity.getCity().isEmpty()
					|| !charity.getIsCharity().equals("Charity")) {
				continue;
			}

			try {
				DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);

				DistanceMatrix trix = req.origins(coordOr).destinations(charity.getStreetAddress(), charity.getCity())
						.awaitIgnoreError();

				String s = trix.rows[0].elements[0].distance.humanReadable;
				double distance = Double.parseDouble(s.substring(0, s.indexOf(" ")));

				if (distance <= range) {
					nearbyCharities.add(charity);

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