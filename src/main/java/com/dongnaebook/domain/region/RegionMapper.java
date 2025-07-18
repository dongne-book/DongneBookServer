package com.dongnaebook.domain.region;

import com.dongnaebook.domain.region.DTO.RegionResponseDTO;
import com.dongnaebook.domain.region.Region;

public class RegionMapper {
   public static RegionResponseDTO toResponseDTO(Region region) {
       return RegionResponseDTO.builder()
               .code(region.getCode())
                .name(region.getName())
               .build();
   }
    public static Region toRegion(RegionResponseDTO region) {
        return Region.builder()
                .code(region.getCode())
                .name(region.getName())
                .build();
    }
}
