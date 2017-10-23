package com.libertymutual.goforcode.localhope.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.libertymutual.goforcode.localhope.repositories.UserRepository;

@SuppressWarnings("serial")
@Entity
public abstract class UserD implements UserDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	@Column(nullable = false, unique = true)
	protected String username;

	@Column(nullable = false)
	protected String password;

	@Column(length = 200, nullable = false)
	protected String firstName;

	@Column(length = 200, nullable = false)
	protected String lastName;

	@Column(length = 200, nullable = false)
	protected String streetAddress;

	@Column(length = 50, nullable = false)
	protected String city;

	@Column(length = 2, nullable = false)
	protected String state;

	@Column(length = 10, nullable = false)
	protected String zipCode;

	@Column(length = 15, nullable = false)
	protected String phone;
	
	@Column(length = 100, nullable = false)
	protected String email;

	@Column(name="role_name", nullable = false)
	protected String isCharity;

	@Column(length = 5)
	protected String resetNumber;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "users")
	private List<Need> needs;
	


	public UserD() {
	}

	// List<Need> needs
	public UserD(String username, String password, String isCharity, String firstName,
			String lastName, String streetAddress, String city, String state, String zipCode, String phone,
			String email, String resetNumber) {

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
		this.resetNumber = resetNumber;
				

	}
	
	// Associate a Need with a User (either DoGooder or Charity)
	public void addNeed(Need need) {
		if (needs == null) {
			needs = new ArrayList<Need>();
		}

		needs.add(need);			
		need.getUsers().add(this);
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

	public String getIsCharity() {
		return isCharity;
	}

	public void setIsCharity(String isCharity) {
		this.isCharity = isCharity;
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
	
	@JsonIgnore
	@Override
	public abstract Collection<? extends GrantedAuthority> getAuthorities();

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

	
	public String getResetNumber() {
		return resetNumber;
	}

	public void setResetNumber(String resetNumber) {
		this.resetNumber = resetNumber;
	}

//	public UserRepository getUserRepository() {
//		return userRepository;
//	}
//
//	public void setUserRepository(UserRepository userRepository) {
//		this.userRepository = userRepository;
//	}

	public List<Need> getNeeds() {
		return needs;
	}

	public void setNeeds(List<Need> needs) {
		this.needs = needs;
	}



}
