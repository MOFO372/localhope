package com.libertymutual.goforcode.localhope.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@SuppressWarnings("serial")
@Entity
public class Charity extends UserD {

//	@OneToOne
//	protected static Long id;

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
	private ArrayList<UserD> followers;

	public Charity() {
	}

	public Charity(String username, String password, String isCharity, String firstName, String lastName, String streetAddress, String city, String state, String zipCode, String phone, String email,
			String resetNumber, ArrayList<UserD> followers, String charityName, String ein, String charityUserRole, String charityType) {
		super(username, password, isCharity, firstName, lastName, streetAddress, city, state, zipCode, phone, email,
				resetNumber);
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
		followers.add(user);
	}
	
	// Add a DoGooder to the list of a charity's followers
	public void removeFollowers(UserD user) throws ThisIsNotAUserException {
		if (!user.getIsCharity().equals("User")) {
			throw new ThisIsNotAUserException();
		}
		
		String temp = user.getUsername();
		int index = followers.indexOf(user);
		followers.remove(index);
	}

	// Returns an ArrayList populated with Users who have followed this charity
	public ArrayList<UserD> listFollowers(UserRepository userRepository) {
		return followers;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(); 
		roles.add(new SimpleGrantedAuthority("ROLE_CHARITY"));
		return roles;
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

	public ArrayList<UserD> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<UserD> followers) {
		this.followers = followers;
	}



}
