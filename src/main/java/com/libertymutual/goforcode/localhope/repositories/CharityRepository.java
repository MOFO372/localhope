package com.libertymutual.goforcode.localhope.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libertymutual.goforcode.localhope.models.Charity;

public interface CharityRepository extends JpaRepository<Charity, Long> {

	Charity findByEin(String role);

}
