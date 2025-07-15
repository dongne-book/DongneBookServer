package com.dongnaebook.domain.place;

import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    @Query(value = """
    SELECT * FROM places p
    WHERE
        (:name IS NULL OR p.name ILIKE CONCAT('%', :name, '%'))
    AND
        (:address IS NULL OR p.address ILIKE CONCAT('%', :address, '%'))
""", nativeQuery = true)
    List<Place> findByNameAndAddress(
            @Param("name") String name,
            @Param("address") String address
    );

    @Query(value = """
    SELECT * FROM places p
    WHERE ST_DWithin(p.location, ST_SetSRID(ST_MakePoint(:lng, :lat), 4326), :distance)
""", nativeQuery = true)
    List<Place> findByDistance(
            @Param("lng") double lng,
            @Param("lat") double lat,
            @Param("distance") double distance
    );
}