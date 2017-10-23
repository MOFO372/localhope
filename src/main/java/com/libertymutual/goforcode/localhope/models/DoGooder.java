package com.libertymutual.goforcode.localhope.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libertymutual.goforcode.localhope.repositories.CharityRepository;

@SuppressWarnings("serial")
@Entity
public class DoGooder extends UserD {

//	@OneToOne
//	protected static Long id;

	@Column(length = 200)
	private String donationPreference;

	@Column(length = 100)
	private String charityPreference;

	@Column
	private String followedCharities = "";

	public DoGooder() {
	}

	public DoGooder(String username, String password, String isCharity, String firstName, String lastName, String streetAddress, String city, String state, String zipCode, String phone, String email,
			String resetNumber, String donationPreference, String charityPreference, String followedCharities) {
		super(username, password, isCharity, firstName, lastName, streetAddress, city, state, zipCode, phone, email,
				resetNumber);
		this.donationPreference = donationPreference;
		this.charityPreference = charityPreference;
		this.followedCharities = followedCharities;
	}

	// Add a Charity to the list of followed charities
	public void addFollowedCharity(Charity charity)
			throws ThisIsNotACharityException, FollowUniqueCharitiesOnlyException {

		if (!charity.getIsCharity().equals("Charity")) {
			throw new ThisIsNotACharityException();
		}
		if (followedCharities.indexOf(charity.getEin()) > -1) {
			throw new FollowUniqueCharitiesOnlyException();
		}
		followedCharities += " " + charity.getEin();
		followedCharities.trim();
	}

	// Remove a Charity from the list of followed charities
	public void removeFollowedCharity(Charity charity)
			throws ThisIsNotACharityException, UnableToDeFollowThisCharityException {

		if (!charity.getIsCharity().equals("Charity")) {
			throw new ThisIsNotACharityException();
		}
		if (followedCharities.indexOf(charity.getEin()) < 0) {
			throw new UnableToDeFollowThisCharityException();
		}

		String temp = charity.getEin(); // redo
		followedCharities = followedCharities.replace(temp.trim(), ""); // redo
		followedCharities.trim();
	}

	// Returns an ArrayList populated with EINs from the followedCharities Strings
	public ArrayList<Charity> listFollowedCharities(CharityRepository charityRepository) {
		String[] charityNames = followedCharities.trim().split("\\s+");
		ArrayList<Charity> charities = new ArrayList<Charity>();

		// For DoGooder who is not following any Charities return []
		for (int i = 0; i < charityNames.length; i++) {
			if (charityNames[i].length() > 0) {
				charities.add(charityRepository.findByEin(charityNames[i]));
			}
		}
		return charities;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>(); 
		roles.add(new SimpleGrantedAuthority("ROLE_USER"));
		return roles;
	}

	public String getDonationPreferences() {
		return donationPreference;
	}

	public void setDonationPreferences(String donationPreferences) {
		this.donationPreference = donationPreferences;
	}

	public String getCharityPreference() {
		return charityPreference;
	}

	public void setCharityPreference(String charityPreference) {
		this.charityPreference = charityPreference;
	}

	public String getFollowedCharities() {
		return followedCharities;
	}

	public void setFollowedCharities(String followedCharities) {
		this.followedCharities = followedCharities;
	}

	public String getDonationPreference() {
		return donationPreference;
	}

	public void setDonationPreference(String donationPreference) {
		this.donationPreference = donationPreference;
	}

}
