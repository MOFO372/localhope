package com.libertymutual.goforcode.localhope.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.localhope.models.UserD;

@Repository
public interface UserRepository extends JpaRepository<UserD, Long> {

	UserD findByUsername(String username);

	List<UserD> findByZipCodeStartingWithAndIsCharity(String zipCode, String Charity);
}
