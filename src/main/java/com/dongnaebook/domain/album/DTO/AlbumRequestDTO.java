package com.dongnaebook.domain.album.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlbumRequestDTO {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
}
