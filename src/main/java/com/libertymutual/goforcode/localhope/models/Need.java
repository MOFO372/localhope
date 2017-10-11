package com.libertymutual.goforcode.localhope.models;

import java.sql.Date;
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

	@Column(nullable=false)                  // false by default ??
	private Boolean needMet = false;
	
	@Column(length=1000, nullable=false)
	private String description;
	
	private int originalAmount;
	
	// @JsonFormat(pattern = "YYYY-MM-dd")
	private Date dateNeeded;

	

	@ManyToMany
	private List<UserD> users;

	
	public Need () {}
	
	public Need(Long id, String type, Boolean needMet, String description, int originalAmount, Date dateNeeded) {
		//super();
		this.id = id;
		this.type = type;
		this.needMet = needMet;
		this.description = description;
		this.originalAmount = originalAmount;
		this.dateNeeded = dateNeeded;
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

	public List<UserD> getUsers() {
		return users;
	}

	public void setUsers(List<UserD> users) {
		this.users = users;
	}


}
