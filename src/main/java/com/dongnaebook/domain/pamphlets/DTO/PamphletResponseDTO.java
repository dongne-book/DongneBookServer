package com.dongnaebook.domain.pamphlets.DTO;

import com.dongnaebook.common.domain.DTO.BaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PamphletResponseDTO extends BaseDTO {
    private String regionName;
    private String title;
    private String content;
    private String version;
}
