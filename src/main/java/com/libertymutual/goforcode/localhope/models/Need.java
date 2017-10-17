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
	
	private int originalAmount;
	
	private String units;
	
	private Date dateNeeded;

	
	// Owner of the rel'p
	@ManyToMany
	private List<UserD> users;

	
	public Need () {
	}
	
	public Need(Long id, String type, Boolean needMet, String description, int originalAmount, String units, Date dateNeeded, List<UserD> users) {
		//super();
		this.id = id;
		this.type = type;
		this.needMet = needMet;
		this.description = description;
		this.originalAmount = originalAmount;
		this.units = units;
		this.dateNeeded = dateNeeded;
		this.users = users; 
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
	public List<UserD> getUsers() {
		if (users == null) {
			users = new ArrayList<UserD>();
		}
		return users;
	}

	public void setUsers(List<UserD> users) {
		this.users = users;
	}

	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
}
