package com.libertymutual.goforcode.localhope.models;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@Entity
public class Charity extends UserD {
	
	@Id
	@Column
	public Long getId() {
		return id;
	}
	
	// Charity only -----------------------------------
	@Column(length = 200)
	private String charityName = "NA"; // VALIDATION? TODO if(ein != null && !ein.isEmpty()) --> charityName has to be
										// populated ??

	// IRS: "EIN is a unique 9-digit number", e.g. 01-0553690
	@Column(length = 10)
	private String ein;

	@Column(length = 20)
	private String charityUserRole = "NA";

	@Column(length = 20)
	private String charityType = "NA"; // Charity type/category?? {health, education, puppies... }

	// Followers
	@Column
	private String followers;

	public Charity() {
	}

	public Charity(String followers, String charityName, String ein, String charityUserRole, String charityType) {
		
		this.followers = followers;
		this.charityName = charityName;
		this.ein = ein;
		this.charityUserRole = charityUserRole;
		this.charityType = charityType;

	}

	// Add a DoGooder to the list of a charity's followers
	public void addFollowers(UserD user) throws ThisIsNotAUserException {
		if (!user.getIsCharity().equals("User")) {
			throw new ThisIsNotAUserException();
		}
		followers += " " + user.getUsername();
		followers.trim();
	}

	// Returns an ArrayList populated with Users who have followed this charity
	public ArrayList<UserD> listFollowers(UserRepository userRepository) {
		String[] userNames = followers.trim().split("\\s+");

		ArrayList<UserD> users = new ArrayList<UserD>();

		for (int i = 0; i < userNames.length; i++) {
			System.out.println(userRepository.findByUsername(userNames[i])); // delete
			users.add(userRepository.findByUsername(userNames[i]));
		}
		return users;
	}

	public String getCharityName() {
		return charityName;
	}

	public void setCharityName(String charityName) {
		this.charityName = charityName;
	}

	public String getEin() {
		return ein;
	}

	public void setEin(String ein) {
		this.ein = ein;
	}

	public String getCharityUserRole() {
		return charityUserRole;
	}

	public void setCharityUserRole(String charityUserRole) {
		this.charityUserRole = charityUserRole;
	}

	public String getCharityType() {
		return charityType;
	}

	public void setCharityType(String charityType) {
		this.charityType = charityType;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}
}
