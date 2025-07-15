package com.dongnaebook.domain.region.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponseDTO {
    private String code;
    private String name;
}
