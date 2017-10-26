package com.libertymutual.goforcode.localhope.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.libertymutual.goforcode.localhope.models.Need;

public interface NeedRepository extends JpaRepository<Need, Long> {

	List<Need> findByNeedMet(Boolean needMet);
}
