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

	@Column(length = 200)
	private String charityName = "NA";

	// IRS: "EIN is a unique 9-digit number", e.g. 01-0553690
	@Column(length = 10)
	private String ein;

	@Column(length = 20)
	private String charityUserRole = "NA";

	@Column(length = 20)
	private String charityType = "NA"; // Charity type/category {health, education, puppies... }

	@Column
	private String followers;

	public Charity() {
	}

	public Charity(String username, String password, String isCharity, String firstName, String lastName,
			String streetAddress, String city, String state, String zipCode, String phone, String email,
			String resetNumber, String followers, String charityName, String ein, String charityUserRole,
			String charityType) {
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
		followers += " " + user.getUsername();
		followers.trim();
	}

	// Add a DoGooder to the list of a charity's followers
	public void removeFollowers(UserD user) throws ThisIsNotAUserException {
		if (!user.getIsCharity().equals("User")) {
			throw new ThisIsNotAUserException();
		}

		String temp = user.getUsername();
		followers = followers.replace(temp.trim(), "");
		followers.trim();
	}

	// Returns an ArrayList populated with Users who have followed this charity
	public ArrayList<UserD> listFollowers(UserRepository userRepository) {
		String[] userNames = followers.trim().split("\\s+");

		ArrayList<UserD> users = new ArrayList<UserD>();

		for (int i = 0; i < userNames.length; i++) {
			users.add(userRepository.findByUsername(userNames[i]));
		}
		return users;
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

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

}
