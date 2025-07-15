package com.dongnaebook.domain.place;

import com.dongnaebook.common.exception.NotFoundException;
import com.dongnaebook.domain.place.DTO.PlaceRequestDTO;
import com.dongnaebook.domain.place.DTO.PlaceResponseDTO;
import com.dongnaebook.domain.region.Region;
import com.dongnaebook.domain.region.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;
    private final RegionRepository regionRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory();


    public Point createPoint(double longitude, double latitude) {
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    @Transactional
    public PlaceResponseDTO create(PlaceRequestDTO requestDTO) {
        Point location = createPoint(requestDTO.getLongitude(), requestDTO.getLatitude());
        location.setSRID(4326);

        Region region = findRegionByAddress(requestDTO.getAddress());

        Place place = Place.builder()
                .name(requestDTO.getName())
                .address(requestDTO.getAddress())
                .location(location)
                .region(region)
                .build();


        Place savedPlace = placeRepository.save(place);
        return PlaceMapper.toResponseDTO(savedPlace);
    }

    private Region findRegionByAddress(String address) {
        List<Region> regions = regionRepository.findBestMatchingRegion(address);
        if (regions.isEmpty()) {
            throw new NotFoundException("Region not found for address: " + address);
        }
        return regions.get(0);
    }

    public List<PlaceResponseDTO> getAll() {
        return placeRepository.findAll().stream()
                .map(PlaceMapper::toResponseDTO)
                .toList();
    }

    public PlaceResponseDTO getById(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Place not found"));
        return PlaceMapper.toResponseDTO(place);
    }

    public List<PlaceResponseDTO> searchByLocation(double longitude, double latitude, double distance) {
        Point searchPoint = createPoint(longitude, latitude);
        searchPoint.setSRID(4326);

        return placeRepository.findByDistance(searchPoint.getX(), searchPoint.getY(), distance * 1000)
                .stream()
                .map(PlaceMapper::toResponseDTO)
                .toList();
    }

    public List<PlaceResponseDTO> searchByNameAndAddress(String name, String address) {
        return placeRepository.findByNameAndAddress(name, address)
                .stream()
                .map(PlaceMapper::toResponseDTO)
                .toList();
    }

}