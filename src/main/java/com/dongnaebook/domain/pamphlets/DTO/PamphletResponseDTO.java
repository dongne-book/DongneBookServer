package com.dongnaebook.domain.pamphlets.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PamphletResponseDTO {
    private String regionName;
    private String title;
    private String content;
    private String version;
    private LocalDateTime timestamp;
}
