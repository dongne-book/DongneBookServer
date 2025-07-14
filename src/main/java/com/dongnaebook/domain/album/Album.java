package com.dongnaebook.domain.album;

import com.dongnaebook.common.domain.BaseEntity;
import com.dongnaebook.domain.album.DTO.AlbumRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="albums")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Album extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate endDate;

    public void update(AlbumRequestDTO updateDTO) {
        this.name = updateDTO.getName();
        this.startDate = updateDTO.getStartDate();
        this.endDate = updateDTO.getEndDate();
    }
}