
package com.libertymutual.goforcode.localhope.configuration;


import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.localhope.models.Charity;
import com.libertymutual.goforcode.localhope.models.DoGooder;
import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;


@Configuration
@Profile("development")
// spring.profiles.active=development
public class SeedData {
	
	// PasswordEncoder encoder
	public SeedData(UserRepository userRepository, NeedRepository needRepository, PasswordEncoder encoder) {
	
      Charity user1 = new Charity();
    
    // why is this one separate from the rest?
        user1 = userRepository.save(new Charity(
        	// username
        		"PeterTheGreat2", 
        	// password	
        		encoder.encode("password"), 
        	// user type	
        		"Charity", 
        	// first name	
        		"Peter", 
        	// last name	
        		"Alson", 
        	// address	
        		"1001 4th Ave", 
        	// city	
        		"Seattle", 
        	// state	
        		"WA", 
        	// zip	
        		"98001",
        	// phone
	            "(206) 333-4444", 
	        // email    
	            "find.me@if.you.can", 
	        // donation preferences (do-gooders only)
	            "", 
	        // charity preferences (do-gooders only)       
	            "", 
	        // followed charities (do-gooders only)    
	            "", 
	        // followers (charities only)    
	            "", 
	        // charity name (charities only)    
	            "Civic-1", 
	        // ein (charities only)    
	            "01-1234777"));
           
        
        // ???????
        ArrayList<UserD> users = new ArrayList<UserD>(); 
        users.add(user1); 
                  
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
		needRepository.save(new Need(1L, "crib",  false, "We need those cribs!", 10, "units", sqlDate, users, false));
		needRepository.save(new Need(2L, "money", false, "We need to buy more cribs.", 200, "units", sqlDate, users, false));
		needRepository.save(new Need(3L, "volunteer", false, "We need to deliver them cribs.", 6, "units", sqlDate, users, false));
		
				
		userRepository.save(new DoGooder(
				// username		
				"ActuallyJasmine", 
				// password	
				encoder.encode("jasmine"), 
				// user type	
				"User", 
				// first name	
				"Jasmine", 
				// last name	
				"Stotts", 
				// address	
				"530 Broadway East", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98102",
				// phone ***verified in Twilio***
	            "(501) 529-0281", 
	            // email     
	            "jasmine@if.you.can", 
	            // donation preferences (do-gooders only)
	            "", 
	            // charity preferences (do-gooders only)
	            "", 
	            // followed charities (do-gooders only)         
	            "01-1234111", 
	            // followers (charities only)    
	            ""));
	
		
		userRepository.save(new DoGooder(// username
				"Mofo", 
				// password				
				encoder.encode("bitch"), 
				// user type				
				"User", 
				// first name				
				"Rachel", 
				// last name				
				"Fakey", 
				// address				
				"6034 Palatine Ave N", 
				// city					
				"Seattle", 
				// state				
				"WA", 
				// zip	
				"98103",
				// phone ***verified in Twilio***				
	            "(206) 218-2753", 
	    	    // email	            
	            "rachel@if.you.can", 
	    	    // donation preferences (do-gooders only)	            
	            "", 
	    	    // charity preferences (do-gooders only)	            
	            "", 
	    	    // followed charities (do-gooders only)	            
	            "01-1234111", 
	    	    // followers (charities only)	            
	            ""));


		userRepository.save(new DoGooder(// username		
				"Victor", 
				// password		
				encoder.encode("password"), 
				// user type	
				"User", 
				// first name	
				"Seth", 
				// last name	
				"Slaughter", 
				// address	
				"516 High Street", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98225",
				// phone 
	            "(206) 333-4444", 
	            // email     
			    "seth@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "", 
			    // followers (charities only)    
			    ""));	
			

		userRepository.save(new DoGooder(// username				
				"Looser", 
				// password	
				encoder.encode("password"), 
				// user type	
				"User", 
				// first name	
				"Alex", 
				// last name	
				"Alson", 
				// address	
				"6211 77th Ave SE", 
				// city	 
				"Mercer Island", 
				// state	
				"WA", 
				// zip	
				"98195",
				// phone 
	            "(206) 333-4444", 
	            // email     
			    "find.me@if.you.can", 
			    // donation preferences (do-gooders only)
			    "",
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "01-1234000 01-1234111 01-1234333", 
			    // followers (charities only)    
			    ""));	
		

				    
		
		
		//added to needs seed data
		userRepository.save(new Charity(// username	
				"John", 
				// password	
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"Peter", 
				// last name
				"Alson", 
				// address	
				"321 Pine", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98195",
				// phone 
			    "(206) 333-4444", 
			    // email     
			    "find.me@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)
			    "",
			    // followers (charities only)
			    "ActuallyJasmine", 
			    // charity name (charities only)
			    "ABC H", 
			    // ein (charities only)
			    "01-1234110"));	
		    
		
		//added to needs seed data
		userRepository.save(new Charity(// username				
				"PeterTheGreat", 
				// password	
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"Peter", 
				// last name	
				"Alson", 
				// address	
				"321 Pine", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98195",
				// phone 
	            "(206) 333-4444", 
	            // email     
			    "find.me@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "", 
			    // followers (charities only)    
			    "ActuallyJasmine Mofo", 
			    // charity name (charities only)    
			    "Civic-1", 
			    // ein (charities only)
			    "01-1234111"));	


		userRepository.save(new Charity(// username
				"PeterTheThird", 
				// password	
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"James", 
				// last name	
				"Alson", 
				// address	
				"321 Pine", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98195",
				// phone 
	            "(206) 333-4444", 
	            // email     
			    "find.me@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "", 
			    // followers (charities only)    
			    "", 
			    // charity name (charities only)
			    "Civic-2", 
			    // ein (charities only)    
			    "01-1234000"));	
				    
			    
		userRepository.save(new Charity(// username		
				"NotPeter", 
				// password
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"Jay", 
				// last name	
				"Alson", 
				// address	
				"321 Pine", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98195",
				// phone 
			    "(206) 333-4444", 
			    // email     
			    "find.me@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "", 
			    // followers (charities only)    
			    "", 
			    // charity name (charities only)    
			    "Educ", 
			    // ein (charities only)    
			    "01-1234333"));	
		
					
			    // actual live charities
		userRepository.save(new Charity(// username	
				"Cancer", 
				// password	
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"Chad", 
				// last name
				"Chase", 
				// address	
				"1425 Broadway, Ste 260", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98122",
				// phone 
			    "(206) 867-5309", 
			    // email     
			    "acs@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)
			    "",
			    // followers (charities only)
			    "", 
			    // charity name (charities only)
			    "American Cancer Society", 
			    // ein (charities only)
			    "81-4392579"));	

		userRepository.save(new Charity(// username	
				"Puppies", 
				// password	
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"Doug", 
				// last name
				"Doggo", 
				// address	
				"371 Williamson Way", 
				// city	 
				"Bellingham", 
				// state	
				"WA", 
				// zip	
				"98226",
				// phone 
			    "(206) 867-5309", 
			    // email     
			    "puppies@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)
			    "",
			    // followers (charities only)
			    "", 
			    // charity name (charities only)
			    "Whatcom Humane Society", 
			    // ein (charities only)
			    "91-0677564"));	
		
		userRepository.save(new Charity(// username	
				"Smart", 
				// password	
				encoder.encode("password"), 
				// user type	
				"Charity", 
				// first name	
				"Ms", 
				// last name
				"Teacher", 
				// address	
				"4701 Southwest Admiral Way, Box 149", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98116",
				// phone 
			    "(206) 867-5309", 
			    // email     
			    "smart@if.you.can", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)
			    "",
			    // followers (charities only)
			    "", 
			    // charity name (charities only)
			    "Education First", 
			    // ein (charities only)
			    "91-1699248"));
	}
}
