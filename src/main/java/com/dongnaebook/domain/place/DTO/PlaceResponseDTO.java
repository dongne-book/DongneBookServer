package com.dongnaebook.domain.place.DTO;

import com.dongnaebook.common.domain.DTO.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.locationtech.jts.geom.Point;

import java.time.LocalDate;

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

}
