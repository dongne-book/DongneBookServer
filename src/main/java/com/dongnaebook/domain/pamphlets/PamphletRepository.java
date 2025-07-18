package com.dongnaebook.domain.pamphlets;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PamphletRepository extends JpaRepository<Pamphlet, Long> {
    boolean existsByRegion_CodeAndVersion(String regionCode, String version);
    List<Pamphlet> findByRegion_Code(String regionCode);
}
