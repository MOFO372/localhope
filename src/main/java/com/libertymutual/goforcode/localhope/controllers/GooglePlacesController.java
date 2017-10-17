package com.libertymutual.goforcode.localhope.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.maps.GeoApiContext;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("")
public class GooglePlacesController {
	
	@PostMapping("distance/{userid}")
    public String getCharitiesByDistance(@PathVariable long userid, @RequestBody int range) {
//      setQueryRateLimit(int maxQps)
//      Sets the maximum number of queries that will be executed during a 1 second interval.
        
        final String MY_API_KEY = "AIzaSyDXUd3vSC0dj5xs1-HLoc1BFRyy69U5ZEc";
        
        
        GeoApiContext context = new GeoApiContext().setApiKey(MY_API_KEY).setQueryRateLimit(10);
        
        return ""; 

	}
}
