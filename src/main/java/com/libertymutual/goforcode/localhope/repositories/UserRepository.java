package com.libertymutual.goforcode.localhope.repositories;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.libertymutual.goforcode.localhope.models.UserD;


public interface UserRepository extends JpaRepository <UserD, Long>{
	
	List<UserD> findByRole(String role, Sort sort);
	List<UserD> findByCharityTypeEquals(String role);
	UserD       findByEin(String role);	                           
}
