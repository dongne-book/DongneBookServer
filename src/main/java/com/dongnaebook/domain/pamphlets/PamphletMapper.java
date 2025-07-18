package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.domain.pamphlets.DTO.PamphletRequestDTO;
import com.dongnaebook.domain.pamphlets.DTO.PamphletResponseDTO;
import com.dongnaebook.domain.region.Region;

public class PamphletMapper {
    public static PamphletResponseDTO toDTO(Pamphlet pamphlet) {
        return PamphletResponseDTO.builder()
                .title(pamphlet.getTitle())
                .content(pamphlet.getContent())
                .version(pamphlet.getVersion())
                .build();
    }
    public static Pamphlet makingPamphlet(Region region, PamphletRequestDTO requestDTO) {
        return Pamphlet.builder()
                .region(region)
                .title(requestDTO.getTitle())
                .content(requestDTO.getContent())
                .version(requestDTO.getVersion())
                .build();
    }
}
