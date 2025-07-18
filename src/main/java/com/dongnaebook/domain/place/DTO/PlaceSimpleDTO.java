package com.dongnaebook.domain.place.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceSimpleDTO {
    Long id;
    String name;
    String address;
}
