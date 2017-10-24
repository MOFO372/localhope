package com.libertymutual.goforcode.localhope.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonFormat;



@Entity
public class Need {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
		
	@Column(length=200, nullable=false)
	private String type;

	@Column(nullable=false)                  
	private Boolean needMet = false;
	
	@Column(length=1000, nullable=false)
	private String description;
	
	@Column
	private int originalAmount;
	
	@Column
	private String units;
	
	@JsonFormat(timezone="PST")
	@Column
	private Date dateNeeded; 
	
	@Column
	private Boolean hasFollowers = false;

	
	// Owner of the rel'p
	@ManyToMany
	private List<Charity> users;

	
	public Need () {
	}
	
	public Need(Long id, String type, Boolean needMet, String description, int originalAmount, String units, Date dateNeeded, List<Charity> users, Boolean hasFollowers) {
		//super();
		this.id = id;
		this.type = type;
		this.needMet = needMet;
		this.description = description;
		this.originalAmount = originalAmount;
		this.units = units;
		this.dateNeeded = dateNeeded;
		this.users = users; 
		this.hasFollowers = hasFollowers;
	}

	



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}


	public Boolean getNeedMet() {
		return needMet;
	}
	public void setNeedMet(Boolean needMet) {
		this.needMet = needMet;
	}


	public int getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(int originalAmount) {
		this.originalAmount = originalAmount;
	}

	public Date getDateNeeded() {
		return dateNeeded;
	}
	public void setDateNeeded(Date dateNeeded) {
		this.dateNeeded = dateNeeded;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	// Create User(s), if there are none 
	public List<Charity> getUsers() {
		if (users == null) {
			users = new ArrayList<Charity>();
		}
		return users;
	}

	public void setUsers(List<Charity> users) {
		this.users = users;
	}

	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}

	public Boolean getHasFollowers() {
		return hasFollowers;
	}

	public void setHasFollowers(Boolean hasFollowers) {
		this.hasFollowers = hasFollowers;
	}
}
