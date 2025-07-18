package com.dongnaebook.domain.place;

import com.dongnaebook.domain.place.DTO.PlaceSimpleDTO;
import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.DTO.PlaceResponseDTO;
import com.dongnaebook.domain.region.RegionMapper;

public class PlaceMapper {
   public static PlaceResponseDTO toResponseDTO(Place place) {
       return PlaceResponseDTO.builder()
               .id(place.getId())
               .name(place.getName())
               .address(place.getAddress())
               .latitude(place.getLocation().getY())
               .longitude(place.getLocation().getX())
               .region(RegionMapper.toResponseDTO(place.getRegion()))
               .build();
   }

    public static PlaceSimpleDTO toSimpleDTO(Place place) {
        return PlaceSimpleDTO.builder()
                .id(place.getId())
                .name(place.getName())
                .address(place.getAddress())
                .build();
    }

}
