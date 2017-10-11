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
        
		needRepository.save(new Need(1L, "crib",  false, "We need those cribs!", 10, sqlDate));
		needRepository.save(new Need(2L, "money", false, "We need to buy more cribs.", 200, sqlDate));
		needRepository.save(new Need(3L, "volunteer", false, "We need to deliver them cribs.", 6, sqlDate));
		
		
			
//		public UserD(Long id, String username, String password, String roleName, Boolean isCharity, String firstName, String lastName, String streetAddress, String city, String state,
//				String zipCode, String phone, String email, String role, String donationPreferences,
//				String charityPreference, String followedCharities, String charityName, String ein, String charityUserRole, String charityType) 	
		
		userRepository.save(new UserD(12L, "Peter", encoder.encode("password"), "USER", "Charity", "Peter", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Charity", "", "", "", "ABC H", "01-1234999", "Assistant", "health"));	
		
		userRepository.save(new UserD(12L, "PeterTheGreat", encoder.encode("password"), "USER", "User", "Peter", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Charity", "", "", "", "Civic-1", "01-1234999", "Assistant", "civic"));	

		userRepository.save(new UserD(12L, "PeterTheThird", encoder.encode("password"), "USER", "User", "Peter", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Charity", "", "", "", "Civic-2", "01-1234999", "Assistant", "civic"));	
	
		userRepository.save(new UserD(12L, "NotPeter", encoder.encode("password"), "Peter", "Charity", "USER", "Alson", "321 Pine", "Seattle", "WA", "98195",
	            "(206) 333-4444", "find.me@if.you.can", "Charity", "", "", "", "Educ", "01-1234999", "Assistant", "education"));	
	
					
	}
}


















