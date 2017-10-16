package com.libertymutual.goforcode.localhope.configuration;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.localhope.models.Need;
import com.libertymutual.goforcode.localhope.models.UserD;
import com.libertymutual.goforcode.localhope.repositories.NeedRepository;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;


@Configuration
@Profile("development")
public class SeedData {
	
	// PasswordEncoder encoder
	public SeedData(UserRepository userRepository, NeedRepository needRepository, PasswordEncoder encoder) {
	
        UserD user1 = new UserD();
    
    // why is this one separate from the rest?
        user1 = userRepository.save(new UserD(13L,
        	// username
        		"PeterTheGreat2", 
        	// password	
        		encoder.encode("password"), 
        	// role	
        		"USER", 
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
        		"98191",
        	// phone
	            "(206) 333-4444", 
	        // email    
	            "find.me@if.you.can", 
	        // role?    
	            "Redund", 
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
	            "01-1234777", 
	        // charity user role (charities only)    
	            "Assistant", 
	        // charity type (charities only)    
        		"civic"));
              
        
        ArrayList<UserD> users = new ArrayList<UserD>(); 
        users.add(user1); 
            
        
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
		needRepository.save(new Need(1L, "crib",  false, "We need those cribs!", 10, sqlDate, users));
		needRepository.save(new Need(2L, "money", false, "We need to buy more cribs.", 200, sqlDate, users));
		needRepository.save(new Need(3L, "volunteer", true, "We need to deliver them cribs.", 6, sqlDate, users));
		
				
		userRepository.save(new UserD(31L, 
				
				// username		
				"ActuallyJasmine", 
				// password	
				encoder.encode("jasmine"), 
				// role	 
				"USER", 
				// user type	
				"User", 
				// first name	
				"Jasmine", 
				// last name	
				"Stotts", 
				// address	
				"321 Pine", 
				// city	 
				"Seattle", 
				// state	
				"WA", 
				// zip	
				"98191",
				// phone ***verified in Twilio***
	            "(501) 529-0281", 
	            // email     
	            "find.me@if.you.can", 
	            // role?     
	            "Redund", 
	            // donation preferences (do-gooders only)
	            "", 
	            // charity preferences (do-gooders only)
	            "", 
	            // followed charities (do-gooders only)    
	            "01-1234111", 
	            // followers (charities only)    
	            "", 
	            // charity name (charities only)    
	            "", 
	            // ein (charities only)	            
	            "", 
	            // charity user role (charities only)	            
	            "", 
	            // charity type (charities only)    	            
				""));
	
		
		userRepository.save(new UserD(32L, 
				// username
				"Mofo", 
				// password				
				encoder.encode("bitch"), 
				// role					
				"USER", 
				// user type				
				"User", 
				// first name				
				"Rachel", 
				// last name				
				"Fakey", 
				// address				
				"321 Pine", 
				// city					
				"Seattle", 
				// state				
				"WA", 
				// zip	
				"98195",
				// phone ***verified in Twilio***				
	            "(206) 218-2753", 
	    	    // email	            
	            "find.me@if.you.can", 
	    	    // role?	            
	            "Redund", 
	    	    // donation preferences (do-gooders only)	            
	            "", 
	    	    // charity preferences (do-gooders only)	            
	            "", 
	    	    // followed charities (do-gooders only)	            
	            "01-1234111", 
	    	    // followers (charities only)	            
	            "", 
	    	    // charity name (charities only)        
	            "", 
	    	    // ein (charities only)	            
	            "", 
	    	    // charity user role (charities only)	            
	            "", 
	    	    // charity type (charities only)	            
				""));


		userRepository.save(new UserD(11L, 
				// username		
				"Victor", 
				// password		
				encoder.encode("password"), 
				// role	 
				"USER", 
				// user type	
				"User", 
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
			    // role?     
			    "Redund", 
			    // donation preferences (do-gooders only)
			    "", 
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "", 
			    // followers (charities only)    
			    "", 
			    // charity name (charities only)    
			    "", 
			    // ein (charities only)    
			    "",
			    // charity user role (charities only)     
			    "",
				// charity type (charities only)    
			    ""));	
			

		userRepository.save(new UserD(11L, 
				// username				
				"Looser", 
				// password	
				encoder.encode("password"), 
				// role	 
				"USER", 
				// user type	
				"User", 
				// first name	
				"Alex", 
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
			    // role?     
			    "Redund", 
			    // donation preferences (do-gooders only)
			    "",
			    // charity preferences (do-gooders only)       
			    "", 
			    // followed charities (do-gooders only)    
			    "01-1234000 01-1234111 01-1234333", 
			    // followers (charities only)    
			    "",  
			    // charity name (charities only)    
			    "", 
			    // ein (charities only)    
			    "", 
			    // charity user role (charities only)    
			    "", 
			    // charity type (charities only)
			    ""));	
		

				    
		
		
		//added to needs seed data
		userRepository.save(new UserD(12L, 
				// username	
				"John", 
				// password	
				encoder.encode("password"), 
				// role	
				"USER", 
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
			    // role?     
			    "Redund", 
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
			    "01-1234111", 
			    // charity user role (charities only)
			    "Assistant", 
			    // charity type (charities only)
				"health"));	
		    
		
		//added to needs seed data
		userRepository.save(new UserD(13L, 
				// username				
				"PeterTheGreat", 
				// password	
				encoder.encode("password"), 
				// role	 
				"USER", 
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
			    // role?     
			    "Redund", 
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
			    "01-1234111", 
			    // charity user role (charities only)    
			    "Assistant", 
			    // charity type (charities only)
			    "civic"));	


		userRepository.save(new UserD(14L, 
				// username
				"PeterTheThird", 
				// password	
				encoder.encode("password"), 
				// role	 
				"USER", 
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
			    // role?     
			    "Redund", 
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
			    "01-1234000", 
			    // charity user role (charities only)    
			    "Assistant", 
			    // charity type (charities only)    
				"civic"));	
				    
			    
		userRepository.save(new UserD(15L, 
				// username		
				"NotPeter", 
				// password
				encoder.encode("password"), 
				// role	 
				"USER", 
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
			    // role?     
			    "Redund", 
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
			    "01-1234333", 
			    // charity user role (charities only)    
			    "Assistant", 
			    // charity type (charities only)    
				"education"));	
		
					
			    				
	}
}


















