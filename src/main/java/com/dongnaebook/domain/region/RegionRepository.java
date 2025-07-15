package com.dongnaebook.domain.region;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {
    @Query("SELECT r FROM Region r WHERE :name LIKE CONCAT('%', r.name, '%') ORDER BY LENGTH(r.name) DESC")
    List<Region> findBestMatchingRegion(@Param("name") String name);

}
