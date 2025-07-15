package com.dongnaebook.domain.place;

import com.dongnaebook.domain.place.Place;
import com.dongnaebook.domain.place.DTO.PlaceResponseDTO;

public class PlaceMapper {
   public static PlaceResponseDTO toResponseDTO(Place place) {
       return PlaceResponseDTO.builder()
               .id(place.getId())
               .name(place.getName())
               .address(place.getAddress())
               .latitude(place.getLocation().getY())
               .longitude(place.getLocation().getX())
               .build();
   }
}
