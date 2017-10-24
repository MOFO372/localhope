package com.libertymutual.goforcode.localhope.models;

import java.util.ArrayList;

import org.springframework.dao.DataIntegrityViolationException;

import com.libertymutual.goforcode.localhope.repositories.CharityRepository;

public class RegistrationDto {
	protected String username;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String streetAddress;
	protected String city;
	protected String state;
	protected String zipCode;
	protected String phone;
	protected String email;
	protected String isCharity;
	protected String charityName = "NA"; // VALIDATION? TODO if(ein != null && !ein.isEmpty()) --> charityName has to be
	protected String ein;
	protected String charityUserRole = "NA";
	protected String charityType = "NA";
	protected String donationPreference;
	protected String charityPreference;
	private CharityRepository charityRepository;

	public RegistrationDto() {}
	
	public UserD createUser() throws UniqueEinForCharitiesException {
//		Charity charity = null;
//		DoGooder dogooder = null;
//		UserD user = null;
		if (isCharity.equals("Charity")) {
			Charity charity = new Charity();		
			charity.setUsername(username);
			charity.setPassword(password);
			charity.setFirstName(firstName);
			charity.setLastName(lastName);
			charity.setStreetAddress(streetAddress);
			charity.setCity(city);
			charity.setState(state);
			charity.setZipCode(zipCode);
			charity.setPhone(phone);
			charity.setEmail(email);
			charity.setIsCharity(isCharity);
			charity.setEin(ein);
			charity.setCharityName(charityName);
			ArrayList<UserD> followers = new ArrayList<UserD>();
			charity.setFollowers(followers);
			return charity;
		} else if (isCharity.equals("User")) {
			DoGooder dogooder = new DoGooder();
			dogooder.setUsername(username);
			dogooder.setPassword(password);
			dogooder.setFirstName(firstName);
			dogooder.setLastName(lastName);
			dogooder.setStreetAddress(streetAddress);
			dogooder.setCity(city);
			dogooder.setState(state);
			dogooder.setZipCode(zipCode);
			dogooder.setPhone(phone);
			dogooder.setEmail(email);
			dogooder.setIsCharity(isCharity);
			dogooder.setDonationPreference(donationPreference);
			dogooder.setCharityPreference(charityPreference);
			return dogooder;
		}
		return null;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIsCharity() {
		return isCharity;
	}

	public void setIsCharity(String isCharity) {
		this.isCharity = isCharity;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getDonationPreference() {
		return donationPreference;
	}

	public void setDonationPreference(String donationPreference) {
		this.donationPreference = donationPreference;
	}

	public String getCharityPreference() {
		return charityPreference;
	}

	public void setCharityPreference(String charityPreference) {
		this.charityPreference = charityPreference;
	}

	public CharityRepository getCharityRepository() {
		return charityRepository;
	}

	public void setCharityRepository(CharityRepository charityRepository) {
		this.charityRepository = charityRepository;
	}

}
