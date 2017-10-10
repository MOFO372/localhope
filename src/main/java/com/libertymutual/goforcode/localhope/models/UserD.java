package com.libertymutual.goforcode.localhope.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class UserD implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String username;
	
	@Column(nullable=false) 
	private String password;
	
	@Column(length=200, nullable=false)
	private String firstName;

	@Column(length=200, nullable=false)
	private String lastName;
		
	@Column(length=200, nullable=false)
	private String streetAddress;
	
	@Column(length=50, nullable=false)
	private String city;
	
	@Column(length=2, nullable=false)       // ??
	private String state;	
	
	@Column(length=10, nullable=false)
	private String zipCode;
		
	@Column(length=15, nullable=false)
	private String phone;
	
	@Email
	@Column(length=100, nullable=false)
	private String email;
	
	@Column(nullable=false)
	private Boolean isCharity;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="user", cascade=CascadeType.ALL)
	private List<UserRole> roles;
	
	// User only  -----------------------------------	
	@Column(length=200)
	private String donationPreferences;      // multiple?   $, volunteer, items ??  ENUMERATION ??
	// private ArrayList<String> donationPreferences; 
	
	@Column(length=100)						// select from charityType values?
	private String charityPreference;	
	
	// Followed Charities   
	@Column
	private String followedCharities;
	
	
	// Charity only  -----------------------------------
	@Column(length=200)					
	private String charityName = "NA";         // if(ein != null && !ein.isEmpty())   charityName has to be populated ??
	
	// IRS: "EIN is a unique 9-digit number", e.g. 01-0553690 - it's a String, really
	@Column(length=10)   // unique?
	private String ein = "00-0000000";
	
	@Column(length=20)
	private String charityUserRole = "NA";

	@Column(length=20)
	private String charityType = "NA";           // Charity type/category?? {health, education, puppies... }


	
	@JsonIgnore  
	@ManyToMany(mappedBy="users")
	private List<Need> needs;

	
	public UserD() {}

    // List<Need> needs
	public UserD(Long id, String username, String password, String roleName, Boolean isCharity, String firstName, String lastName, String streetAddress, String city, String state,
			String zipCode, String phone, String email, String role, String donationPreferences,
			String charityPreference, String followedCharities, String charityName, String ein, String charityUserRole, String charityType) {
		
		this.id = id;
		this.username = username;
		this.password = password;
		this.isCharity = isCharity;
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phone = phone;
		this.email = email;
//		this.role = role;
		
		this.donationPreferences = donationPreferences;
		this.charityPreference = charityPreference;
		this.followedCharities = followedCharities;
		
		this.charityName = charityName;
		this.ein = ein;
		this.charityUserRole = charityUserRole;
		this.charityType = charityType;
		
		roles = new ArrayList<UserRole>();
		roles.add(new UserRole(roleName, this));
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Boolean getIsCharity() {
		return isCharity;
	}


	public void setIsCharity(Boolean isCharity) {
		this.isCharity = isCharity;
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


	public List<Need> getNeeds() {
		return needs;
	}


	public void setNeeds(List<Need> needs) {
		this.needs = needs;
	}

	public String getCharityType() {
		return charityType;
	}

	public void setCharityType(String charityType) {
		this.charityType = charityType;
	}

	public String getDonationPreferences() {
		return donationPreferences;
	}

	public void setDonationPreferences(String donationPreferences) {
		this.donationPreferences = donationPreferences;
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

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> roleNames = roles.stream()
				.map(userRole -> "ROLE_" + userRole.getName())
				.collect(Collectors.toList());
			
			String authorityString = String.join(",", roleNames); 
			return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	public List<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}
}
