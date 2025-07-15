package com.dongnaebook.domain.place.DTO;

import com.dongnaebook.domain.region.DTO.RegionResponseDTO;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDTO {
    private Long id;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private RegionResponseDTO region;

}
