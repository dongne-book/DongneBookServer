package com.dongnaebook.domain.place.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaceRequestDTO {
    private String name;
    private String address;
    private double latitude;
    private double longitude;
}
