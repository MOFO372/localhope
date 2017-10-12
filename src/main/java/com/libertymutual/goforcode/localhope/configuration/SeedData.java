package com.libertymutual.goforcode.localhope.configuration;


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
	
	// , PasswordEncoder encoder
	public SeedData(UserRepository userRepository, NeedRepository needRepository, PasswordEncoder encoder) {
	
        
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        
		needRepository.save(new Need(1L, "crib",      true,  "We need those cribs!",           10,  sqlDate));
		needRepository.save(new Need(2L, "money",     false, "We need to buy more cribs.",     200, sqlDate));
		needRepository.save(new Need(3L, "volunteer", false, "We need to deliver them cribs.", 6,   sqlDate));
		
		
//		Long id, 			String username, String password, 
//		String roleName, 	
		// String isCharity, 
//		String firstName, 	String lastName, String streetAddress, String city, String state, String zipCode, String phone, String email, 
//		String role,   ?????? 
//		String donationPreferences, String charityPreference, 	String followedCharities, 
//		String charityName, 		String ein, 				String charityUserRole, 			String charityType) 
		

		userRepository.save(new UserD(11L, "Victor", encoder.encode("password"), "USER", "DoGooder", "Peter", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Redund", "", "", "", "", "", "", ""));	
		
		userRepository.save(new UserD(11L, "Looser", encoder.encode("password"), "USER", "DoGooder", "Alex", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Redund", "", "", "01-1234000 01-1234111 01-1234333", "", "", "", ""));	
		
		
		
		userRepository.save(new UserD(12L, "John", encoder.encode("password"), "USER", "Charity", "Peter", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Redund", "", "", "", "ABC H", "01-1234000", "Assistant", "health"));	
		
		userRepository.save(new UserD(13L, "PeterTheGreat", encoder.encode("password"), "USER", "Charity", "Peter", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Redund", "", "", "", "Civic-1", "01-1234111", "Assistant", "civic"));	

		userRepository.save(new UserD(14L, "PeterTheThird", encoder.encode("password"), "USER", "Charity", "James", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Redund", "", "", "", "Civic-2", "01-1234222", "Assistant", "civic"));	
	
		userRepository.save(new UserD(15L, "NotPeter", encoder.encode("password"), "USER", "Charity", "Jay", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Redund", "", "", "", "Educ", "01-1234333", "Assistant", "education"));	
	
					
	}
}


















