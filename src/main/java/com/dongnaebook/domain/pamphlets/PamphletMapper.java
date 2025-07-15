package com.dongnaebook.domain.pamphlets;


import com.dongnaebook.domain.pamphlets.DTO.PamphletDTO;

public class PamphletMapper {
    public static PamphletDTO toDTO(Pamphlet pamphlet) {
        return PamphletDTO.builder()
                .title(pamphlet.getTitle())
                .content(pamphlet.getContent())
                .version(pamphlet.getVersion())
                .timestamp(pamphlet.getTimestamp())
                .build();
    }
}
