package com.dongnaebook.domain.pamphlets;

import com.dongnaebook.common.domain.BaseEntity;
import com.dongnaebook.domain.pamphlets.DTO.PamphletRequestDTO;
import com.dongnaebook.domain.region.Region;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="pamphlets")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pamphlet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_code", referencedColumnName = "code")
    private Region region;

    @Column(nullable=false)
    private String title;

    @Column(nullable=true)
    private String content;

    @Column(nullable=false)
    private String version;

    public void update(PamphletRequestDTO pamphletRequestDTO) {
        this.title = pamphletRequestDTO.getTitle();
        this.content = pamphletRequestDTO.getContent();
        this.version = pamphletRequestDTO.getVersion();
    }
}
